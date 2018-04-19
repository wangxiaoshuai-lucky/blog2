package cn.wzy.vo;

import cn.wzy.entity.Myblog;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author wzy
 * @Date 2018/4/10 12:41
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MyblogUI extends Myblog{
    private String blog_content;

}
