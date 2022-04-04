package net.purefunc.c2c.lottery

import com.google.common.collect.ImmutableSet
import com.google.common.collect.Sets
import net.purefunc.c2c.lottery.ext.genUnixMilli
import org.junit.jupiter.api.Test

//@SpringBootTest
class C2cLotteryServiceApplicationTests {

    @Test
    fun contextLoads() {
        val combinations = Sets.combinations(ImmutableSet.of(0, 1, 2, 3, 4, 5), 3)
        println(combinations.toString())

        println(genUnixMilli())
    }

}
