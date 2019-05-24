package com.wzcsoft.dzpjdy.controller;

import com.wzcsoft.dzpjdy.service.DzpjdyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Xingzhi on 2019/5/18.
 */
@RestController
@RequestMapping(value = "dzpjdy")
public class dzpjdyController {
    @Autowired
    private DzpjdyService ds;

    @RequestMapping(value = "pageinit")
    public Object Pageinit(){
        return ds.pageInit();
    }

}
