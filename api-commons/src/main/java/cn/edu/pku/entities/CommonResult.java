package cn.edu.pku.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonResult<T> {
    private boolean status = true;  // 结果状态

    private T data;          // 结果数据

    private String msg;      // 结果信息
}