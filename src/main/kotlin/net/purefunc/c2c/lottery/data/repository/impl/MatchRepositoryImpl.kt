package net.purefunc.c2c.lottery.data.repository.impl

import arrow.core.Either.Companion.catch
import net.purefunc.c2c.lottery.data.dao.BetItemDao
import net.purefunc.c2c.lottery.data.dao.MatchDao
import net.purefunc.c2c.lottery.data.dto.MatchDto
import net.purefunc.c2c.lottery.data.repository.MatchRepository
import net.purefunc.c2c.lottery.data.table.BetItemDo
import org.springframework.stereotype.Repository

@Repository
class MatchRepositoryImpl(
    private val matchDao: MatchDao,
    private val betItemDao: BetItemDao,
) : MatchRepository {

    override suspend fun findByUuid(uuid: String) =
        catch {
            matchDao.findMatchDtoResByUuid(uuid) ?: throw IllegalStateException("")
        }

    override suspend fun findAll() =
        catch {
            matchDao.findMatchDtoRes()
        }

    override suspend fun save(matchDto: MatchDto) =
        catch {
            val matchDo = matchDao.save(matchDto.toMatchDo())
            matchDto.betItems
                .map { BetItemDo(null, matchDo.id!!, it.type, it.value, it.odds) }
                .forEach { betItemDao.save(it) }

            matchDto.toMatchDtoRes(matchDo.uuid)
        }
}