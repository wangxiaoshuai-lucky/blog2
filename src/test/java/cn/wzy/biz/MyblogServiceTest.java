package cn.wzy.biz;

import cn.wzy.BaseTest;
import cn.wzy.entity.Myblog;
import com.alibaba.fastjson.JSON;
import org.cn.wzy.query.BaseQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wzy
 * @Date 2018/4/10 13:19
 */
public class MyblogServiceTest extends BaseTest{
    @Autowired
    private MyblogService service;

    @Autowired
    private Blog_tagService tagService;

    @Test
    public void queryBlogByConditiontest() {
        BaseQuery<Myblog> query = new BaseQuery<>(Myblog.class);
        query.setStart(1).setRows(10);
        System.out.println(JSON.toJSON(service.queryBlogByCondition(query)));
    }

    @Test
    public void queryById() {
        System.out.println(JSON.toJSON(service.queryById(1)));
    }

    @Test
    public void getAll() {
        System.out.println(JSON.toJSON(tagService.getAllTags()));
    }

}
