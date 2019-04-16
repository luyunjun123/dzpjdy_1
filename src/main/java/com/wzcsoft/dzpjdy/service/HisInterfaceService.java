package com.wzcsoft.dzpjdy.service;

/**
 * Created by 邢直 on 2019/1/9.
 */
public interface HisInterfaceService {
    Object getPatientInfo(String cardno);
    Object setTicketinfo(String ebillno,String pbillno,String pbillbatchcode);
}
