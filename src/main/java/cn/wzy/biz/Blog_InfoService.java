package cn.wzy.biz;

import cn.wzy.dao.Blog_InfoDao;
import cn.wzy.entity.Blog_Info;
import cn.wzy.model.ResultModel;
import cn.wzy.vo.Blog_InfoUI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:43
 */
@Service
public class Blog_InfoService {

    @Autowired
    private Blog_InfoDao blog_infoDao;

    /**
     * 获取blog的主要信息
     * @return
     */
    public ResultModel<Blog_InfoUI> getBlog_Info() {
        ResultModel<Blog_InfoUI> result = new ResultModel<>();
        List<Blog_InfoUI> list = new ArrayList<>();
        Blog_Info old_info = blog_infoDao.selectByPrimaryKey(1);
        Blog_InfoUI new_info = new Blog_InfoUI();
        BeanUtils.copyProperties(old_info, new_info);
        try {
            new_info.setTop_info(new String(old_info.getInfo(),"utf-8"));
            new_info.setInfo(null);
        } catch (UnsupportedEncodingException e) {
            result.setCode(result.FAILED + "topinfo转换出错")
                    .setData(null)
                    .setTotal(0);
            return result;
        }
        list.add(new_info);
        result.setCode(result.SUCCESS)
                .setData(list)
                .setTotal(1);
        return result;
    }

    public Integer updateInfo(String info, String password) {
        if (!blog_infoDao.getAdminPassword().equals(password))
            return 0;
        Blog_Info record = null;
        try {
            record = new Blog_Info().setId(1).setInfo(info.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return blog_infoDao.updateByPrimaryKeySelective(record);
    }
}
