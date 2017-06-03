package com.example.sanstoi.bean;

/**
 * Created by Sans toi on 2016/12/15.
 */
import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    private int userId;
    private String nickname;
    private String userpwd;
    private Integer version;

    public User() {
        super();
    }

    public User(int userId, String nickname, String userpwd) {
        super();
        this.userId = userId;
        this.nickname = nickname;
        this.userpwd = userpwd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}