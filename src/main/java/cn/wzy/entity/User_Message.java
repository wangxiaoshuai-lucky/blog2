package cn.wzy.entity;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class User_Message {
    private Integer id;

    private Integer blogId;

    private String content;

    private Integer commenterId;

    private Long addtime;

    private String ip;
}