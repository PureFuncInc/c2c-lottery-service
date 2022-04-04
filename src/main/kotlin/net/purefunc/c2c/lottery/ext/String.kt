package net.purefunc.c2c.lottery.ext

import java.util.UUID
import kotlin.random.Random.Default.nextInt

fun randomUUID() = UUID.randomUUID().toString()

private val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
fun randomAlphanumeric(length: Int) = (1..length)
    .map { nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")
