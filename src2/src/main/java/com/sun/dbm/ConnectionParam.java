package com.sun.dbm;

import java.io.Serializable;

/**
 * Created by sunyang on 2017/4/24.
 * 数据库连接池参数
 */
public class ConnectionParam implements Serializable {
    /**
     *
     */
    public static final long serialVersionUID = 1L;

    public String driver; // 数据库连接驱动
    public String url; // 数据库连接URL
    public String user; // 数据库连接user
    public String password; // 数据库连接password
    public int minConnection; // 数据库连接池最小连接数
    public int maxConnection; // 数据库连接池最大连接数
    public long timeoutValue; // 连接的最大空闲时间
    public long waitTime; // 取得连接的最大等待时间
    public int incrementalConnections=5; //连接池自动增加连接的数量

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMinConnection() {
        return minConnection;
    }

    public void setMinConnection(int minConnection) {
        this.minConnection = minConnection;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }

    public long getTimeoutValue() {
        return timeoutValue;
    }

    public void setTimeoutValue(long timeoutValue) {
        this.timeoutValue = timeoutValue;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public void setIncrementalConnections(int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }

    public int getIncrementalConnections() {
        return incrementalConnections;
    }

}
