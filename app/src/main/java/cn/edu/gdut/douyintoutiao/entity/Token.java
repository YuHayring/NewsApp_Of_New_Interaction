package cn.edu.gdut.douyintoutiao.entity;

/**
 * @author hayring
 * @date 11/16/20 1:55 PM
 */
public class Token {


    public Token(String token, String reFlashToken) {
        this.token = token;
        this.reFlashToken = reFlashToken;
    }

    /**
     * 普通 token
     */
    String token;

    /**
     * 刷新普通 token 的 token，只能用一次
     */
    String reFlashToken;


    public String getToken() {
        return token;
    }

    public String getReFlashToken() {
        return reFlashToken;
    }
}
