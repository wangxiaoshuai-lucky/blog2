package cn.wzy.vo;

import cn.wzy.entity.Blog_Info;
import lombok.Setter;
import lombok.*;
import lombok.experimental.Accessors;


/**
 * @author wzy
 * @Date 2018/4/10 12:39
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Blog_InfoUI extends Blog_Info{
    private String top_info;
}
