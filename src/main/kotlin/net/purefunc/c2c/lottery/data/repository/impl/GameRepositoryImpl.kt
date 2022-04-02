package net.purefunc.c2c.lottery.data.repository.impl

import arrow.core.Either.Companion.catch
import kotlinx.coroutines.flow.toList
import net.purefunc.c2c.lottery.data.dao.BetItemDao
import net.purefunc.c2c.lottery.data.dao.GameDao
import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.data.table.BetItemDo
import net.purefunc.c2c.lottery.data.vo.BetItemVo
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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
                guestName = gameVos[0].guestName,
                hostName = gameVos[0].hostName,
                sportType = gameVos[0].sportType,
                betItems = gameVos.map { BetItemVo(it.type, it.value, it.odds) }.toList(),
                endSubmitDate = gameVos[0].endSubmitDate,
            )
        }

    @Transactional(readOnly = true)
    override suspend fun findAll() =
        catch {
            gameDao.findGame().toList()
                .groupBy { it.uuid }
                .map {
                    GameDtoRes(
                        uuid = it.value[0].uuid,
                        guestName = it.value[0].guestName,
                        hostName = it.value[0].hostName,
                        sportType = it.value[0].sportType,
                        betItems = it.value.map { gameVo -> BetItemVo(gameVo.type, gameVo.value, gameVo.odds) }
                            .toList(),
                        endSubmitDate = it.value[0].endSubmitDate,
                    )
                }
        }

    @Transactional
    override suspend fun save(gameDto: GameDto) =
        catch {
            val matchDo = gameDao.save(gameDto.toGameDo())
            gameDto.betItems
                .map { BetItemDo(null, matchDo.id!!, it.type, it.value, it.odds) }
                .forEach { betItemDao.save(it) }

            gameDto.toGameDtoRes(matchDo.uuid)
        }
}