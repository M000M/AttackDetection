package dag.pojo;

public class CommonResult {
    /***
     * 返回结果状态，1为正常，0为错误，2为出现异常
     */
    private int status;

    private String data;

    private String msg;

    public CommonResult() {
    }

    public CommonResult(int status, String data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setSuccess() {
        this.setStatus(0);
        this.setMsg("操作成功");
    }

    public void setFailure() {
        this.setStatus(1);
        this.setData(null);
        this.setMsg("操作错误");
    }

    public void setException() {
        this.setStatus(2);
        this.setData(null);
        this.setMsg("操作异常");
    }
}
