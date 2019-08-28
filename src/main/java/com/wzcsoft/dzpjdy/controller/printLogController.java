package com.wzcsoft.dzpjdy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzcsoft.dzpjdy.service.PrintlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    @RequestMapping(value = "etk_turnPaper",method = RequestMethod.POST)
    public  Object etk_turnPaper(   @RequestBody Map<String, Object> json, HttpServletRequest reques){
        String str="{\"result\":{\n" +
                "\t\"result\": \"S0000\",\n" +
                "\t\"message\":\"成功\"\n" +
                "}}";
        return str;
    }
    //测试接口
    @RequestMapping(value = "etk_getValidBillNo",method = RequestMethod.POST)
    public  Object etk_getValidBillNo(   @RequestBody Map<String, Object> json, HttpServletRequest reques){
        String str="{\n" +
                "    \"result\": {\n" +
                "        \"result\": \"S0000\",\n" +
                "        \"message\": \"{\\\"count\\\":\\\"9\\\",\\\"billNoList\\\":[{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181465\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181405\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181416\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181410\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181421\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181421\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181424\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181424\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181432\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181432\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181464\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181454\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181477\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181477\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000181481\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181481\\\"},{\\\"billBatchCode\\\":\\\"51060119\\\",\\\"billEndNo\\\":\\\"0000182404\\\",\\\"billCode\\\":\\\"4002\\\",\\\"billBgnNo\\\":\\\"0000181484\\\"}]}\"\n" +
                "    },\n" +
                "    \"status\": \"T\",\n" +
                "    \"msgCode\": null,\n" +
                "    \"msg\": null,\n" +
                "    \"responseTime\": 69,\n" +
                "    \"inParamSize\": null,\n" +
                "    \"transId\": \"2d65e694deb34fd4b751056ad49bf668\"\n" +
                "}\n";
        return str;
    }
    //测试接口  etk_getBillDetail
    @RequestMapping(value = "etk_getBillDetail",method = RequestMethod.POST)
    public  Object etk_getBillDetail(   @RequestBody Map<String, Object> json, HttpServletRequest reques){
        String str="{\"result\":{\n" +
                "\t\"result\": \"S0000\",\n" +
                "\t\"message\": {\n" +
                "\t\t\"busNo\": \"业务流水号\",\n" +
                "\t\t\"payer\": \"患者姓名\",\n" +
                "\t\t\"busDateTime\": \"患者姓名\",\n" +
                "\t\t\"placeCode\": \"开票点编码\",\n" +
                "\t\t\"payee\": \"收费员\",\n" +
                "\t\t\"author\": \"票据编制人\",\n" +
                "\t\t\"totalAmt\": 0,\n" +
                "\t\t\"busType\": \"01\",\n" +
                "\t\t\"remark\": \"备注\",\n" +
                "\t\t\"cardType\": \"卡类型\",\n" +
                "\t\t\"cardNo\": \"卡号\",\n" +
                "\t\t\"otherParameter\": [{\n" +
                "\t\t\t\t\"infoName\": \"历年账户\",\n" +
                "\t\t\t\t\"infoValue\": \"18.00\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"infoName\": \"个人支付\",\n" +
                "\t\t\t\t\"infoValue\": \"28.00\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\"otherSpecialParameter\": [{\n" +
                "\t\t\t\t\"infoName\": \"历年账户\",\n" +
                "\t\t\t\t\"infoValue\": \"18.00\"\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"infoName\": \"个人支付\",\n" +
                "\t\t\t\t\"infoValue\": \"28.00\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\n" +
                "\t\t\"chargeDetail\": [{\n" +
                "\t\t\t\"chargeCode\": \"收费项目代码\",\n" +
                "\t\t\t\"chargeName\": \"收费项目名称\",\n" +
                "\t\t\t\"unit\": \"计量单位\",\n" +
                "\t\t\t\"number\": \"0\",\n" +
                "\t\t\t\"std\": \"0\",\n" +
                "\t\t\t\"amt\": \"0\",\n" +
                "\t\t\t\"selfAmt\": \"0\",\n" +
                "\t\t\t\"remark\": \"备注\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"chargeCode\": \"收费项目代码\",\n" +
                "\t\t\t\"chargeName\": \"收费项目名称\",\n" +
                "\t\t\t\"unit\": \"计量单位\",\n" +
                "\t\t\t\"number\": \"0\",\n" +
                "\t\t\t\"std\": \"0\",\n" +
                "\t\t\t\"amt\": \"0\",\n" +
                "\t\t\t\"selfAmt\": \"0\",\n" +
                "\t\t\t\"remark\": \"备注\"\n" +
                "\t\t}],\n" +
                "\t\t\"listDetail\": [{\n" +
                "\t\t\t\"chrgtype\": \"费用类型\",\n" +
                "\t\t\t\"code\": \"编码\",\n" +
                "\t\t\t\"name\": \"药品名称/剂型\",\n" +
                "\t\t\t\"unit\": \"计量单位 \",\n" +
                "\t\t\t\"std\": \"0\",\n" +
                "\t\t\t\"number\": \"0\",\n" +
                "\t\t\t\"amt\": \"0\",\n" +
                "\t\t\t\"selfAmt\": \"0\",\n" +
                "\t\t\t\"remark\": \"备注\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"chrgtype\": \"费用类型\",\n" +
                "\t\t\t\"code\": \"编码\",\n" +
                "\t\t\t\"name\": \"药品名称/剂型\",\n" +
                "\t\t\t\"unit\": \"计量单位 \",\n" +
                "\t\t\t\"std\": \"0\",\n" +
                "\t\t\t\"number\": \"0\",\n" +
                "\t\t\t\"amt\": \"0\",\n" +
                "\t\t\t\"selfAmt\": \"0\",\n" +
                "\t\t\t\"remark\": \"备注\"\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}}";
        return str;
    }

    //测试接口
    @RequestMapping(value = "etk_getEBillUnPrintList",method = RequestMethod.POST)
    public  Object etk_getEBillUnPrintList(   @RequestBody Map<String, Object> json, HttpServletRequest reques){
            String str="{\"result\":\n" +
                    "{\n" +
                    "\t\"result\": \"S0000\",\n" +
                    "\t\"message\":{\n" +
                    "\"total\": \"1\",\n" +
                    "\"pageNo\": \"10\",\n" +
                    "\"billList\": [{\n" +
                    "\"busDate\": \"20190812\",\n" +
                    "\"busNo\": \"3123123\",\n" +
                    "\"billName\": \"421414\",\n" +
                    "\"billBatchCode\": \"10001\",\n" +
                    "\"billNo\": \"00001\",\n" +
                    "\"random\": \"34hr\",\n" +
                    "\"totalAmt\": \"1000\",\n" +
                    "\"ivcDateTime\": \"20191021\",\n" +
                    "\"payer\": \"张麻子\",\n" +
                    "\"remark\": \"备注\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"busDate\": \"20190812\",\n" +
                    "\"busNo\": \"3123231123\",\n" +
                    "\"billName\": \"421412\",\n" +
                    "\"billBatchCode\": \"001010\",\n" +
                    "\"billNo\": \"12312132\",\n" +
                    "\"random\": \"fe21\",\n" +
                    "\"totalAmt\": \"20000\",\n" +
                    "\"ivcDateTime\": \"20101010\",\n" +
                    "\"payer\": \"张麻子\",\n" +
                    "\"remark\": \"备注\"\n" +
                    "}\n" +
                    "]\n" +
                    "}\n" +
                    "}\n" +
                    "\n" +
                    "}";
                 return str;
    }

    //测试接口
    @RequestMapping(value = "etk_getPaperBillNo",method = RequestMethod.POST)
    public  Object etk_getPaperBillNo(   @RequestBody Map<String, Object> json, HttpServletRequest reques){
        String str="{\"result\":{\n" +
                "\t\"result\": \"S0000\",\n" +
                "\t\"message\":{\n" +
                "\"pBillBatchCode\": \"00001\",\n" +
                "\"pBillNo\": \"00002\"\n" +
                "}\n" +
                "}}";
        return str;
    }
    //测试接口
    @RequestMapping(value = "Test_1",method = RequestMethod.POST)
    public  Object Writeprintlog(   @RequestBody Map<String, Object> json, HttpServletRequest reques){
        String requestURI = reques.getRequestURI();
        System.out.println("requestURI"+requestURI);
        if (requestURI.equals("/printlog/Test_1")){
        String str="{\"result\":{\n" +
                "\"content\":[{\"patName\":\"张麻子\",\"patId\":\"898989\",\"caseNo\":\"332211\",\"patSex\":\"1\",\"patAge\":\"80\",\"patBirthDate\":\"1960-10-11\",\"nationality\":\"CN\",\"nation\":\"H\",\"maritalStatus\":\"已婚\",\"certId\":\"51110203132890001121\",\"remark\":\"备注信息\"}],\n" +
                "\"pageNo\":1,\n" +
                "\"pageSize\":10,\n" +
                "\"total\":5,\n" +
                "\"totalPage\":1\n" +
                "},\"status\":\"T\"\n" +
                ",\"msgCode\":null,\n" +
                "\"msg\":null,\n" +
                "\"responseTime\":500,\n" +
                "\"transId\":\"3331\",\n" +
                "\"inParamSize\":null\n" +
                "}";
            return str;
        }
        return null;
//        Enumeration<String> authorization = reques.getHeaders("authorization");
//        String s = authorization.nextElement();
//        System.out.println("验证消息:"+s);
//        Map<String, String[]> parameterMap = reques.getParameterMap();
//        Set<String> keys = parameterMap.keySet();
//        for(String key : keys) {
//            String[] b = parameterMap.get(key);
//            System.out.println(b);
//        }

    }

    //测试接口
    @RequestMapping(value = "Test",method = RequestMethod.POST)
    public  Object Writeprintlog(
         /*   @RequestBody String basic,@RequestBody String detail,@RequestBody String enc,*/
//            @RequestBody Map<String, Object> json,
       /*  HttpServletRequest reques*/){
//        String str="{\n" +
//                "\"result\": {\n" +
//                "\"content\": [\n" +
//                "{\"patName\":\"1\",\"caseNo\":\"000213\",\"patSex\":\"1\",\"patAge\":\"000213\",\"certId\":\"5110219921323112\"}\n" +
//                "],\n" +
//                "\"pageNo\": 1,\n" +
//                "\"pageSize\": 10,\n" +
//                "\"total\": 5,\n" +
//                "\"totalPage\": 1\n" +
//                "},\n" +
//                "\"status\": T, \"msgCode\": \"\",\n" +
//                "\"msg\": \"1231\",\n" +
//                "\"inParamSize\": \"1231\",\n" +
//                "\"responseTime\": 500,\n" +
//                "\"transId\": \"123\" \n" +
//                "}";


//        String  str="{\"total\":5,\"pageNo\":1,\"totalPage\":1,\"pageSize\":10,\"content\":[{\"patId\":\"4399\",\"patAge\":\"18\",\"patSex\":\"1\",\"patName\":\"1\",\"caseNo\":\"123123\"}]}";
//
//
//        return str;
//        HashMap<String , Object> datamap = new HashMap<>();
//        datamap.put("caseNo", "1");
//        datamap.put("patBirthDate", "2");
//        datamap.put("patSex", "2");
//        datamap.put("patId", "3");
//        datamap.put("patName", "5");

//        System.out.println(json);
//        JSONObject jsonObject1 = JSON.parseObject(cardno);
//        String string1 = jsonObject1.getString("cardno");
//        System.out.println("接收到的消息"+string1);

//          Enumeration<String> authorization = reques.getHeaders("authorization");
//          String s = authorization.nextElement();
//          System.out.println("验证消息:"+s);
//            Map<String, String[]> parameterMap = reques.getParameterMap();
//            Set<String> keys = parameterMap.keySet();
//             for(String key : keys){
//            String[] b = parameterMap.get(key);
//            System.out.println(b);
//         }


//        if(string1.equals("123")){
            Map<Object, Object> retMap = new HashMap<>();

            String str="{\n" +
                    "    \"access_token\":\"aede1419-fa16-4b2f-967c-02ee62726951\",\n" +
                    "    \"token_type\":\"bearer\",\n" +
                    "    \"expires_in\":29453,\n" +
                    "    \"scope\":\"server\",\n" +
                    "    \"license\":\"made by hsop\"\n" +
                    "}";
//            retMap.put("access_token", "aede1419-fa16-4b2f-967c-02ee62726951");
//            retMap.put("token_type", "bearer");
//            retMap.put("scope", "bearer");
//            retMap.put("expires_in", "bearer");
//            retMap.put("license", "made by hsop");

            return str;
//        }else {
//            Map<Object, Object> dataMap = new HashMap<>();
//            dataMap.put("status", "S_NO");
//            dataMap.put("message", "失败");
//            return dataMap;
//        }
    }

}
