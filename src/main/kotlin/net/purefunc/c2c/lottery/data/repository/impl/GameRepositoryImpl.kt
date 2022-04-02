package net.purefunc.c2c.lottery.data.repository.impl

import arrow.core.Either.Companion.catch
import net.purefunc.c2c.lottery.data.dao.BetItemDao
import net.purefunc.c2c.lottery.data.dao.GameDao
import net.purefunc.c2c.lottery.data.dto.GameDto
import net.purefunc.c2c.lottery.data.repository.GameRepository
import net.purefunc.c2c.lottery.data.table.BetItemDo
import org.springframework.stereotype.Repository

@Repository
class GameRepositoryImpl(
    private val gameDao: GameDao,
    private val betItemDao: BetItemDao,
) : GameRepository {

    override suspend fun findByUuid(uuid: String) =
        catch {
            gameDao.findGameDtoResByUuid(uuid) ?: throw IllegalStateException("")
        }

    override suspend fun findAll() =
        catch {
            gameDao.findGameDtoRes()
        }

    override suspend fun save(gameDto: GameDto) =
        catch {
            val matchDo = gameDao.save(gameDto.toGameDo())
            gameDto.betItems
                .map { BetItemDo(null, matchDo.id!!, it.type, it.value, it.odds) }
                .forEach { betItemDao.save(it) }

            gameDto.toGameDtoRes(matchDo.uuid)
        }
}