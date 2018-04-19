package cn.wzy.biz;

import cn.wzy.dao.Leaving_messageDao;
import cn.wzy.entity.Leaving_message;
import cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:44
 */
@Service
public class Leaving_messageService {

    @Autowired
    private Leaving_messageDao dao;

    /**
     * 通过页数获取留言
     * @param query
     * @return
     */
    public ResultModel<Leaving_message> queryByCondition(BaseQuery<Leaving_message> query) {
        if (query.getStart() != null && query.getRows() != null) {
            query.setStart((query.getStart() - 1) * query.getRows());
        }
        ResultModel<Leaving_message> result = new ResultModel<>();
        List<Leaving_message> list = dao.selectByCondition(query);
        if (list == null) {
            return result
                    .setCode(ResultModel.FAILED + "页面参数有误(start应该 <= 1)")
                    .setData(null).setTotal(0);
        }
        return result.setData(list).setCode(ResultModel.SUCCESS).setTotal(list.size());
    }

    /**
     * 添加评论
     * @param record
     * @return
     */
    public Boolean addLeaveMessage(Leaving_message record) {
        return dao.insertSelective(record) == 1 ? true : false;
    }

    /**
     * 所有留言条数
     * @param query
     * @return
     */
    public ResultModel<Integer> queryCountByCondition(BaseQuery<Leaving_message> query) {
        if (query.getStart() != null && query.getRows() != null) {
            query.setStart((query.getStart() - 1) * query.getRows());
        }
        return new ResultModel<Integer>()
                .setData(Arrays.asList(dao.selectCountByCondition(query)))
                .setCode(ResultModel.SUCCESS);
    }
}
