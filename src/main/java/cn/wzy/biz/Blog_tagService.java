package cn.wzy.biz;

import cn.wzy.dao.Blog_tagDao;
import cn.wzy.entity.Blog_tag;
import cn.wzy.model.ResultModel;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:43
 */
@Service
public class Blog_tagService {

    @Autowired
    private Blog_tagDao dao;

    public ResultModel<Blog_tag> getAllTags() {
        ResultModel<Blog_tag> result = new ResultModel<>();
        List<Blog_tag> tags = dao.selectByCondition(new BaseQuery<>(Blog_tag.class));
        return result.setData(tags).setCode(ResultModel.SUCCESS).setTotal(tags.size());
    }
}
