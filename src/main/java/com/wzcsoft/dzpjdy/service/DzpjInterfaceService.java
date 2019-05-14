package com.wzcsoft.dzpjdy.service;

/**
 * Created by 邢直 on 2019/1/9.
 */
public interface DzpjInterfaceService {
    Object getBillList(String chargeTime,String patientId,String cardNo,int pageNo,int pageSize);
    Object getBillInfo(String billName,String billBatchCode,String billNo,String payer,String random,String ivcDateTime);
    Object getValidBillBatchCode();
    Object getPaperBillNo(String pBillBatchCode);
    Object turnPaper(String billBatchCode,String billNo,String pBillBatchCode,String pBillNo);
}
