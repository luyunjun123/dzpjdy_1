package com.wzcsoft.dzpjdy.controller;

import com.alibaba.fastjson.JSON;
import com.wzcsoft.dzpjdy.service.DzpjInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by 邢直 on 2019/1/9.
 */
@RestController
@RequestMapping(value = "dzpjinterf")
public class dzpjInterfController {
    @Autowired
    DzpjInterfaceService diservice;


    /*三医院病人基本信息查询*/
    @RequestMapping(value = "getpainfobycard")
    public Object Getpainfobycard(@RequestParam(name = "cardno",required = true) String cardno){
        System.out.println("前台接收到cardno："+cardno);
        return diservice.getPatientInfo(cardno);
    }





















    /* 省医院*/
//    @RequestMapping(value = "getEwmTimeQuery")
//    public Object getEwmTimeQuery(@RequestParam(name = "decode",required = true) String decode,
//                                  @RequestParam(name = "chargetime",required = true) String chargetime,
//                                  @RequestParam(name = "pageno",required = true) int pageno,
//                                  @RequestParam(name = "pagesize",required = true) int pagesize,
//                                  @RequestParam(name = "Token",required = true) String Token) {
//
//        return diservice.getEwmTimeQuery(decode,chargetime,pageno,pagesize,Token);
//    }
//    /*二维码查询博士系统*/
//    @RequestMapping(value = "getEwmBillList")
//    public Object getEwmBillList(@RequestParam(name = "decode",required = true) String decode,
//                                 @RequestParam(name = "Token",required = true) String Token) {
////        Object ewmBillList = diservice.getEwmBillList(decode);
////        System.out.println(ewmBillList.toString()+"1231231312");
//        return diservice.getEwmBillList(decode,Token);
//    }
//    /*分页加展示列表*/
//    @RequestMapping(value = "getbilllist")
//    public Object Getbilllist(@RequestParam(name = "chargetime",required = true) String chargetime,
//                              @RequestParam(name = "patientid",required = true) String patientid,
//                              @RequestParam(name = "cardno",required = true) String cardno,
//                              @RequestParam(name = "pageno",required = true) int pageno,
//                              @RequestParam(name = "pagesize",required = true) int pagesize){
//
//        return diservice.getBillList(chargetime,patientid,cardno,pageno,pagesize);
//
//
////        String v = "{\n" +
////                "    \"data\": {\n" +
////                "        \"total\": \"4\",\n" +
////                "        \"pageNo\": \"1\",\n" +
////                "        \"billList\": [\n" +
////                "            {\n" +
////                "                \"busDate\": \"20190116121200001\",\n" +
////                "                \"billName\": \"四川省医疗卫生单位门诊结算票据（电子）\",\n" +
////                "                \"random\": \"3c23a3\",\n" +
////                "                \"ivcDateTime\": \"20190117095450557\",\n" +
////                "                \"totalAmt\": 200,\n" +
////                "                \"remark\": \"备注\",\n" +
////                "                \"busNo\": \"20190117000002\",\n" +
////                "                \"payer\": \"测试用户01\",\n" +
////                "                \"billNo\": \"0000012197\",\n" +
////                "                \"billBatchCode\": \"51060119\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"busDate\": \"20190116121200001\",\n" +
////                "                \"billName\": \"四川省医疗卫生单位门诊结算票据（电子）\",\n" +
////                "                \"random\": \"476705\",\n" +
////                "                \"ivcDateTime\": \"20190117100100230\",\n" +
////                "                \"totalAmt\": 200,\n" +
////                "                \"remark\": \"备注\",\n" +
////                "                \"busNo\": \"20190117000001\",\n" +
////                "                \"payer\": \"测试用户02\",\n" +
////                "                \"billNo\": \"0000012198\",\n" +
////                "                \"billBatchCode\": \"51060119\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"busDate\": \"20190117101200001\",\n" +
////                "                \"billName\": \"四川省医疗卫生单位门诊结算票据（电子）\",\n" +
////                "                \"random\": \"41cb06\",\n" +
////                "                \"ivcDateTime\": \"20190117102734520\",\n" +
////                "                \"totalAmt\": 200,\n" +
////                "                \"remark\": \"备注\",\n" +
////                "                \"busNo\": \"20190117000004\",\n" +
////                "                \"payer\": \"测试用户04\",\n" +
////                "                \"billNo\": \"0000016373\",\n" +
////                "                \"billBatchCode\": \"51060119\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"busDate\": \"20190117101200001\",\n" +
////                "                \"billName\": \"四川省医疗卫生单位门诊结算票据（电子）\",\n" +
////                "                \"random\": \"0e7747\",\n" +
////                "                \"ivcDateTime\": \"20190117101850969\",\n" +
////                "                \"totalAmt\": 200,\n" +
////                "                \"remark\": \"备注\",\n" +
////                "                \"busNo\": \"20190117000003\",\n" +
////                "                \"payer\": \"测试用户03\",\n" +
////                "                \"billNo\": \"0000016372\",\n" +
////                "                \"billBatchCode\": \"51060119\"\n" +
////                "            }\n" +
////                "        ]\n" +
////                "    },\n" +
////                "    \"message\": \"获取打印列表成功\",\n" +
////                "    \"status\": \"S_OK\"\n" +
////                "}";
////        return JSON.parse(v);
//    }
////getbillinfo
//    @RequestMapping(value = "getbillinfo")
//    public  Object Getbillinfo(@RequestParam(name = "billname",required = true) String billname,
//                               @RequestParam(name = "billbatchcode",required = true) String billbatchcode,
//                               @RequestParam(name = "billno",required = true) String billno,
//                               @RequestParam(name = "payer",required = true) String payer,
//                               @RequestParam(name = "random",required = true) String random,
//                               @RequestParam(name = "ivcdatetime",required = true) String ivcdatetime
//                              ){
//        System.out.println(ivcdatetime);
//          return diservice.getBillInfo(billname,billbatchcode,billno,payer,random,ivcdatetime);
//
////        String v ="{\n" +
////                "    \"data\": {\n" +
////                "        \"chargeDetail\": [\n" +
////                "            {\n" +
////                "                \"number\": \"1\",\n" +
////                "                \"unit\": \"元\",\n" +
////                "                \"std\": \"100\",\n" +
////                "                \"amt\": \"100\",\n" +
////                "                \"remark\": \"备注\",\n" +
////                "                \"selfAmt\": \"0\",\n" +
////                "                \"chargeName\": \"诊查费\",\n" +
////                "                \"chargeCode\": \"0202\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"number\": \"1\",\n" +
////                "                \"unit\": \"元\",\n" +
////                "                \"std\": \"100\",\n" +
////                "                \"amt\": \"100\",\n" +
////                "                \"remark\": \"备注\",\n" +
////                "                \"selfAmt\": \"0\",\n" +
////                "                \"chargeName\": \"手术费\",\n" +
////                "                \"chargeCode\": \"0201\"\n" +
////                "            }\n" +
////                "        ],\n" +
////                "        \"author\": \"票据编制人\",\n" +
////                "        \"cardType\": \"3101\",\n" +
////                "        \"remark\": \"备注\",\n" +
////                "        \"busDateTime\": \"20190116121200001\",\n" +
////                "        \"otherParameter\": [\n" +
////                "            {\n" +
////                "                \"infoName\": \"卡类型\",\n" +
////                "                \"infoValue\": \"3101\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"卡号\",\n" +
////                "                \"infoValue\": \"0123456789\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"医保类型\",\n" +
////                "                \"infoValue\": \"医保类型\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"性别\",\n" +
////                "                \"infoValue\": \"男\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"科室\",\n" +
////                "                \"infoValue\": \"科室\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"患者门诊号\",\n" +
////                "                \"infoValue\": \"患者门诊号\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"特殊病种名称\",\n" +
////                "                \"infoValue\": \"特殊病种名称\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"个人账户支付\",\n" +
////                "                \"infoValue\": \"0\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"个人账户余额\",\n" +
////                "                \"infoValue\": \"0\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"医保统筹基金支付\",\n" +
////                "                \"infoValue\": \"0\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"其它医保支付\",\n" +
////                "                \"infoValue\": \"0\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"自费金额\",\n" +
////                "                \"infoValue\": \"0\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"医疗机构类型\",\n" +
////                "                \"infoValue\": \"医疗机构类型\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"身份证号码\",\n" +
////                "                \"infoValue\": \"510101198801011234\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"交款人支付宝账户\",\n" +
////                "                \"infoValue\": \"患者支付宝账户\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"微信支付订单号\",\n" +
////                "                \"infoValue\": \"微信支付订单号\"\n" +
////                "            },\n" +
////                "            {\n" +
////                "                \"infoName\": \"医保信息名称\",\n" +
////                "                \"infoValue\": \"医保信息值\"\n" +
////                "            }\n" +
////                "        ],\n" +
////                "        \"cardNo\": \"0123456789\",\n" +
////                "        \"payer\": \"测试用户01\",\n" +
////                "        \"payee\": \"收款人\",\n" +
////                "        \"totalAmt\": 200,\n" +
////                "        \"otherSpecialParameter\": [\n" +
////                "            {\n" +
////                "                \"infoName\": \"扩展信息名称\",\n" +
////                "                \"infoValue\": \"扩展信息值\"\n" +
////                "            }\n" +
////                "        ],\n" +
////                "        \"listDetail\": [],\n" +
////                "        \"busNo\": \"20190117000002\",\n" +
////                "        \"busType\": \"02\",\n" +
////                "        \"placeCode\": \"001\"\n" +
////                "    },\n" +
////                "    \"message\": \"获取票据明细成功\",\n" +
////                "    \"status\": \"S_OK\"\n" +
////                "}";
////        return JSON.parse(v);
//    }
////
//    @RequestMapping(value = "getvalidbillbatchcode")
//    public  Object Getvalidbillbatchcode(){
//        return diservice.getValidBillBatchCode();
//    }
////    /*获取纸质票据*/
//    @RequestMapping(value = "getpaperbillno")
//    public  Object Getpaperbillno(@RequestParam(name = "pBillBatchCode",required = true) String billbatchcode,
//                                  @RequestParam(name = "Token",required = true) String Token){
//        return diservice.getPaperBillNo(billbatchcode,Token);
//
////        String v = "{\n" +
////                "\"data\": {\n" +
////                " \"pBillBatchCode\": \"4001\",\n" +
////                "  \"pBillNo\": \"0000094015\"\n" +
////                "    },\n" +
////                "    \"message\": \"获取当前纸质票据可用号码成功\",\n" +
////                "    \"status\": \"S_OK\"\n" +
////                "}\n";
////        return JSON.parse(v);
//    }
//
//    @RequestMapping(value = "turnpaper")
//    public  Object Turnpaper(@RequestParam(name = "billbatchcode",required = true) String billbatchcode,
//                             @RequestParam(name = "billno",required = true) String billno,
//                             @RequestParam(name = "pbillbatchcode",required = true) String pbillbatchcode,
//                             @RequestParam(name = "pbillno",required = true) String pbillno){
//        return diservice.turnPaper(billbatchcode,billno,pbillbatchcode,pbillno);
////        String v="{\n" +
////                "    \"data\": \"操作成功\",\n" +
////                "    \"message\": \"换开纸质票据成功\",\n" +
////                "    \"status\": \"S_OK\"\n" +
////                "}";
////        return JSON.parse(v);
//    }
//
//    @RequestMapping(value = "getvalidbillno")
//    public  Object getvalidbillno(){
//        return diservice.getValidBillNo();
//    }

}
