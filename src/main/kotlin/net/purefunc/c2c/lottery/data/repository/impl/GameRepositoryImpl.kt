package net.purefunc.c2c.lottery.data.repository.impl

import arrow.core.Either.Companion.catch
import kotlinx.coroutines.flow.toList
import net.purefunc.c2c.lottery.data.dao.BetItemDao
import net.purefunc.c2c.lottery.data.dao.GameDao
import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.dto.response.BetItemDtoRes
import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes
import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.data.table.BetItemDo
import net.purefunc.core.ext.randomUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Repository
class GameRepositoryImpl(
    private val gameDao: GameDao,
    private val betItemDao: BetItemDao,
) : GameRepository {

    @Transactional(readOnly = true)
    override suspend fun findByUuid(uuid: String) =
        catch {
            val gameVos = gameDao.findGameByUuid(uuid).toList()
            if (gameVos.isEmpty()) throw NoSuchElementException()

            GameDtoRes(
                uuid = gameVos[0].uuid,
                owner = gameVos[0].owner,
                guestName = gameVos[0].guestName,
                hostName = gameVos[0].hostName,
                sportType = gameVos[0].sportType,
                betItems = gameVos.map {
                    BetItemDtoRes(
                        uuid = it.betItemUuid,
                        type = it.type,
                        value = it.value,
                        odds = it.odds,
                        status = it.status,
                    )
                }.toList(),
                endSubmitDate = gameVos[0].endSubmitDate,
            )
        }

    @Transactional(readOnly = true)
    override suspend fun findAll(page: Int, size: Int) =
        catch {
            gameDao.findGame(size, page * size).toList()
                .groupBy { it.uuid }
                .map {
                    GameDtoRes(
                        uuid = it.value[0].uuid,
                        owner = it.value[0].owner,
                        guestName = it.value[0].guestName,
                        hostName = it.value[0].hostName,
                        sportType = it.value[0].sportType,
                        betItems = it.value.map { gameVo ->
                            BetItemDtoRes(
                                uuid = gameVo.betItemUuid,
                                type = gameVo.type,
                                value = gameVo.value,
                                odds = gameVo.odds,
                                status = gameVo.status,
                            )
                        }.toList(),
                        endSubmitDate = it.value[0].endSubmitDate,
                    )
                }
        }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun save(gameDto: GameDto, email: String) =
        catch {
            val gameDo = gameDao.save(gameDto.toGameDo(email))
            gameDto.betItems
                .map {
                    BetItemDo(null,
                        randomUUID(),
                        gameDo.id!!,
                        email,
                        it.type,
                        it.value,
                        it.odds,
                        BetItemStatus.ENABLE)
                }
                .map { betItemDao.save(it).uuid }

            gameDo.uuid
        }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun updateGameStatus(uuid: String, email: String, status: BetItemStatus) =
        catch {
            val game = gameDao.findByUuid(uuid) ?: throw IllegalStateException()
            game.owner.takeIf { it == email } ?: throw IllegalStateException()

            betItemDao.findAllByGameId(game.id!!)
                .toList()
                .forEach {
                    it.status = status
                    betItemDao.save(it).uuid
                }

            uuid
        }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun updateBetItemsStatus(
        uuids: List<String>,
        email: String,
        status: BetItemStatus,
    ) = catch {
        uuids.forEach { uuid ->
            val betItemDo = betItemDao.findByUuid(uuid) ?: throw IllegalStateException()
            betItemDo.takeIf { it.owner == email } ?: throw IllegalStateException()
            betItemDo.status = status

            betItemDao.save(betItemDo)
        }

        uuids
    }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun updateBetItemsOdds(
        uuids: List<String>,
        email: String,
        odds: List<BigDecimal>,
    ) = catch {
        uuids.forEachIndexed { index, uuid ->
            val betItemDo = betItemDao.findByUuid(uuid) ?: throw IllegalStateException()
            betItemDo.takeIf { it.owner == email } ?: throw IllegalStateException()
            betItemDo.odds = odds[index]

            betItemDao.save(betItemDo)
        }

        uuids
    }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun updateBetItemsResult(
        gameUuid: String,
        betItemUuids: List<String>,
        email: String,
    ) = catch {
        val game = gameDao.findByUuid(gameUuid) ?: throw IllegalStateException()
        game.takeIf { it.owner == email } ?: throw IllegalStateException()

        betItemDao.findAllByGameId(game.id!!).toList()
            .map {
                if (betItemUuids.contains(it.uuid)) {
                    it.status = BetItemStatus.WIN
                } else {
                    it.status = BetItemStatus.NO_WIN
                }
                betItemDao.save(it)
            }

        betItemUuids
    }
}