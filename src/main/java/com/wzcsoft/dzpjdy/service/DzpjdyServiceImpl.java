package com.wzcsoft.dzpjdy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzcsoft.dzpjdy.dao.CurentbillnoMapper;
import com.wzcsoft.dzpjdy.domain.Curentbillno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xingzhi on 2019/5/18.
 */
@Service
public class DzpjdyServiceImpl implements DzpjdyService{
    @Autowired
    private CurentbillnoMapper cbm;

    @Autowired
    private DzpjInterfaceService dzis;

    @Value("${pageinit.title}")
    private String _pagetitle;

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public Object pageInit(){
        String billBgnNo;
        String billEndNo;
        Map<String,Object> retMap = new HashMap<>();
        Curentbillno curbill = cbm.selectByPrimaryKey(1);
        String curbillno = curbill.getCurpbillno();
        String jsonStr = JSONObject.toJSONString(dzis.getValidBillNo());
        JSONObject json = JSONObject.parseObject(jsonStr);
        String status = json.getString("status");
        String message = json.getString("message");
        if(status.equals("S_OK")){
            JSONObject data = json.getJSONObject("data");
            int count = data.getInteger("count");
            JSONArray billNoList = data.getJSONArray("billNoList");

            if (billNoList.size()>0){
                billBgnNo = billNoList.getJSONObject(0).getString("billBgnNo");
                billEndNo = billNoList.getJSONObject(0).getString("billEndNo");
            }


            retMap.put("pagetile",_pagetitle);
            retMap.put("status","S_OK");
            retMap.put("message",message);
        }else{
            retMap.put("status","S_FALSE");
            retMap.put("message",message);
        }



        return retMap;
    }

    @Override
    public Object savePbillNo(String pbillno){
        Map<String,Object> retMap = new HashMap<>();
        Curentbillno rec = cbm.selectByPrimaryKey(1);
        rec.setCurpbillno(pbillno);
        cbm.updateByPrimaryKey(rec);

        retMap.put("status","S_OK");
        retMap.put("message","");
        return true;
    }
}
