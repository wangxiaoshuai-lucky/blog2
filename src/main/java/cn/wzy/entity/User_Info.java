package cn.wzy.entity;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class User_Info {
    private Integer id;

    private String username;

    private String password;

    private String content;

    private Integer access;

}