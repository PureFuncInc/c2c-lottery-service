package net.purefunc.c2c.lottery.data.repository

import arrow.core.Either
import net.purefunc.c2c.lottery.data.dto.OrderDto
import net.purefunc.c2c.lottery.data.dto.response.OrderDtoRes

interface OrderRepository {

    suspend fun findByUuid(uuid: String): Either<Throwable, OrderDtoRes>

    suspend fun findAll(page: Int, size: Int): Either<Throwable, List<OrderDtoRes>>

    suspend fun save(orderDto: OrderDto, email: String): Either<Throwable, String>

    suspend fun deleteByUuid(uuid: String, email: String): Either<Throwable, String>
}