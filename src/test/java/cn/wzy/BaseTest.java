package cn.wzy;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
abstract public class BaseTest {

    @Before
    public void before() {
        System.out.println("=============start=====test==========");
    }

    @After
    public void after() {
        System.out.println("=============start======end==========");
    }
}
