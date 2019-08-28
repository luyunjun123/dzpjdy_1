package com.wzcsoft.dzpjdy.domain;


import java.io.Serializable;
public class TokenResultVo implements Serializable {
    
    /***/
    private String access_token;
    
    /***/
    private String token_type;

    /***/
    private String expires_in;

    /***/
    private String scope;

    /***/
    private String license;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }

    public String getLicense() {
        return license;
    }

    @Override
    public String toString() {
        return "TokenResultVo{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", scope='" + scope + '\'' +
                ", license='" + license + '\'' +
                '}';
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
