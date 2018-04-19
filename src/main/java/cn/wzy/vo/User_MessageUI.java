package cn.wzy.vo;

import cn.wzy.entity.User_Message;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author wzy
 * @Date 2018/4/12 19:52
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class User_MessageUI extends User_Message {
    private String username;
    private String userContent;
}
