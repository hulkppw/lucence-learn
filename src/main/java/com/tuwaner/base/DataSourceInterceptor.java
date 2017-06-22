package com.tuwaner.base;


import org.aspectj.lang.JoinPoint;

public class DataSourceInterceptor {
    public void setdataSourceMysql(JoinPoint jp) {
        CustomerContextHolder.setCustomerType("dataSource1");
    }

    public void setdataSourceOracle(JoinPoint jp) {
        CustomerContextHolder.setCustomerType("dataSource2");
    }
}
