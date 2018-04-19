package cn.wzy.biz;

import cn.wzy.BaseTest;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzy
 * @Date 2018/4/10 12:57
 */
public class Blog_InfoTest extends BaseTest {

    @Autowired
    private Blog_InfoService service;

    @Test
    public void test() {
        System.out.println(JSON.toJSON(service.getBlog_Info()));
    }
}
