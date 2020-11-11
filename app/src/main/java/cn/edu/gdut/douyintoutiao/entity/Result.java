package cn.edu.gdut.douyintoutiao.entity;

public class Result<T> {
    private String msg;
    private String code;
    private Boolean isLogin;
    private T[] data;

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", isLogin=" + isLogin +
                ", data=" + data +
                '}';
    }

    public Result() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Result(String msg, String code, Boolean isLogin, T[] data) {
        this.msg = msg;
        this.code = code;
        this.isLogin = isLogin;
        this.data = data;
    }

    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }
}
