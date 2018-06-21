package cn.wzy.controller;

import cn.wzy.biz.Blog_InfoService;
import cn.wzy.biz.Blog_tagService;
import cn.wzy.biz.MyblogService;
import cn.wzy.biz.User_MessageService;
import cn.wzy.entity.Blog_tag;
import cn.wzy.entity.Myblog;
import cn.wzy.entity.User_Message;
import cn.wzy.model.ResultModel;
import cn.wzy.vo.Blog_InfoUI;
import cn.wzy.vo.MyblogUI;
import lombok.extern.log4j.Log4j;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 13:48
 */
@Log4j
@Controller
@RequestMapping("/blog")
public class BlogController extends BaseController{

    @Autowired
    private MyblogService myblogService;

    @Autowired
    private Blog_tagService blog_tagService;

    @Autowired
    private User_MessageService user_messageService;

    @Autowired
    private Blog_InfoService blog_infoService;


    /**
     * 获取blog主要信息（顶栏和浏览量）
     * @return
     */
    @RequestMapping("/getInfo.do")
    @ResponseBody
    public ResultModel<Blog_InfoUI> getBlog_Info() {
        return blog_infoService.getBlog_Info().setOnline_num(users.size());
    }
    /**
     * 通过条件查询相关blogs
     * @param query
     * @param blog
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryByCondition.do")
    public ResultModel<MyblogUI> getBlogsByCondition(BaseQuery<Myblog> query, Myblog blog) {
        query.setQuery(blog);
        return myblogService.queryBlogByCondition(query).setOnline_num(users.size());
    }

    /**
     * 获取所有的标签
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAllTags.do")
    public ResultModel<Blog_tag> getAllTags() {
        return blog_tagService.getAllTags().setOnline_num(users.size());
    }

    /**
     * 获取相关blog的数量
     * @param query
     * @param blog
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCountByCondition.do")
    public ResultModel<Integer> queryCountByCondition(BaseQuery<Myblog> query, Myblog blog) {
        query.setQuery(blog);
        return myblogService.queryCountByCondition(query).setOnline_num(users.size());
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryById.do")
    public ResultModel<MyblogUI> queryById(Integer id) {
        return myblogService.queryById(id).setOnline_num(users.size());
    }

    /**
     * 评论blog 1 成功 2 验证码错误 3 用户已失效
     * @param message
     * @param str
     * @return
     */
    @ResponseBody
    @RequestMapping("/comment.do")
    public ResultModel<Integer> comment(User_Message message, String str) throws IOException {
        ResultModel<Integer> result = new ResultModel<>();
        Integer id = (Integer) getRequest().getSession().getAttribute("id");
        if (id == null) {
            return result.setCode(ResultModel.FAILED + "用户未登录")
                    .setData(Arrays.asList(3))
                    .setOnline_num(users.size());
        }
        message.setCommenterId(id);
        String randomString = (String) getRequest().getSession(true).getAttribute("randomString");
        //验证一次就销毁，防止重复提交
        getRequest().getSession(true).removeAttribute("randomString");
        if (randomString == null || !randomString.equals(str.toLowerCase()))
            return result.setData(Arrays.asList(2))
                    .setCode(ResultModel.FAILED + "验证码错误")
                    .setOnline_num(users.size());
        message.setAddtime(new Date().getTime()).setIp(getRequest().getRemoteAddr());
        log.info("ip为：" + getRequest().getRemoteAddr() + "(" + queryAdress() + ")"+ "评论了你id为" + message.getBlogId() + "的blog");
        return result.setData(Arrays.asList(user_messageService.comment(message)))
                .setCode(ResultModel.SUCCESS)
                .setOnline_num(users.size());
    }


    /**
     * 写blog
     * @param record
     * @param article
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBlog.do",method = {RequestMethod.POST})
    public ResultModel<Integer> addBlog(Myblog record, String article, String password) {

        String ne = getRequest().getCharacterEncoding();

        return new ResultModel<Integer>()
                .setCode(ResultModel.SUCCESS)
                .setData(Arrays.asList(myblogService.addBlog(record,article,password)))
                .setOnline_num(users.size());
    }

    /**
     * 更新blog的关于信息
     * @param article
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update.do", method = {RequestMethod.POST})
    public ResultModel<Integer> update(String article, String password) {

        System.out.println(getRequest().getCharacterEncoding());
        return new ResultModel<Integer>()
                .setTotal(1)
                .setCode(ResultModel.SUCCESS)
                .setData(Arrays.asList(blog_infoService.updateInfo(article,password)))
                .setOnline_num(users.size());
    }
}
