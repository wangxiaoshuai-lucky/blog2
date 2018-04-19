package cn.wzy.biz;

import cn.wzy.dao.MyblogDao;
import cn.wzy.dao.User_MessageDao;
import cn.wzy.entity.User_Message;
import cn.wzy.vo.User_MessageUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:44
 */
@Service
public class User_MessageService {

    @Autowired
    private User_MessageDao dao;

    @Autowired
    private MyblogDao myblogDao;

    /**
     * 获取微博的评论说明正在访问，更新blog的访问量
     * @param query
     * @return
     */
    public List<User_MessageUI> queryComments(User_Message query) {
        return dao.getComments(query.getBlogId());
    }

    /**
     * 添加评论
     * @param record
     * @return
     */
    public int comment(User_Message record) {
        //更新评论量
        myblogDao.addCommentNum(record.getBlogId());
        return dao.insertSelective(record);
    }
}
