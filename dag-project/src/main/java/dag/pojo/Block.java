package dag.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Block implements Comparable<Block> {
    /***
     * 数据库ID
     */
    private int id;

    /**
     * 前面一个节点的哈希地址
     */
    private String pre1;

    /**
     * 前面另一个节点的哈希地址
     */
    private String pre2;

    /***
     * 存储在本节点中的数据直
     */
    private String data;

    /***
     * 本节点的哈希码
     */
    private String hash;

    /***
     * 指向本节点的节点数目
     */
    private int num;

    /***
     * 时间戳
     */
    private long timestamp;

    @Override
    public int compareTo(Block o) {
        if (this.num > o.num) return 1;
        else if (this.num == o.num) return 0;
        else return -1;
    }
}
