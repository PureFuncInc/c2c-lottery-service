package net.purefunc.c2c.lottery.data.repository.impl

import arrow.core.Either.Companion.catch
import kotlinx.coroutines.flow.toList
import net.purefunc.c2c.lottery.data.dao.BetItemDao
import net.purefunc.c2c.lottery.data.dao.OrderDao
import net.purefunc.c2c.lottery.data.dao.SlipDao
import net.purefunc.c2c.lottery.data.dto.OrderDto
import net.purefunc.c2c.lottery.data.dto.response.OrderDtoRes
import net.purefunc.c2c.lottery.data.dto.response.SlipDtoRes
import net.purefunc.c2c.lottery.data.enu.OrderStatus
import net.purefunc.c2c.lottery.data.repository.OrderRepository
import net.purefunc.c2c.lottery.data.repository.WalletRepository
import net.purefunc.c2c.lottery.data.table.OrderDo
import net.purefunc.c2c.lottery.data.table.SlipDo
import net.purefunc.c2c.lottery.ext.combinations
import net.purefunc.c2c.lottery.ext.genUnixMilli
import net.purefunc.c2c.lottery.ext.randomUUID
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Repository
class OrderRepositoryImpl(
    private val orderDao: OrderDao,
    private val betItemDao: BetItemDao,
    private val slipDao: SlipDao,
    private val walletRepository: WalletRepository,
) : OrderRepository {

    @Transactional(readOnly = true)
    override suspend fun findByUuid(uuid: String, email: String) =
        catch {
            val orderVos = orderDao.findOrderByUuid(uuid, email).toList()
            if (orderVos.isEmpty()) throw NoSuchElementException()

            OrderDtoRes(
                uuid = orderVos[0].uuid,
                email = orderVos[0].email,
                combination = orderVos[0].combination.split(",").toList(),
                multiple = orderVos[0].multiple,
                totalAmount = orderVos[0].totalAmount,
                winAmount = orderVos[0].winAmount,
                status = orderVos[0].status,
                createDate = orderVos[0].createDate,
                slips = orderVos.map {
                    SlipDtoRes(
                        it.guestName,
                        it.hostName,
                        it.sportType,
                        it.type,
                        it.value,
                        it.odds,
                    )
                }.toList()
            )
        }

    @Transactional(readOnly = true)
    override suspend fun findAll(email: String, page: Int, size: Int) =
        catch {
            orderDao.findOrder(email, size, page * size).toList()
                .groupBy { it.uuid }
                .map {
                    OrderDtoRes(
                        uuid = it.value[0].uuid,
                        email = it.value[0].email,
                        combination = it.value[0].combination.split(",").toList(),
                        multiple = it.value[0].multiple,
                        totalAmount = it.value[0].totalAmount,
                        winAmount = it.value[0].winAmount,
                        status = it.value[0].status,
                        createDate = it.value[0].createDate,
                        slips = it.value.map { orderVo ->
                            SlipDtoRes(
                                orderVo.guestName,
                                orderVo.hostName,
                                orderVo.sportType,
                                orderVo.type,
                                orderVo.value,
                                orderVo.odds
                            )
                        }.toList()
                    )
                }

        }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun save(orderDto: OrderDto, email: String) =
        catch {
            val totalCount = orderDto.combination
                .map { it.toInt() }
                .map { combinations[orderDto.betItemUuids.size][it] }
                .toList()
                .sum()

            val totalAmount = 10 * orderDto.multiple * totalCount

            val save = orderDao.save(
                OrderDo(
                    id = null,
                    uuid = randomUUID(),
                    email = email,
                    multiple = orderDto.multiple,
                    combination = orderDto.combination.joinToString(","),
                    totalAmount = totalAmount,
                    winAmount = BigDecimal.ZERO,
                    status = OrderStatus.INIT,
                    createDate = genUnixMilli()
                )
            )
            orderDto.betItemUuids
                .map { betItemDao.findByUuid(it) ?: throw IllegalStateException() }
                .map { slipDao.save(SlipDo(null, randomUUID(), save.id!!, it.id!!)) }

            walletRepository.payForOrder(save.email, save.uuid, save.totalAmount)

            save.uuid
        }

    @Transactional(rollbackFor = [Exception::class])
    override suspend fun deleteByUuid(uuid: String, email: String) =
        catch {
            val order = orderDao.findByUuid(uuid) ?: throw IllegalStateException()
            if (order.email != email) throw IllegalStateException()

            order.status = OrderStatus.CANCEL
            val save = orderDao.save(order)

            walletRepository.cancelOrder(save.email, save.uuid, save.totalAmount)

            save.uuid
        }
}