package cn.wzy.biz;

import cn.wzy.dao.User_InfoDao;
import cn.wzy.entity.User_Info;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:44
 */
@Service
public class User_InfoService {


    @Autowired
    private User_InfoDao dao;

    public Integer reg(User_Info record) {
        BaseQuery<User_Info> query = new BaseQuery<>(User_Info.class);
        query.getQuery().setUsername(record.getUsername());
        if (dao.selectByCondition(query) == null || dao.selectByCondition(query).size() == 0) {
            dao.insertSelective(record.setAccess(1));
            return 1;
        }
        return 0;
    }

    public int login(User_Info user, HttpServletRequest request) {
        BaseQuery<User_Info> query = new BaseQuery<>(User_Info.class);
        query.getQuery().setUsername(user.getUsername());
        if (dao.selectByCondition(query) == null || dao.selectByCondition(query).size() == 0) {
            return 0;//用户不存在
        }
        query.getQuery().setPassword(user.getPassword());
        List<User_Info> user_info = dao.selectByCondition(query);
        if (user_info == null || user_info.size() == 0) {
            return 2;//密码错误
        }
        request.getSession().setAttribute("id",user_info.get(0).getId());
        return 1;
    }

    public User_Info getUserInfo(int id) {
        return this.dao.selectByPrimaryKey(id);
    }
}
