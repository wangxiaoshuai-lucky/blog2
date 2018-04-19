package cn.wzy.entity;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Leaving_message {
    private Integer id;

    private String content;

    private String ip;

    private Long addtime;
}