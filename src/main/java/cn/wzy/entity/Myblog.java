package cn.wzy.entity;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Myblog {
    private Integer id;

    private String title;

    private String tag;

    private Long writetime;

    private Integer lookNum;

    private Integer commentNum;

    private byte[] content;
}