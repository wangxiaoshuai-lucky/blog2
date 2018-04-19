package cn.wzy.dao;

import cn.wzy.entity.Myblog;
import org.cn.wzy.dao.BaseDao;

public interface MyblogDao extends BaseDao<Myblog> {

    void addLookNum(int id);

    void addCommentNum(int id);
}