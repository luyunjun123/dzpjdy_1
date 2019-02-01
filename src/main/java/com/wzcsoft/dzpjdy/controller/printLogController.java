package com.wzcsoft.dzpjdy.controller;

import com.wzcsoft.dzpjdy.service.PrintlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Xingzhi on 2019/1/28.
 */
@RestController
@RequestMapping(value = "printlog")
public class printLogController {
    @Autowired
    PrintlogServiceImpl pservice;

    @RequestMapping(value = "writeprintlog")
    public  Object Writeprintlog(@RequestParam(name = "cardno",required = true) String cardno,
                                 @RequestParam(name = "patientname",required = true) String patientname,
                                 @RequestParam(name = "socialno",required = true) String socialno,
                                 @RequestParam(name = "billno",required = true) String billno,
                                 @RequestParam(name = "billamount",required = true) double billamount,
                                 @RequestParam(name = "remark",required = false) String remark){
        return pservice.insertLog(cardno,patientname,socialno,billno,billamount,remark);
    }
}
