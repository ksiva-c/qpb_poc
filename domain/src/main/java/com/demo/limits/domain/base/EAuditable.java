package com.demo.limits.domain.base;

import org.joda.time.DateTime;

/**
 * User: lnv
 * Date:
 * Time: 
 */
public interface EAuditable {
    public static interface Update{
        public EUpdateInfo getUpdateInfo();
        public void setUpdateInfo(EUpdateInfo updateInfo);
    }
    public static interface Create{
        public ECreateInfo getCreateInfo();
        public void setCreateInfo(ECreateInfo createInfo);
    }
    public static interface UpdateDate{
        public DateTime getLastUpdatedOn();
        public void setLastUpdatedOn(DateTime date);
    }
    public static interface UpdateUser{
        public String getLastUpdatedBy();
        public void setLastUpdatedBy(String user);
    }
    public static interface CreateDate{
        public DateTime getCreatedOn();
        public void setCreatedOn(DateTime date);
    }
    public static interface CreateUser{
        public String getCreatedBy();
        public void setCreatedBy(String user);
    }
}
