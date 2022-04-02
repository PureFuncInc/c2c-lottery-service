package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.dto.response.MatchDtoRes
import net.purefunc.c2c.lottery.data.table.MatchDo
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface MatchDao : CoroutineCrudRepository<MatchDo, Long> {

    @Query("SELECT g.uuid, g.guest_name, g.host_name, g.sport_type, b.type, b.value, b.odds, g.start_date, g.end_date FROM game g INNER JOIN BET_ITEM b ON g.id = b.match_id WHERE g.uuid = :uuid")
    fun findMatchDtoResByUuid(uuid: String): MatchDtoRes?

    @Query("SELECT g.uuid, g.guest_name, g.host_name, g.sport_type, b.type, b.value, b.odds, g.start_date, g.end_date FROM game g INNER JOIN bet_item b ON g.id = b.match_id ORDER BY g.end_date DESC")
    fun findMatchDtoRes(): List<MatchDtoRes>
}
