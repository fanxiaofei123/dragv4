package org.com.drag.model;

public class DatabaseConnect {
    private Integer dbCconId;

    private Short dbConTypeId;

    private Integer dbConUserId;

    private String dbConName;

    private String dbConDbname;

    private String dbConIp;

    private Integer dbConPort;

    private String dbConUser;

    private String dbConPassword;

    private Boolean dbConEnable;


    public Integer getDbCconId() {
        return dbCconId;
    }

    public void setDbCconId(Integer dbCconId) {
        this.dbCconId = dbCconId;
    }

    public Short getDbConTypeId() {
        return dbConTypeId;
    }

    public void setDbConTypeId(Short dbConTypeId) {
        this.dbConTypeId = dbConTypeId;
    }

    public Integer getDbConUserId() {
        return dbConUserId;
    }

    public void setDbConUserId(Integer dbConUserId) {
        this.dbConUserId = dbConUserId;
    }

    public String getDbConName() {
        return dbConName;
    }

    public void setDbConName(String dbConName) {
        this.dbConName = dbConName;
    }

    public String getDbConDbname() {
        return dbConDbname;
    }

    public void setDbConDbname(String dbConDbname) {
        this.dbConDbname = dbConDbname;
    }

    public String getDbConIp() {
        return dbConIp;
    }

    public void setDbConIp(String dbConIp) {
        this.dbConIp = dbConIp;
    }

    public Integer getDbConPort() {
        return dbConPort;
    }

    public void setDbConPort(Integer dbConPort) {
        this.dbConPort = dbConPort;
    }

    public String getDbConUser() {
        return dbConUser;
    }

    public void setDbConUser(String dbConUser) {
        this.dbConUser = dbConUser;
    }

    public String getDbConPassword() {
        return dbConPassword;
    }

    public void setDbConPassword(String dbConPassword) {
        this.dbConPassword = dbConPassword;
    }

    public Boolean getDbConEnable() {
        return dbConEnable;
    }

    public void setDbConEnable(Boolean dbConEnable) {
        this.dbConEnable = dbConEnable;
    }

    @Override
    public String toString() {
        return "DatabaseConnect{" +
                "dbCconId=" + dbCconId +
                ", dbConTypeId=" + dbConTypeId +
                ", dbConUserId=" + dbConUserId +
                ", dbConName='" + dbConName + '\'' +
                ", dbConDbname='" + dbConDbname + '\'' +
                ", dbConIp='" + dbConIp + '\'' +
                ", dbConPort=" + dbConPort +
                ", dbConUser='" + dbConUser + '\'' +
                ", dbConPassword='" + dbConPassword + '\'' +
                ", dbConEnable=" + dbConEnable +
                '}';
    }
}