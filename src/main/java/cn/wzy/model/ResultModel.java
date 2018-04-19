package cn.wzy.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wzy
 * @Date 2018/4/10 12:45
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ResultModel<Q> {

    public static final String SUCCESS = "success";
    public static final String FAILED= "failed";
    private List<Q> data;

    private String code;

    private int total;

    private int online_num;
}
