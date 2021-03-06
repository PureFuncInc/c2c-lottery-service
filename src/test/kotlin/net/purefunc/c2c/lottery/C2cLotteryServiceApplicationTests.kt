package net.purefunc.c2c.lottery

import com.google.common.collect.ImmutableSet
import com.google.common.collect.Sets
import net.purefunc.core.ext.genUnixMilli
import org.junit.jupiter.api.Test

//@SpringBootTest
class C2cLotteryServiceApplicationTests {

    @Test
    fun contextLoads() {
        val combinations = Sets.combinations(ImmutableSet.of(1, 2, 3, 4), 3)
        println(combinations.size)

        println(genUnixMilli())
    }

}
