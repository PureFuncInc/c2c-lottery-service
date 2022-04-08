package net.purefunc.c2c.lottery.schedule

import com.google.common.collect.ImmutableSet
import com.google.common.collect.Sets
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import net.purefunc.c2c.lottery.data.dao.OrderDao
import net.purefunc.c2c.lottery.data.enu.BetItemStatus
import net.purefunc.c2c.lottery.data.enu.OrderStatus
import net.purefunc.c2c.lottery.data.vo.OrderOddsVo
import net.purefunc.core.ext.Slf4j
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Slf4j
@Service
class OrderTasks(
    private val orderDao: OrderDao,
) {

    private val statusFilter = listOf(BetItemStatus.WIN, BetItemStatus.NO_WIN, BetItemStatus.CANCEL)
    private val winFilter = listOf(BetItemStatus.WIN, BetItemStatus.NO_WIN)

    @Scheduled(cron = "0 */1 * * * *")
    fun calculateOrderWinAmountPart1() =
        runBlocking {
            orderDao.findOrder(OrderStatus.INIT)
                .toList()
                .groupBy { it.uuid }
                .filter {
                    it.value.none { orderResultVo -> !statusFilter.contains(orderResultVo.status) }
                }
                .map { it.key }
                .toList()
                .map {
                    val order = orderDao.findByUuid(it) ?: throw IllegalStateException()
                    order.status = OrderStatus.DRAWING
                    orderDao.save(order)
                }
        }

    //    @Scheduled(cron = "30 */1 * * * *")
    fun calculateOrderWinAmountPart2() =
        runBlocking {
            orderDao.findOrderOdds(OrderStatus.DRAWING)
                .toList()
                .groupBy { it.uuid }
                .map {
                    var winAmount = BigDecimal.ZERO
                    it.value[0].combination.split(",")
                        .map { combination ->
                            when (combination) {
                                "1" ->
                                    Sets.combinations(ImmutableSet.of(it.value), 1)
                                        .map { a: MutableSet<List<OrderOddsVo>> ->
                                            a.map { b: List<OrderOddsVo> ->
                                                b.forEach { c: OrderOddsVo ->
                                                    if (c.betItemStatus == BetItemStatus.WIN) {
                                                        winAmount =
                                                            winAmount.plus(
                                                                BigDecimal.TEN.multiply(c.multiple.toBigDecimal())
                                                                    .multiply(c.odds))
                                                    }
                                                }
                                            }
                                        }
                                "2" ->
                                    Sets.combinations(ImmutableSet.of(it.value), 2)
                                        .map { a: MutableSet<List<OrderOddsVo>> ->
                                            a.map { b: List<OrderOddsVo> ->
                                                val slips: List<OrderOddsVo> =
                                                    b.filter { c: OrderOddsVo -> winFilter.contains(c.betItemStatus) }
                                                        .toList()
                                                slips.takeIf { d -> d.size == 2 }?.run {
                                                    winAmount = winAmount.plus(
                                                        BigDecimal.TEN.multiply(slips[0].multiple.toBigDecimal())
                                                            .multiply(this[0].odds)
                                                            .multiply(this[1].odds)
                                                    )
                                                }
                                            }
                                        }
                                else -> throw IllegalStateException()
                            }
                        }
                    val order = orderDao.findByUuid(it.key) ?: throw IllegalStateException()
                    if (winAmount > BigDecimal.ZERO) {
                        order.status = OrderStatus.WIN
                        order.winAmount = winAmount
                    } else {
                        order.status = OrderStatus.NO_WIN
                    }
                    orderDao.save(order)
                }
        }
}