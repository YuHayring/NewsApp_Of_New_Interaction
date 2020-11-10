package cn.edu.gdut.douyintoutiao.entity;

public class Result {
    private String msg;
    private String code;
    private Boolean isLogin;

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", isLogin='" + isLogin + '\'' +
                '}';
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

    public Result(String msg, String code, Boolean isLogin) {
        this.msg = msg;
        this.code = code;
        this.isLogin = isLogin;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }
}
