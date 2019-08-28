package com.wzcsoft.dzpjdy.service;

public interface ThreeHospitalsService {

    Object getBillList(String chargetime, String patientid, String cardno, int pageno, int pagesize);

    Object etk_getPaperBillNo(String billbatchcode);

    Object getBillInfo(String billname, String billbatchcode, String billno, String payer, String random, String ivcdatetime);

    Object THpageinit();

    Object turnPaper(String billbatchcode, String billno, String pbillbatchcode, String pbillno);
}
