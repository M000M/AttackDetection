package cn.edu.pku.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegularExpression {

    private int id;

    private String expression;

    private int type;

    private String desc;

    private String field;

    private int valid;
}
