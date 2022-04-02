package net.purefunc.c2c.lottery.ext

import java.security.Principal

fun Principal.memberId() = this.name.toLong()