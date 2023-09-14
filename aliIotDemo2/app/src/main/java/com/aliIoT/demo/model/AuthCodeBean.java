package com.aliIoT.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class AuthCodeBean implements Parcelable {
    public static final Creator<AuthCodeBean> CREATOR = new Creator<AuthCodeBean>() {
        @Override
        public AuthCodeBean createFromParcel(Parcel source) {
            return new AuthCodeBean(source);
        }

        @Override
        public AuthCodeBean[] newArray(int size) {
            return new AuthCodeBean[size];
        }
    };
    /**
     * resultCode : 0
     * msg : success
     * state : ZmCe0BA165XH1RFc0
     * userId : lDG8iv8yBp0IJXrVUPOBlQKGLWmntgpG
     * token : 8c811OkNuhH1hMp7IKOLo1B5V9lJQ14s
     * erverSite:CN
     */

    private String userId;
    private String token;
    private String authCode;
    private String serverSite;
    private String identityId;
    private String account;

    public AuthCodeBean() {
    }

    protected AuthCodeBean(Parcel in) {
        this.userId = in.readString();
        this.token = in.readString();
        this.authCode = in.readString();
        this.serverSite = in.readString();
        this.identityId = in.readString();
        this.account = in.readString();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getServerSite() {
        return serverSite;
    }

    public void setServerSite(String serverSite) {
        this.serverSite = serverSite;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.token);
        dest.writeString(this.authCode);
        dest.writeString(this.serverSite);
        dest.writeString(this.identityId);
        dest.writeString(this.account);
    }
}
