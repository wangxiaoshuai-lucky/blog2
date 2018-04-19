package cn.wzy.controller;

import cn.wzy.biz.Leaving_messageService;
import cn.wzy.entity.Leaving_message;
import cn.wzy.model.ResultModel;
import lombok.extern.log4j.Log4j;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;

/**
 * @author wzy
 * @Date 2018/4/10 14:33
 */
@Log4j
@Controller
@RequestMapping("/leave")
public class Leaving_messageController extends BaseController {

    @Autowired
    private Leaving_messageService leavingService;

    /**
     * 通过页数获取留言
     * @param query
     * @param leave
     * @return
     */
    @ResponseBody
    @RequestMapping("queryByCondition.do")
    public ResultModel<Leaving_message> queryByCondition(BaseQuery<Leaving_message> query, Leaving_message leave) {
        query.setQuery(leave);
        return leavingService.queryByCondition(query).setOnline_num(online_num);
    }

    /**
     * 添加留言 false为验证码错误
     * @param userstr
     * @param record
     * @return
     */
    @ResponseBody
    @RequestMapping("addLeaveMessage.do")
    public ResultModel<Boolean> addLeaveMessage(String userstr, Leaving_message record) {
        ResultModel<Boolean> result = new ResultModel<>();;
        String randomString = (String) getRequest().getSession(true).getAttribute("randomString");
        //验证一次就销毁，防止重复提交
        getRequest().getSession(true).removeAttribute("randomString");
        if (randomString == null || !randomString.equals(userstr))
            return result.setData(Arrays.asList(false))
                    .setCode(ResultModel.FAILED + "验证码错误")
                    .setOnline_num(online_num);
        record.setAddtime(new Date().getTime())
                .setIp(this.getRequest().getRemoteAddr());
        log.info("ip为：" + getRequest().getRemoteAddr() + "给你留言" + record.getContent());
        return result.setData(Arrays.asList(leavingService.addLeaveMessage(record)))
                .setCode(ResultModel.SUCCESS)
                .setOnline_num(online_num);
    }

    /**
     * 获取留言板上的记录条数
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryCount.do")
    public ResultModel<Integer> queryCount(BaseQuery<Leaving_message> query, Leaving_message record) {
        query.setQuery(record);
        return leavingService.queryCountByCondition(query).setOnline_num(online_num);
    }
}
