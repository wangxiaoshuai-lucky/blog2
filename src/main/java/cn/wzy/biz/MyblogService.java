package cn.wzy.biz;

import cn.wzy.dao.Blog_InfoDao;
import cn.wzy.dao.Blog_tagDao;
import cn.wzy.dao.MyblogDao;
import cn.wzy.entity.Blog_tag;
import cn.wzy.entity.Myblog;
import cn.wzy.model.ResultModel;
import cn.wzy.vo.MyblogUI;
import org.cn.wzy.query.BaseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author wzy
 * @Date 2018/4/10 12:43
 */
@Service
public class MyblogService {

    @Autowired
    private MyblogDao dao;

    @Autowired
    private Blog_tagDao tagDao;

    @Autowired
    private Blog_InfoDao infoDao;

    /**
     * 通过条件查询相关blogs
     * @param query
     * @return
     */
    public ResultModel<MyblogUI> queryBlogByCondition(BaseQuery<Myblog> query) {
        ResultModel<MyblogUI> result = new ResultModel<>();
        List<Myblog> oldList = dao.selectByCondition(query);
        if (oldList == null) {
            return result.setData(null).setTotal(0).setCode(ResultModel.FAILED + "");
        }
        List<MyblogUI> newList = new ArrayList<>(oldList.size());
        for (int i = 0; i < oldList.size(); ++i) {
            String content = null;
            try {
                content = new String(oldList.get(i).getContent(),"utf-8");
            } catch (UnsupportedEncodingException e) {
                return result.setData(null)
                        .setTotal(0)
                        .setCode(ResultModel.FAILED + "content转换失败");
            }
            //提取第一个段落
            int pIndex = content.indexOf("</p>");
            if (pIndex == -1)
                pIndex = content.length();
            newList.add(new MyblogUI());
            BeanUtils.copyProperties(oldList.get(i),newList.get(i));
            newList.get(i).setContent(null);
            newList.get(i).setBlog_content(content.substring(0,pIndex + 4));
        }
        return result.setData(newList).setTotal(oldList.size()).setCode(ResultModel.SUCCESS);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    public ResultModel<MyblogUI> queryById(Integer id) {
        //更新blog的访问量
        dao.addLookNum(id);
        ResultModel<MyblogUI> result = new ResultModel<>();
        Myblog old = dao.selectByPrimaryKey(id);
        MyblogUI new_blog = new MyblogUI();
        BeanUtils.copyProperties(old,new_blog);
        try {
            new_blog.setBlog_content(new String(old.getContent(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            return result.setData(null).setTotal(0).setCode(ResultModel.FAILED + "转换失败.");
        }
        new_blog.setContent(null);
        return result.setData(Arrays.asList(new_blog)).setTotal(1).setCode(ResultModel.SUCCESS);
    }

    /**
     * 通过条件查询相关条数
     * @param query
     * @return
     */
    public ResultModel<Integer> queryCountByCondition(BaseQuery<Myblog> query) {
        ResultModel<Integer> result = new ResultModel<>();
        int count = dao.selectCountByCondition(query);
        if (count == -1) {
            return result.setData(null).setTotal(0).setCode(ResultModel.FAILED + "查询出错");
        }
        return result.setData(Arrays.asList(count)).setTotal(1).setCode(ResultModel.SUCCESS);
    }


    /**
     * 写blog如果tag是新的 那么就加入进去 1 成功 2 密码错误 3 其他异常
     * @param record
     * @param article
     * @return
     */
    public int addBlog(Myblog record, String article, String password) {
        String pass = infoDao.getAdminPassword();
        if (!pass.equals(password)) {
            return 2;
        }
        if (article == null || "".equals(article.trim())) {
            return 3;
        }
        article = article.replace("<pre","<div class=\"codeDiv\"><pre");
        article = article.replace("</pre>","</pre></div>");
        try {
            record.setWritetime(new Date().getTime())
                    .setLookNum(0)
                    .setCommentNum(0)
                    .setContent(article.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Blog_tag tag = new Blog_tag()
                .setTagName(record.getTag());
        if (tagDao.selectCountByCondition(new BaseQuery<Blog_tag>().setQuery(tag)) == 0)
            tagDao.insert(tag);
        return dao.insertSelective(record);
    }

}
