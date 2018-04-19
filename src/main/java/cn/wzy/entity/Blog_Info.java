package cn.wzy.entity;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Blog_Info {
    private Integer id;

    private Integer lookNum;

    private Long start;

    private byte[] info;

}