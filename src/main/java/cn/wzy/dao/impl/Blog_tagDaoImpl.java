package cn.wzy.dao.impl;

import cn.wzy.dao.Blog_tagDao;
import cn.wzy.entity.Blog_tag;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author wzy
 * @Date 2018/4/10 12:15
 */
@Repository
public class Blog_tagDaoImpl extends BaseDaoImpl<Blog_tag>
        implements Blog_tagDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.Blog_tagDao";
    }
}
