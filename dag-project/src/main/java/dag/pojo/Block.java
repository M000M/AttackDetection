package dag.pojo;

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

    public Block() {
    }

    public Block(int id, String pre1, String pre2, String data, String hash, int num, long timestamp) {
        this.id = id;
        this.pre1 = pre1;
        this.pre2 = pre2;
        this.data = data;
        this.hash = hash;
        this.num = num;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPre1() {
        return pre1;
    }

    public void setPre1(String pre1) {
        this.pre1 = pre1;
    }

    public String getPre2() {
        return pre2;
    }

    public void setPre2(String pre2) {
        this.pre2 = pre2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", pre1='" + pre1 + '\'' +
                ", pre2='" + pre2 + '\'' +
                ", data='" + data + '\'' +
                ", hash='" + hash + '\'' +
                ", num=" + num +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(Block o) {
        if (this.num > o.num) return 1;
        else if (this.num == o.num) return 0;
        else return -1;
    }
}
