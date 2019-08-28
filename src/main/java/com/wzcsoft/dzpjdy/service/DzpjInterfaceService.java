package com.wzcsoft.dzpjdy.service;

/**
 * Created by 邢直 on 2019/1/9.
 */
public interface DzpjInterfaceService {
//    Object getBillList(String timeStr, String patientId, String cardNo, int pageNo, int pageSize);
//    Object getBillInfo(String billName,String billBatchCode,String billNo,String payer,String random,String ivcDateTime);
//    Object getValidBillBatchCode();
//    Object getPaperBillNo(String pBillBatchCode,String Token);
//    Object turnPaper(String billBatchCode,String billNo,String pBillBatchCode,String pBillNo);
//    Object getValidBillNo();
//
//    Object getEwmTimeQuery(String decode, String chargetime, int pageno, int pagesize,String Token);
//
//    Object getEwmBillList(String decode,String Token);
    /*三医院病人基本信息查询*/
    Object getPatientInfo(String cardno);
}
