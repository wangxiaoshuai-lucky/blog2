package cn.wzy.dao.impl;

import cn.wzy.dao.Leaving_messageDao;
import cn.wzy.entity.Leaving_message;
import org.cn.wzy.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author wzy
 * @Date 2018/4/10 12:16
 */
@Repository
public class Leaving_messageDaoImpl extends BaseDaoImpl<Leaving_message>
        implements Leaving_messageDao {
    @Override
    public String getNameSpace() {
        return "cn.wzy.dao.Leaving_messageDao";
    }
}
