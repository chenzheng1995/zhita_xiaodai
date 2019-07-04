package com.zhita.util;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  
/* 
 * 配置多数据源 
 */  

public class DynamicDataSource extends AbstractRoutingDataSource{  
  
    public static final String  xiaodaiDataSource= "xiaodaiDataSource";  
      
    public static final String yunyingshangDataSource = "yunyingshangDataSource";  
    //本地线程，获取当前正在执行的currentThread  
    public static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();   
      
    public static void setCustomerType(String customerType) {  
  
        contextHolder.set(customerType);  
      
    }  
  
    public static String getCustomerType() {  
        return contextHolder.get();       
    }  
  
    public static void clearCustomerType() {  
        contextHolder.remove();  
    }  
  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return getCustomerType();    
    }  
}
