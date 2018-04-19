package cn.wzy.dao.impl;

import cn.wzy.dao.MyblogDao;
import cn.wzy.entity.Myblog;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author wzy
 * @Date 2018/4/10 12:17
 */
@Repository
public class MyblogDaoImpl extends BaseDaoImpl<Myblog>
        implements MyblogDao {
    @Override
    public void addCommentNum(int id) {
        getSqlSession().update(getNameSpace() + ".addComment",id);
    }

    @Override
    public void addLookNum(int id) {
        getSqlSession().update(getNameSpace() + ".addlook",id);
    }

    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.MyblogDao";
    }
}
