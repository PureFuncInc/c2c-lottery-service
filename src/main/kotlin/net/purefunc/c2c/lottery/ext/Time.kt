package net.purefunc.c2c.lottery.ext

import java.time.Instant

fun genUnixMilli() = Instant.now().toEpochMilli()