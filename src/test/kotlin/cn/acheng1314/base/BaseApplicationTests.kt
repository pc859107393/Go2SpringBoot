package cn.acheng1314.base

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [BaseApplication::class])
class BaseApplicationTests {

    @Test
    fun contextLoads() {
    }

}
