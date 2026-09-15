package com.isuzuki.jdbc.datasources;

import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;

import java.util.Map;

/**
 * @author :
 * @date : 2023/9/15
 * @description :
 */
public class DynamicDataSourceProperties {
    public static final String PREFIX = "isuzuki.jdbc.datasource";

    private org.springframework.boot.jdbc.autoconfigure.DataSourceProperties master;

    private Map<String, DataSourceProperties> slave;

    public DataSourceProperties getMaster() {
        return master;
    }

    public void setMaster(DataSourceProperties master) {
        this.master = master;
    }

    public Map<String, DataSourceProperties> getSlave() {
        return slave;
    }

    public void setSlave(Map<String, DataSourceProperties> slave) {
        this.slave = slave;
    }
}
