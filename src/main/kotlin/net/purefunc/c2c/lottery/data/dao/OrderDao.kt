package net.purefunc.c2c.lottery.data.dao

import kotlinx.coroutines.flow.Flow
import net.purefunc.c2c.lottery.data.table.OrderDo
import net.purefunc.c2c.lottery.data.vo.OrderVo
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface OrderDao : CoroutineCrudRepository<OrderDo, Long> {

    @Query("SELECT " +
            "so.uuid, so.email, so.type, so.multiple, so.create_date, g.guest_name, g.host_name, g.sport_type, bi.type, bi.value, bi.odds " +
            "FROM " +
            "sport_order so INNER JOIN slip sp ON so.id = sp.order_id " +
            "INNER JOIN bet_item bi ON sp.bet_item_id = bi.id " +
            "INNER JOIN game g ON bi.game_id = g.id " +
            "WHERE so.uuid = :uuid")
    fun findOrderByUuid(uuid: String): Flow<OrderVo>

    @Query("SELECT " +
            "so.uuid, so.email, so.type, so.multiple, so.create_date, g.guest_name, g.host_name, g.sport_type, bi.type, bi.value, bi.odds " +
            "FROM " +
            "(SELECT soo.uuid, soo.email, soo.type, soo.multiple, soo.create_date FROM sport_order soo ORDER BY soo.create_date DESC LIMIT :limit OFFSET :offset) so " +
            "INNER JOIN slip sp ON so.id = sp.order_id " +
            "INNER JOIN bet_item bi ON sp.bet_item_id = bi.id " +
            "INNER JOIN game g ON bi.game_id = g.id " +
            "WHERE so.uuid = :uuid")
    fun findOrder(limit: Int, offset: Int): Flow<OrderVo>
}
