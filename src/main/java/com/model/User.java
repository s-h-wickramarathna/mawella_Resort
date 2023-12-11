package com.model;

public class User {

    public String No;
    public String UserId;
    public String UserName;
    public String UserMobile;
    public String UserType;
    public String UserStatus;
    public String UserCreatedAt;
    public String UserUpdatedAt;

    public User(String no, String userId, String userName, String userMobile, String userType, String userStatus, String userCreatedAt, String userUpdatedAt) {
        No = no;
        UserId = userId;
        UserName = userName;
        UserMobile = userMobile;
        UserType = userType;
        UserStatus = userStatus;
        UserCreatedAt = userCreatedAt;
        UserUpdatedAt = userUpdatedAt;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public String getUserCreatedAt() {
        return UserCreatedAt;
    }

    public void setUserCreatedAt(String userCreatedAt) {
        UserCreatedAt = userCreatedAt;
    }

    public String getUserUpdatedAt() {
        return UserUpdatedAt;
    }

    public void setUserUpdatedAt(String userUpdatedAt) {
        UserUpdatedAt = userUpdatedAt;
    }
}
