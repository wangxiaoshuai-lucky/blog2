package cn.wzy.controller;

import cn.wzy.BaseControlTest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @author wzy
 * @Date 2018/4/10 14:09
 */
public class BlogControllerTest extends BaseControlTest {

    @Test
    public void test() throws Exception {
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/blog/queryCountByCondition.do")
                .param("start","1")
                .param("rows","10"));
//                .param("tag","spring笔记"));
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void leaveTest() throws Exception {
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/leave/queryByCondition.do")
                .param("start","2")
                .param("rows","3"));
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void addLeaveMessage() throws Exception {
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/leave/queryCount.do"));
//                        .param("start","2")
//                        .param("rows","3"));
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
    @Test
    public void addBlog() throws Exception {
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/blog/addBlog.do")
                        .param("article","2565656")
                        .param("password","1123")
                        .param("title","555")
                        .param("tag","5669845"));
        System.out.println(resultActions.andReturn().getResponse().getContentAsString());
    }
}
