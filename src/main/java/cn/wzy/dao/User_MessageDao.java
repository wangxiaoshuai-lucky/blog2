package cn.wzy.dao;

import cn.wzy.entity.User_Message;
import cn.wzy.vo.User_MessageUI;
import org.cn.wzy.dao.BaseDao;

import java.util.List;

public interface User_MessageDao extends BaseDao<User_Message> {
    List<User_MessageUI> getComments(int id);
}