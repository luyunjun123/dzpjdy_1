package com.wzcsoft.dzpjdy.service;

import com.wzcsoft.dzpjdy.dao.PrintlogMapper;
import com.wzcsoft.dzpjdy.domain.Printlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 邢直 on 2019/1/9.
 */
@Service
public class PrintlogServiceImpl implements PrintlogService {

    @Autowired
    PrintlogMapper plm;

    @Override
    public Object insertLog(String cardno,String patientname,String socialno,String billno,double billamount,String remark){
        Map<String,Object> ret = new HashMap<String,Object>();
        Printlog rec = new Printlog();
        rec.setCardno(cardno);
        rec.setPatientname(patientname);
        rec.setSocialno(socialno);
        rec.setBillno(billno);
        rec.setBillamount(billamount);
        rec.setPrintdate(new Date().toString());
        rec.setRemark(remark);
        try{
            plm.insert(rec);
            ret.put("status","S_OK");
            ret.put("message","写入日志成功！");
            return ret;
        }catch (Exception e){
            ret.put("status","S_FALSE");
            ret.put("message","写入日志失败！错误信息：" + e.getMessage());
            return ret;
        }
    }
}
