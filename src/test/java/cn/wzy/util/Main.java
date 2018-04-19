package cn.wzy.util;

import org.cn.wzy.util.MapperingGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wzy
 * @Date 2018/4/10 11:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Main {

    @Autowired
    private  MapperingGenerator mapperingGenerator;

    @Test
    public void run() {
        MapperingGenerator.oldPath = "F:\\blog2\\src\\main\\resources\\mapper";
        MapperingGenerator.implPath = MapperingGenerator.oldPath + "\\impl";
        MapperingGenerator.sql = MapperingGenerator.oldPath + "\\sql";
        mapperingGenerator.run();
    }
}
