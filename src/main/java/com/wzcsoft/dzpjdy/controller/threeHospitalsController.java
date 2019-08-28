package com.wzcsoft.dzpjdy.controller;

import com.wzcsoft.dzpjdy.service.ThreeHospitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lyj
 * @date 2019/8/26 17:44
 */
@RestController
@RequestMapping(value = "threeHospitals")
public class threeHospitalsController {
    @Autowired
    private ThreeHospitalsService threeHospitalsService;
    /*获取票断*/
    @RequestMapping(value = "etk_getValidBillNo")
    public Object THpageinit(){
        return threeHospitalsService.THpageinit();
    }


    @RequestMapping(value = "etk_getEBillUnPrintList")
    public Object Getbilllist(@RequestParam(name = "chargetime",required = true) String chargetime,
                              @RequestParam(name = "patientid",required = true) String patientid,
                              @RequestParam(name = "cardno",required = true) String cardno,
                              @RequestParam(name = "pageno",required = true) int pageno,
                              @RequestParam(name = "pagesize",required = true) int pagesize){
        System.out.println(chargetime);
        return threeHospitalsService.getBillList(chargetime,patientid,cardno,pageno,pagesize);
    }
    @RequestMapping(value = "etk_getPaperBillNo")
    public Object Getbilllist(@RequestParam(name = "pBillBatchCode",required = true) String billbatchcode){
        return threeHospitalsService.etk_getPaperBillNo(billbatchcode);
    }
    @RequestMapping(value = "etk_getBillDetail")
    public  Object Getbillinfo(@RequestParam(name = "billname",required = true) String billname,
                               @RequestParam(name = "billbatchcode",required = true) String billbatchcode,
                               @RequestParam(name = "billno",required = true) String billno,
                               @RequestParam(name = "payer",required = true) String payer,
                               @RequestParam(name = "random",required = true) String random,
                               @RequestParam(name = "ivcdatetime",required = true) String ivcdatetime
    ) {
        return threeHospitalsService.getBillInfo(billname, billbatchcode, billno, payer, random, ivcdatetime);
    }

    @RequestMapping(value = "etk_turnPaper")
    public  Object etk_turnPaper(@RequestParam(name = "billbatchcode",required = true) String billbatchcode,
                             @RequestParam(name = "billno",required = true) String billno,
                             @RequestParam(name = "pbillbatchcode",required = true) String pbillbatchcode,
                             @RequestParam(name = "pbillno",required = true) String pbillno){
        return threeHospitalsService.turnPaper(billbatchcode,billno,pbillbatchcode,pbillno);
//        String v="{\n" +
//                "    \"data\": \"操作成功\",\n" +
//                "    \"message\": \"换开纸质票据成功\",\n" +
//                "    \"status\": \"S_OK\"\n" +
//                "}";
//        return JSON.parse(v);
    }
}
