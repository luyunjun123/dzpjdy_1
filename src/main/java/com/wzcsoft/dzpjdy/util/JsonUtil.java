package com.wzcsoft.dzpjdy.util;


import com.alibaba.fastjson.JSON;
import com.wzcsoft.dzpjdy.domain.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lyj
 * @date 2019/7/26 1:12
 */
public class JsonUtil {

    public static String  getAccessToken(String str){
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(str);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }


    public static Map<String, Object> getBody(String str) {
        //todo
        String s1=null;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            s1 = new String(bytes, "UTF-8");
            System.out.println("解码后的UTF-8"+s1);
        }catch (Exception e){
            System.out.println("解析编码格式失败"+e.getMessage());
        }
        Map<String, Object> rempmap = new HashMap<>();
        System.out.println("获取病人基本信息"+str);
        try {
            org.json.JSONObject jsonResult = new org.json.JSONObject(s1);
            Object responseTime1 = jsonResult.get("responseTime");
            String s = String.valueOf(responseTime1);
            Object status_1 = jsonResult.get("status");
            String status = String.valueOf(status_1);
            //todo
            Object msgCode_1 = jsonResult.get("msgCode");
            String msgCode = String.valueOf(msgCode_1);
            Object msg_1 = jsonResult.get("msg");
            String msg = String.valueOf(msg_1);
            Object inParamSize_1 = jsonResult.get("inParamSize");
            String inParamSize = String.valueOf(inParamSize_1);
            Object transId_1 = jsonResult.get("transId");
            String transId = String.valueOf(transId_1);

            rempmap.put("status", status);
            rempmap.put("msg", msg);
            rempmap.put("inParamSize", inParamSize);
            rempmap.put("transId", transId);
            rempmap.put("responseTime", s);
            org.json.JSONObject result1 = jsonResult.getJSONObject("result");
            String result = result1.toString();
            com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
            String pageNo = jsonObject.getString("pageNo");
            String pageSize = jsonObject.getString("pageSize");
            String total = jsonObject.getString("total");
            String totalPage = jsonObject.getString("totalPage");
            rempmap.put("pageNo", pageNo);
            rempmap.put("pageSize", pageSize);
            rempmap.put("total", total);
            rempmap.put("totalPage", totalPage);
            String content = jsonObject.getString("content");
            rempmap.put("content", content);
            rempmap.put("msgCode", msgCode);
//             List<User> list =  com.alibaba.fastjson.JSONObject.parseObject(content, List.class);
//                for (int i = 0; i < list.size(); i++) {
//                    User user =  com.alibaba.fastjson.JSONObject.parseObject( com.alibaba.fastjson.JSONObject.toJSONString(list.get(i)), User.class);
//                    String caseNo = user.getCaseNo();
//                    String certId = user.getCertId();
//                    String patSex = user.getPatSex();
//                    System.out.println(patSex);
//                    if(patSex.equals("1")){
//                        patSex="男";
//                    }else if(patSex.equals("2")) {
//                        patSex="女";
//                    }else {
//                        patSex="未说明性别";
//                    }
//                    String patId = user.getPatId();
//                    String patName = user.getPatName();
//                    rempmap.put("caseNo", caseNo);
//                    rempmap.put("certId", certId);
//                    rempmap.put("patSex", patSex);
//                    rempmap.put("patId", patId);
//                    rempmap.put("patName", patName);
//                }
            return rempmap;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rempmap.put("msg", "解析异常");
        }

        return rempmap;
    }
}
