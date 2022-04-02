package net.purefunc.c2c.lottery

import net.purefunc.c2c.lottery.ext.genUnixMilli
import org.junit.jupiter.api.Test

//@SpringBootTest
class C2cLotteryServiceApplicationTests {

    @Test
    fun contextLoads() {
        println(genUnixMilli())
    }

}
