package com.wzcsoft.dzpjdy.controller;

import com.wzcsoft.dzpjdy.service.HisInterfaceService;
import com.wzcsoft.dzpjdy.service.HisInterfaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 邢直 on 2019/1/9.
 */
@RestController
@RequestMapping(value = "hisinterf")
public class hisInterfController {
    @Autowired
    HisInterfaceService hiservice;
    //根据卡号像his查询数据
    @RequestMapping(value = "getpainfobycard")
    public Object Getpainfobycard(@RequestParam(name = "cardno",required = true) String cardno){
        return hiservice.getPatientInfo(cardno);
    }
    //his返回打印成功票据 纸质电子票据号 billno:pbillNo, 票据扫码号码 pbillno:paperBillno setpainfobycard
    @RequestMapping(value = "setpainfobycard")
    public Object setpainfobycard( @RequestParam(name = "patientid",required = true) String patientid
            ,@RequestParam(name = "billno",required = true) String billno
    ){
        System.out.println("进入his回传数据方法");
        return hiservice.setpainfobycard(patientid,billno);
    }
    //肿瘤医院回传信息
    @RequestMapping(value = "setticketinfo")
    public Object Setticketinfo(@RequestParam(name = "ebillno",required = true) String ebillno,
                                @RequestParam(name = "pbillno",required = true) String pbillno,
                                @RequestParam(name = "pbillbatchcode",required = true) String pbillbatchcode,
                                @RequestParam(name = "pflag",required = true) String pflag){
        return hiservice.setTicketinfo(ebillno,pbillno,pbillbatchcode,pflag);
    }
}
