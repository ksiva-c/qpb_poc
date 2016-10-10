package com.demo.limits.domain.base;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * User: lnv
 * Date: 
 * Time: 
 */

@Embeddable
public class EAppInfo {
    @Column(name = "IS_360O", nullable = false)
    private Boolean is360O;

    @Column(name = "IS_360D", nullable = false)
    private Boolean is360D;

    public Boolean getIs360O() {
        return is360O;
    }

    public EAppInfo setIs360O(Boolean is360O) {
        this.is360O = is360O;
        return this;
    }

    public Boolean getIs360D() {
        return is360D;
    }

    public EAppInfo setIs360D(Boolean is360D) {
        this.is360D = is360D;
        return this;
    }

    public static EAppInfo d360(){
        EAppInfo appInfo = new EAppInfo();
        appInfo.is360D = true;
        appInfo.is360O = false;
        return appInfo;
    }
}
