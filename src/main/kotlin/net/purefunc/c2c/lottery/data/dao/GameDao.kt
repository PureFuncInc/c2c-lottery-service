package net.purefunc.c2c.lottery.data.dao

import kotlinx.coroutines.flow.Flow
import net.purefunc.c2c.lottery.data.table.GameDo
import net.purefunc.c2c.lottery.data.vo.GameVo
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface GameDao : CoroutineCrudRepository<GameDo, Long> {

    @Query("SELECT " +
            "g.uuid, g.owner, g.guest_name, g.host_name, g.sport_type, g.end_submit_date, b.uuid as bet_item_uuid, b.type, b.value, b.odds, b.status " +
            "FROM " +
            "game g INNER JOIN BET_ITEM b ON g.id = b.game_id " +
            "WHERE g.uuid = :uuid")
    fun findGameByUuid(uuid: String): Flow<GameVo>

    @Query("SELECT " +
            "g.uuid, g.owner, g.guest_name, g.host_name, g.sport_type, g.end_submit_date, b.uuid as bet_item_uuid, b.type, b.value, b.odds, b.status " +
            "FROM " +
            "(SELECT * FROM game gg ORDER BY gg.end_submit_date DESC LIMIT :limit OFFSET :offset) g " +
            "INNER JOIN bet_item b ON g.id = b.game_id")
    fun findGame(limit: Int, offset: Int): Flow<GameVo>

    suspend fun findByUuid(uuid: String): GameDo?
}
