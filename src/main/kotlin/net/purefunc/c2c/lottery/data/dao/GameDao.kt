package net.purefunc.c2c.lottery.data.dao

import net.purefunc.c2c.lottery.data.dto.response.GameDtoRes
import net.purefunc.c2c.lottery.data.table.GameDo
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface GameDao : CoroutineCrudRepository<GameDo, Long> {

    @Query("SELECT g.uuid, g.guest_name, g.host_name, g.sport_type, b.type, b.value, b.odds, g.start_date, g.end_date FROM game g INNER JOIN BET_ITEM b ON g.id = b.match_id WHERE g.uuid = :uuid")
    fun findGameDtoResByUuid(uuid: String): GameDtoRes?

    @Query("SELECT g.uuid, g.guest_name, g.host_name, g.sport_type, b.type, b.value, b.odds, g.start_date, g.end_date FROM game g INNER JOIN bet_item b ON g.id = b.match_id ORDER BY g.end_date DESC")
    fun findGameDtoRes(): List<GameDtoRes>
}
