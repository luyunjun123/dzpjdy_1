package com.wzcsoft.dzpjdy.service;

/**
 * Created by 邢直 on 2019/1/9.
 */
public interface PrintlogService {

    Object insertLog(String cardno,String patientname,String socialno,String billno,double billamount,String remark);
}
