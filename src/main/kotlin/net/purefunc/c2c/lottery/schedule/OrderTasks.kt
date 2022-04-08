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
    private val winFilter = listOf(BetItemStatus.WIN, BetItemStatus.CANCEL)

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

    @Scheduled(cron = "*/10 * * * * *")
    fun calculateOrderWinAmountPart2() =
        runBlocking {
            orderDao.findOrderOdds(OrderStatus.DRAWING)
                .toList()
                .groupBy { it.uuid }
                .map {
                    var winAmount = BigDecimal.ZERO
                    var cancelAmount = BigDecimal.ZERO
                    it.value[0].combination.split(",")
                        .map { combination ->
                            when (combination) {
                                "1" ->
                                    Sets.combinations(ImmutableSet.copyOf(it.value), 1)
                                        .map { case: MutableSet<OrderOddsVo> ->
                                            case.map { element ->
                                                if (element.status == BetItemStatus.WIN) {
                                                    val singleWinAmount = BigDecimal.TEN
                                                        .multiply(element.multiple.toBigDecimal())
                                                        .multiply(element.odds)

                                                    winAmount = winAmount.plus(singleWinAmount)
                                                } else if (element.status == BetItemStatus.CANCEL) {
                                                    val singleCancelAmount = BigDecimal.TEN
                                                        .multiply(element.multiple.toBigDecimal())

                                                    cancelAmount = cancelAmount.plus(singleCancelAmount)
                                                }
                                            }
                                        }
                                "2" ->
                                    Sets.combinations(ImmutableSet.copyOf(it.value), 2)
                                        .map { case: MutableSet<OrderOddsVo> ->
                                            val winSlips = case.filter { element ->
                                                winFilter.contains(element.status)
                                            }.toList()

                                            winSlips.takeIf { slips -> slips.size == 2 }?.run {
                                                val finalOdds = this.map { odds ->
                                                    if (odds.status == BetItemStatus.CANCEL) {
                                                        odds.copy(odds = BigDecimal.ONE)
                                                    } else {
                                                        odds
                                                    }
                                                }

                                                winAmount = winAmount.plus(
                                                    BigDecimal.TEN.multiply(finalOdds[0].multiple.toBigDecimal())
                                                        .multiply(finalOdds[0].odds)
                                                        .multiply(finalOdds[1].odds)
                                                )
                                            }
                                        }
                                else -> throw IllegalStateException()
                            }
                        }
                    val order = orderDao.findByUuid(it.key) ?: throw IllegalStateException()
                    if (winAmount > BigDecimal.ZERO) {
                        order.status = OrderStatus.WIN
                        order.winAmount = winAmount
                        // TODO: cancel amount
                    } else {
                        order.status = OrderStatus.NO_WIN
                    }
                    orderDao.save(order)
                }
        }
}