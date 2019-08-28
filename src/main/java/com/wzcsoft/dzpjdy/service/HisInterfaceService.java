package com.wzcsoft.dzpjdy.service;

/**
 * Created by 邢直 on 2019/1/9.
 */
public interface HisInterfaceService {
    Object getPatientInfo(String cardno);
    Object setTicketinfo(String ebillno,String pbillno,String pbillbatchcode,String pFlag);
    //省医院his回馈数据,String pbillNo   ,String chargetime
    Object setpainfobycard(String patientid,String billno);

}
