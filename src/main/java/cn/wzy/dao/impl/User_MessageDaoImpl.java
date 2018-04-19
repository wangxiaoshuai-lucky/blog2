package cn.wzy.dao.impl;

import cn.wzy.dao.User_MessageDao;
import cn.wzy.entity.User_Message;
import cn.wzy.vo.User_MessageUI;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:12
 */
@Repository
public class User_MessageDaoImpl extends BaseDaoImpl<User_Message>
        implements User_MessageDao{
    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.User_MessageDao";
    }

    @Override
    public List<User_MessageUI> getComments(int id) {
        return getSqlSession().selectList(getNameSpace() + ".getComments",id);
    }
}
