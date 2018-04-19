package cn.wzy.controller;

import cn.wzy.biz.User_MessageService;
import cn.wzy.entity.User_Message;
import cn.wzy.model.ResultModel;
import cn.wzy.vo.User_MessageUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/12 19:07
 */
@Controller
@RequestMapping("/message")
public class UserMessageController extends BaseController {

    @Autowired
    private User_MessageService service;


    /**
     * 获取该blog的所有评论
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryComments.do")
    public ResultModel<User_MessageUI> queryAllComment(User_Message query) {
        List<User_MessageUI> list = service.queryComments(query);
        return new ResultModel<User_MessageUI>().setData(list)
                .setTotal(list.size())
                .setCode(ResultModel.SUCCESS)
                .setOnline_num(online_num);
    }
}
