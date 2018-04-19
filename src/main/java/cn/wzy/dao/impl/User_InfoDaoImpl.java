package cn.wzy.dao.impl;

import cn.wzy.dao.User_InfoDao;
import cn.wzy.entity.User_Info;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author wzy
 * @Date 2018/4/10 12:17
 */
@Repository
public class User_InfoDaoImpl extends BaseDaoImpl<User_Info>
        implements User_InfoDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.User_InfoDao";
    }
}
