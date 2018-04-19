package cn.wzy.dao;

import cn.wzy.entity.Blog_Info;
import org.cn.wzy.dao.BaseDao;

public interface Blog_InfoDao extends BaseDao<Blog_Info> {
    void addLookNum();

    String getAdminPassword();
}