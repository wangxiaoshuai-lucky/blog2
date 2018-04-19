package cn.wzy.dao.impl;

import cn.wzy.dao.Blog_InfoDao;
import cn.wzy.entity.Blog_Info;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author wzy
 * @Date 2018/4/10 12:14
 */
@Repository
public class Blog_InfoDaoImpl extends BaseDaoImpl<Blog_Info>
        implements Blog_InfoDao{
    @Override
    public void addLookNum() {
        getSqlSession().update(getNameSpace() + ".addlook");
    }

    @Override
    public String getAdminPassword() {
        return getSqlSession().selectOne(getNameSpace() + ".getAdminPassword");
    }

    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.Blog_InfoDao";
    }
}
