package com.wzcsoft.dzpjdy.service;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.wzcsoft.dzpjdy.util.JsonUtil;
import com.wzcsoft.dzpjdy.util.PdfUtil;
import com.wzcsoft.dzpjdy.util.ThreeHospitalsToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author lyj
 * @date 2019/8/26 17:48
 */
@Service
public class ThreeHospitalsServiceImpl implements ThreeHospitalsService{
    private static   Log log = LogFactory.getLog(ThreeHospitalsServiceImpl.class);
    @Value("${bossinterf.appid}")
    private String _appid;

    @Value("${bossinterf.key}")
    private String _key;

    @Value("${bossinterf.baseurl}")
    private String _baseurl;

    @Value("${bossinterf.placecode}")
    private String _placecode;

    @Value("${bossinterf.machinename}")
    private String _machinename;

    @Value("${pdf.template.fullname}")
    private String _pdf_template_fullname;

    @Value("${pdf.file.directory}")
    private String _pdf_file_directory;

    @Value("${pdf.field.paycompany}")
    private String _pdf_field_paycompany;

    @Value("${pdf.query.url}")
    private String _pdf_query_url;

    /*授权id*/
    @Value("${bossinterf.id}")
    private String bossinterf_id;
    /*授权账号*/
    @Value("${bossinterf.password}")
    private String bossinterf_password;
    /*获取token路劲*/
    @Value("${bossinterf.baseurl_3}")
    private String bossinterf_baseurl_3;
    /*厂家代码*/
    @Value("${bossinterf.vendorCode}")
    private String bossinterf_vendorCode;
    /*操作员编号*/
    @Value("${bossinterf.operatorNo}")
    private String bossinterf_operatorNo;
    @Value("${pageinit.title}")
    private String _pagetitle;

    /*etk_getEBillUnPrintList  2 todo  获取电子票据未换开列表接口*/
    @Override
    public Object getBillList(String chargetime, String patientid, String cardno, int pageno, int pagesize) {
        cn.hutool.json.JSONObject dataJsonObject = new cn.hutool.json.JSONObject();
        Map<String,Object> retMap = new HashMap<>();
        //公共参数
        cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
        //医院代码
        basic_1.put("hospitalCode","450754387");
        //分院代码
        basic_1.put("branchCode","01");
        //医联体代码
        basic_1.put("medicalCombo","0");
        //操作员号
        basic_1.put("operatorNo","0204");
        //厂商代码
        basic_1.put("vendorCode","ASP0006");
        //交易代码
        basic_1.put("transactionCode","etk_getEBillUnPrintList");
        //是否分页
        basic_1.put("needPage", "true");
        //当前页数
        basic_1.put("pageNo", pageno);
        //每页条数
        basic_1.put("pageSize", pagesize);
        Date inputDate,endDate,startDate;
        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(chargetime);
        }catch (Exception ex){
            retMap.put("status", "S_FALSE");
            retMap.put("message", "日期格式不正确");
            return retMap;
        }
        startDate = new Date(inputDate.getTime() + (-7) * 24 * 60 * 60 * 1000L);
        endDate = new Date(inputDate.getTime() + 24 * 60 * 60 * 1000L -1);
        //开始时间
        String startDateStr = dd.format(startDate);
        //结束时间
        String endDateStr = dd.format(endDate);
        //封装业务参数
        cn.hutool.json.JSONObject detailResult = new cn.hutool.json.JSONObject();
        //开始时间
        detailResult.put("busBgnDate", "20190501093618604");
        //结束时间
        detailResult.put("busEndDate", "20190828114043989");
        //cardType	卡类型
        detailResult.put("cardType", "3101");
        // todo 卡号
        detailResult.put("cardNo", "990300000888837");
        //页码
        detailResult.put("pageNo", "1");
        //每页条数
        detailResult.put("pageSize", "30");
        //再次封装参数
        dataJsonObject.put("basic", basic_1);
        dataJsonObject.put("detail", detailResult);
        dataJsonObject.put("enc","F");

        Map<String, Object> etk_getEBillUnPrintList = callInterface("etk_getEBillUnPrintList", dataJsonObject);
        log.info("etk_getEBillUnPrintList"+etk_getEBillUnPrintList);
        System.out.println("etk_getEBillUnPrintList"+etk_getEBillUnPrintList);
        return etk_getEBillUnPrintList;
    }
    /* 3 获取当前纸质票据可用号码接口  3 todo etk_getPaperBillNo*/
    @Override
    public Object etk_getPaperBillNo(String billbatchcode) {
        cn.hutool.json.JSONObject dataJsonObject = new cn.hutool.json.JSONObject();
        //公共参数
        cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
        //医院代码
        basic_1.put("hospitalCode","450754387");
        //分院代码
        basic_1.put("branchCode","01");
        //医联体代码
        basic_1.put("medicalCombo","1");
        //操作员号
        basic_1.put("operatorNo","009");
        //厂商代码
        basic_1.put("vendorCode","ASP0006");
        //交易代码
        basic_1.put("transactionCode","etk_getPaperBillNo");
        //是否分页
        basic_1.put("needPage", "true");
        //当前页数
        basic_1.put("pageNo", 1);
        //每页条数
        basic_1.put("pageSize", 10);

        cn.hutool.json.JSONObject detailResult = new cn.hutool.json.JSONObject();
        //todo 开票点编码 placeCode
        detailResult.put("placeCode", "001");
        //todo  pBillBatchCode 纸质票据代
        detailResult.put("pBillBatchCode", "4001");
        //再次封装参数
        dataJsonObject.put("basic", basic_1);
        dataJsonObject.put("detail", detailResult);
        dataJsonObject.put("enc","F");
        Map<String, Object> etk_getPaperBillNo = callInterface("etk_getPaperBillNo", dataJsonObject);
        log.info("etk_getPaperBillNo"+etk_getPaperBillNo);
        System.out.println("etk_getPaperBillNo"+etk_getPaperBillNo);
        return etk_getPaperBillNo;
    }
    /*获取电子票据明细接口 4  todo etk_getBillDetail*/
    @Override
    public Object getBillInfo(String billname, String billbatchcode, String billno, String payer, String random, String ivcdatetime) {
        cn.hutool.json.JSONObject dataJsonObject = new cn.hutool.json.JSONObject();
        //公共参数
        cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
        //医院代码
        basic_1.put("hospitalCode","450754387");
        //分院代码
        basic_1.put("branchCode","01");
        //医联体代码
        basic_1.put("medicalCombo","1");
        //操作员号
        basic_1.put("operatorNo","009");
        //厂商代码
        basic_1.put("vendorCode","ASP0006");
        //交易代码
        basic_1.put("transactionCode","etk_getBillDetail");
        //是否分页
        basic_1.put("needPage", "true");
        //当前页数
        basic_1.put("pageNo", 1);
        //每页条数
        basic_1.put("pageSize", 10);

        cn.hutool.json.JSONObject detailResult = new cn.hutool.json.JSONObject();
        //电子票据代码
        detailResult.put("billBatchCode", "510600119");
        //电子票据号码
        detailResult.put("billNo", "0000181425");
        //电子校验码
        detailResult.put("random", "af3c4e");
        dataJsonObject.put("basic", basic_1);
        dataJsonObject.put("detail", detailResult);
        dataJsonObject.put("enc","F");
        Map<String, Object> etk_getBillDetail = callInterface("etk_getBillDetail", dataJsonObject);
        log.info("etk_getBillDetail"+etk_getBillDetail);
        if( etk_getBillDetail.containsKey("data")){
            com.alibaba.fastjson.JSONObject dataJson = (com.alibaba.fastjson.JSONObject)(etk_getBillDetail.get("data"));
            String pdffile = PdfUtil.writeFaPiaoPdf(dataJson,_pdf_template_fullname,_pdf_file_directory, billname, billbatchcode, billno, payer, random, ivcdatetime,_pdf_field_paycompany,_pdf_query_url);
            etk_getBillDetail.put("pdffile",pdffile);
        }
        return etk_getBillDetail;
    }
    /*获取票断  5  todo etk_getValidBillNo*/
    @Override
    public Object THpageinit() {
        String billBgnNo="0";
        String billEndNo="0";
        String curbillno = "0";
        long surplus = 0;
        Map<String,Object> retMap = new HashMap<>();
        cn.hutool.json.JSONObject dataJsonObject = new cn.hutool.json.JSONObject();
        //公共参数
        cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
        //医院代码
        basic_1.put("hospitalCode","450754387");
        //分院代码
        basic_1.put("branchCode","01");
        //医联体代码
        basic_1.put("medicalCombo","1");
        //操作员号
        basic_1.put("operatorNo","009");
        //厂商代码
        basic_1.put("vendorCode","ASP0006");
        //交易代码
        basic_1.put("transactionCode","etk_getValidBillNo");
        //是否分页
        basic_1.put("needPage", "true");
        //当前页数
        basic_1.put("pageNo", 1);
        //每页条数
        basic_1.put("pageSize", 10);

        cn.hutool.json.JSONObject detailResult = new cn.hutool.json.JSONObject();
        //todo 开票点编码
        detailResult.put("placeCode", "001");
        // todo 票据种类代码
//        detailResult.put("billCode", "4002");
        // todo 票据代码
//        detailResult.put("billBatchCode", "51060119");
        dataJsonObject.put("basic", basic_1);
        dataJsonObject.put("detail", detailResult);
        dataJsonObject.put("enc","F");
        Map<String, Object> etk_getBillDetail = callInterface("etk_getValidBillNo", dataJsonObject);
        //信息记录到日志
        log.info(etk_getBillDetail);
        String jsonStr=null;
        try {
            jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(etk_getBillDetail);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("转换异常"+e.getMessage());

            log.error("错误信息"+e.getMessage());
        }

        System.out.println("jsonStr"+jsonStr);
        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
        System.out.println("json"+json);
        String status = json.getString("status");
        String message = json.getString("message");
        if(status.equals("S_OK")){
            com.alibaba.fastjson.JSONObject data = json.getJSONObject("data");
            long count = data.getLong("count");
            JSONArray billNoList = data.getJSONArray("billNoList");
            if (billNoList.size()>0){
                billBgnNo = billNoList.getJSONObject(0).getString("billBgnNo");
                billEndNo = billNoList.getJSONObject(0).getString("billEndNo");
                count = Long.parseLong(billEndNo) - Long.parseLong(billBgnNo) + 1;
            }
            curbillno = billBgnNo;
            surplus = count;

            retMap.put("sn",billBgnNo + " - " + billEndNo);
            retMap.put("curbillno",curbillno);
            retMap.put("surplus",surplus);
            retMap.put("pagetile",_pagetitle);
            retMap.put("status","S_OK");
            retMap.put("message",message);
        }else{
            retMap.put("status","S_FALSE");
            retMap.put("message",message);
        }
        return retMap;
    }
    /*换开纸质票据（电子票据打印）接口  etk_turnPaper*/
    @Override
    public Object turnPaper(String billbatchcode, String billno, String pbillbatchcode, String pbillno) {
        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        cn.hutool.json.JSONObject dataJsonObject = new cn.hutool.json.JSONObject();
        //公共参数
        cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
        //医院代码
        basic_1.put("hospitalCode","450754387");
        //分院代码
        basic_1.put("branchCode","01");
        //医联体代码
        basic_1.put("medicalCombo","1");
        //操作员号
        basic_1.put("operatorNo","009");
        //厂商代码
        basic_1.put("vendorCode","ASP0006");
        //交易代码
        basic_1.put("transactionCode","etk_turnPaper");
        //是否分页
        basic_1.put("needPage", "true");
        //当前页数
        basic_1.put("pageNo", 1);
        //每页条数
        basic_1.put("pageSize", 10);

        cn.hutool.json.JSONObject detailResult = new cn.hutool.json.JSONObject();
        //电子票据代码
        detailResult.put("billBatchCode", "510606119");
        //电子票据号
        detailResult.put("billNo","0000181430" );
        //纸质票据代码
        detailResult.put("pBillBatchCode","4001");
        //纸质票据号
        detailResult.put("pBillNo","991000004");
        //业务发生时间
        detailResult.put("busDateTime","20190509101328819");
        //todo 开票点编码
        detailResult.put("placeCode","001");
        //todo 经办人
        detailResult.put("operator","111-1");

        dataJsonObject.put("basic", basic_1);
        dataJsonObject.put("detail", detailResult);
        dataJsonObject.put("enc","F");
        Map<String, Object> etk_turnPaper = callInterface("etk_turnPaper", dataJsonObject);
        log.info("etk_turnPaper"+etk_turnPaper);
        System.out.println("成功打印后返回的信息"+etk_turnPaper);
        return etk_turnPaper;
    }

    /*====================私有方法==================*/
    private Map<String,Object> callInterface(String url, cn.hutool.json.JSONObject dataJsonObject) {
        Map<String,Object> retMap = new HashMap<>();
        String Token = null;
        try {
            // todo   获取验码
            Token = ThreeHospitalsToken.getThreeHospitalsToken(bossinterf_baseurl_3, bossinterf_id, bossinterf_password);
        }catch (Exception e){
            System.out.println("获取token失败:"+e.getMessage());
        }
        RestTemplate template2 = new RestTemplate();
        //todo 调试接口
        String url3 = "http://10.100.3.113:18140/hsop/business/entrance/operateInfoJson";
        String url2 = "http://localhost:8095/printlog/Test_1";
        String url1 = "http://localhost:8095/printlog/etk_getEBillUnPrintList";
        String url0 = "http://localhost:8095/printlog/etk_getPaperBillNo";
        String url9 = "http://localhost:8095/printlog/etk_getBillDetail";
        String url8 = "http://localhost:8095/printlog/etk_getValidBillNo";
        String url7 = "http://localhost:8095/printlog/etk_getValidBillNo";
        HttpHeaders headers2 = new HttpHeaders();
        String authorization = "Bearer " + Token;
        //todo
//        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
//        headers2.setContentType(type);
        headers2.add("Authorization",authorization);
        HttpEntity<JSONObject> request2 = new HttpEntity<>(dataJsonObject, headers2);
        //todo 测试
//        ResponseEntity<String> response2 = null;
//        if(url.equals("etk_getEBillUnPrintList")){
//            response2 = template2.postForEntity(url1, request2, String.class);
//        }else if(url.equals("etk_getPaperBillNo")){
//            response2 = template2.postForEntity(url0, request2, String.class);
//        }else if(url.equals("etk_getBillDetail")){
//            response2 = template2.postForEntity(url9, request2, String.class);
//        }else  if(url.equals("etk_getValidBillNo")){
//            response2 = template2.postForEntity(url8, request2, String.class);
//        }else if(url.equals("etk_turnPaper")){
//            response2 = template2.postForEntity(url7, request2, String.class);
//        }
        //todo 医院测试接口
        ResponseEntity<String> response2 = template2.postForEntity(url3, request2, String.class);
        String retString = response2.getBody();
        log.info("返回参数"+retString);
        System.out.println("返回的body"+retString);
        com.alibaba.fastjson.JSONObject retJson = com.alibaba.fastjson.JSONObject.parseObject(retString);
        com.alibaba.fastjson.JSONObject result_1 = null;
        try {
            result_1 = retJson.getJSONObject("result");
        }catch (Exception e){
            System.out.println("json解析错误"+e.getMessage());
        }

//        System.out.println("result_1"+result_1);
        String result = result_1.getString("result");
//        System.out.println("result"+result);
        String message = result_1.getString("message");
//        System.out.println("message"+message);
        if (result.equals("S0000")){
            retMap.put("status","S_OK");
            retMap.put("message",url + "成功");
            try{
                retMap.put("data", com.alibaba.fastjson.JSONObject.parseObject(message));
            }catch (Exception ex){
                System.out.println("解析message错误"+ex.getMessage());
            }
        }else{
            retMap.put("status","S_FALSE");
            retMap.put("message","无开票信息");
            retMap.put("errmsg",result + ":" + message);
        }
        //todo
        return retMap;
    }

    }
