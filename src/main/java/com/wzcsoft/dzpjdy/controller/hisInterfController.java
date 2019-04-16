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

    @RequestMapping(value = "getpainfobycard")
    public Object Getpainfobycard(@RequestParam(name = "cardno",required = true) String cardno){
        return hiservice.getPatientInfo(cardno);
    }

    @RequestMapping(value = "setticketinfo")
    public Object Setticketinfo(@RequestParam(name = "ebillno",required = true) String ebillno,
                                @RequestParam(name = "pbillno",required = true) String pbillno,
                                @RequestParam(name = "pbillbatchcode",required = true) String pbillbatchcode){
        return hiservice.setTicketinfo(ebillno,pbillno,pbillbatchcode);
    }
}
