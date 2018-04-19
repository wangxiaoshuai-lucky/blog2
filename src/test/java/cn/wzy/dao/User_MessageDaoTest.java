package cn.wzy.dao;

import cn.wzy.BaseTest;
import cn.wzy.dao.impl.Blog_InfoDaoImpl;
import cn.wzy.entity.User_Info;
import cn.wzy.vo.User_MessageUI;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:20
 */
public class User_MessageDaoTest extends BaseTest {
    @Autowired
    private User_MessageDao dao;
    @Autowired
    private User_InfoDao user_infoDao;

    @Test
    public void test() {
        System.out.println(dao.selectByPrimaryKey(1));
    }
    @Test
    public void test1() {
        System.out.println(user_infoDao.selectByPrimaryKey(1));
    }

    @Test
    public void Applicationtest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Blog_InfoDao blog_InfoDao = (Blog_InfoDaoImpl)context.getBean("blog_InfoDaoImpl");
        System.out.println(blog_InfoDao);
    }

    @Test
    public void test5() {
        List<User_MessageUI> list = dao.getComments(1);
        return;
    }
}
