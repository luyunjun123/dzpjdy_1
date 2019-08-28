package com.wzcsoft.dzpjdy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.wsdl.ServiceImpl;
import com.wzcsoft.dzpjdy.util.JsonUtil;
import com.wzcsoft.dzpjdy.util.PdfUtil;
import com.wzcsoft.dzpjdy.util.ThreeHospitalsToken;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */

@Service
public class DzpjInterfaceServiceImpl implements DzpjInterfaceService {
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

    @Autowired
    private RestTemplate restTemplate;
    /*_1三医院病人信息基本查询*/
    @Override
    public Object getPatientInfo(String cardno) {

        Map<Object, Object> retMap = new HashMap<>();
        try {
            String Token = ThreeHospitalsToken.getThreeHospitalsToken(bossinterf_baseurl_3, bossinterf_id, bossinterf_password);
            RestTemplate template2 = new RestTemplate();
            String url2 = "http://10.100.3.113:18140/hsop/business/entrance/operateInfoJson";
            //todo 测试接口
//            String url2 = "http://localhost:8095/printlog/Test_1";
            HttpHeaders headers2 = new HttpHeaders();
            String authorization = "Bearer " + Token;
            //MediaType type2 = MediaType.parseMediaType("application/json");
            //todo 中文乱码问题
//            MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
//            headers2.setContentType(type);
            headers2.add("Authorization",authorization);
            //basic
            cn.hutool.json.JSONObject dataJsonObject = new cn.hutool.json.JSONObject();
            cn.hutool.json.JSONObject basic_1 = new cn.hutool.json.JSONObject();
            basic_1.put("hospitalCode","450754387");
            basic_1.put("branchCode","01");
            basic_1.put("medicalCombo","1");
            basic_1.put("operatorNo","009");
            basic_1.put("vendorCode","ASP0006");
            basic_1.put("transactionCode","opt_0001");
            basic_1.put("needPage", "true");
            basic_1.put("pageNo", 1);
            basic_1.put("pageSize", 10);
            cn.hutool.json.JSONObject detailResult = new cn.hutool.json.JSONObject();
            detailResult.put("queryType", "1");
            //todo 死数据
            String cardno_1="1801089820";
            detailResult.put("queryValue", cardno_1);
            dataJsonObject.put("basic", basic_1);
            dataJsonObject.put("detail", detailResult);
            dataJsonObject.put("enc","F");
            HttpEntity<cn.hutool.json.JSONObject> request2 = new HttpEntity<>(dataJsonObject, headers2);
         //   HttpHeaders headers = request2.getHeaders();
            //todo
           // System.out.println("发送的headers"+headers);
          //  cn.hutool.json.JSONObject body1 = request2.getBody();
            //todo
          //  System.out.println("发送的body1"+body1);
            ResponseEntity<String> response2 = template2.postForEntity(url2, request2, String.class);
            String body = response2.getBody();
            //todo
//            System.out.println("返回的数据"+body);
            Map<String, Object> bodymaps = JsonUtil.getBody(body);
            //todo
            String  status = (String)bodymaps.get("status");


            if(status.equals("T")){
                System.out.println("解析后的boy"+bodymaps);
                retMap.put("status","S_OK");
                retMap.put("message","获取基本信息" + "成功");
                String  content = (String)bodymaps.get("content");
                String s=null;
                try {
                    s = content.replaceAll("[\\[\\]]", "");
                }catch (Exception e){
                    System.out.println("转换错误_111"+e.getMessage());
                }

                String  pageNo = (String)bodymaps.get("pageNo");
                String  total = (String)bodymaps.get("total");
                retMap.put("data",JSONObject.parseObject(s));
                retMap.put("total",total);
                retMap.put("pageNo",pageNo);
            }else {
                String  msgCode = (String)bodymaps.get("msgCode");
                retMap.put("status","S_FALSE");
                retMap.put("msgCode",msgCode);
                retMap.put("errmsg",status);
            }

            return retMap;
        }catch (Exception e) {
            System.out.println("异常信息"+e);
        }


        return retMap;
    }




    //公共参数
    public Map<String ,Object>getBasic(){
        Long timeInMillis = Calendar.getInstance().getTimeInMillis();
        HashMap<String, Object> basicmap = new HashMap<>();
        //  厂家代码
        basicmap.put("vendorCode", bossinterf_vendorCode);
        //医院代码
        basicmap.put("hospitalCode", "450754387");
        //分院代码
        basicmap.put("branchCode", "01");
        //医联体代码
        basicmap.put("medicalCombo", "1");
        //操作员号
        basicmap.put("operatorNo", bossinterf_operatorNo);
        //终端 ID
        basicmap.put("terminalId", "123456");
        //交易代码
        basicmap.put(" transactionCode", "opt_0001");
        //版本号
        basicmap.put(" version", "V1.0.3");
        //时间戳
        basicmap.put(" timestamp", timeInMillis);
        //是否分页
        basicmap.put(" needPage", "false");
        //当前页数
        basicmap.put(" pageNo", 1);
        //每页条数
        basicmap.put(" pageSize", 10);
        return basicmap;
    }



















//    /*_2获取打印列表*/
//    @Override
//    public Object getBillList(String timeStr, String patientId, String cardNo, int pageNo, int pageSize){
//
//        String Token = ThreeHospitalsToken.getThreeHospitalsToken(bossinterf_baseurl_3, bossinterf_id, bossinterf_password);
//
//        Map<String, Object> retMap = new HashMap();
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        Date inputDate;
//        try {
//            inputDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(timeStr);
//        } catch (Exception var19) {
//            retMap.put("status", "S_FALSE");
//            retMap.put("message", "日期格式不正确");
//            return retMap;
//        }
//
//        Date startDate = new Date(inputDate.getTime() + -604800000L);
//        Date endDate = new Date(inputDate.getTime() + 86400000L - 1L);
//        String startDateStr = dd.format(startDate);
//        String endDateStr = dd.format(endDate);
//        Map<String, Object> paramMap = new HashMap();
//        Map<String, Object> dataMap = new HashMap();
//        dataMap.put("busBgnDate", startDateStr);
//        dataMap.put("busEndDate", endDateStr);
//        dataMap.put("cardType", "3101");
//        dataMap.put("cardNo", cardNo);
//        dataMap.put("patientId", patientId);
//        dataMap.put("pageNo", pageNo);
//        dataMap.put("pageSize", pageSize);
//        Map<String, Object> basic = getBasic();
//        //公共参数
//        paramMap.put("basic ", basic);
//        //业务参数
//        paramMap.put("detail ", dataMap);
//        //是否加密
//        paramMap.put("enc ", "F");
//        Map<String, Object> stringObjectMap = callInterface(paramMap, "etk_getEBillUnPrintList", "获取打印列表");
//        stringObjectMap.put("Token", Token);
//        return stringObjectMap;
//    }
//
//
//
//    @Override
//    public Object getEwmTimeQuery(String decode, String chargetime,int pageNo ,int pageSize,String Token) {
//        //初始化变量
//        Map<String,Object> retMap = new HashMap<>();
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        //1、时间条件转换
//        Date inputDate,endDate,startDate;
//        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        try {
//            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(chargetime);
//        }catch (Exception ex){
//            retMap.put("status", "S_FALSE");
//            retMap.put("message", "日期格式不正确");
//            return retMap;
//        }
//        startDate = new Date(inputDate.getTime() + (-7) * 24 * 60 * 60 * 1000L);
//        endDate = new Date(inputDate.getTime() + 24 * 60 * 60 * 1000L -1);
//        String startDateStr = dd.format(startDate);
//        String endDateStr = dd.format(endDate);
//        System.out.println(startDateStr+"开始时间");
//        System.out.println(endDateStr+"结束时间");
//        //2、处理接口参数
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//
//        dataMap.put("busBgnDate",startDateStr);
//        dataMap.put("busEndDate",endDateStr);
////        dataMap.put("busType","02");
//        dataMap.put("cardType","3101");
//        String cardNo="00";
//        dataMap.put("cardNo",cardNo);
//        //二维码唯一标识
//        dataMap.put("patientId",decode);
//        dataMap.put("pageNo",pageNo);
//        dataMap.put("pageSize",pageSize);
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
//        Map<String, Object> stringObjectMap = callInterface(paramMap, "getEBillUnPrintList", "获取打印列表");
//        //3、调用接口
//
//        return stringObjectMap;
//    }
//
//
//
//    //二维码获取从博士获取打印列表
//    @Override
//    public Object getEwmBillList(String decode,String Token) {
//        System.out.println(decode+"二维码");
//        //初始化变量
//        Map<String,Object> retMap = new HashMap<>();
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        //1、时间条件转换
//        Date inputDate,endDate,startDate;
//        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//
//        //获取当前时间日期
//        Date date = new Date();
//        //设置要获取到什么样的时间
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        //获取String类型的时间
//        String timeStr = sdf.format(date);
//
//        try {
//            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(timeStr);
//        }catch (Exception ex){
//            retMap.put("status", "S_FALSE");
//            retMap.put("message", "日期格式不正确");
//            return retMap;
//        }
//        startDate = new Date(inputDate.getTime() + (-7) * 24 * 60 * 60 * 1000L);
//        endDate = new Date(inputDate.getTime() + 24 * 60 * 60 * 1000L -1);
//        String startDateStr = dd.format(startDate);
//        String endDateStr = dd.format(endDate);
//        System.out.println(startDateStr+"开始时间");
//        System.out.println(endDateStr+"结束时间");
//        //2、处理接口参数
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//
//        dataMap.put("busBgnDate",startDateStr);
//        dataMap.put("busEndDate",endDateStr);
////        dataMap.put("busType","02");
//        dataMap.put("cardType","3101");
//        String cardNo="00";
//        dataMap.put("cardNo",cardNo);
//        //二维码唯一标识
//        dataMap.put("patientId",decode);
//        int pageNo=1;
//        int pageSize=10;
//        dataMap.put("pageNo",pageNo);
//        dataMap.put("pageSize",pageSize);
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
//        Map<String, Object> stringObjectMap = callInterface(paramMap, "getEBillUnPrintList", "获取打印列表");
//        return stringObjectMap;
//    }
//
//
//
//    /*获取三级明细*/
//    @Override
//    public Object getBillInfo(String billName,String billBatchCode,String billNo,
//                              String payer,String random,String ivcDateTime){
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        //1、处理参数
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//            /*billBatchCode	电子票据代码	String	50	是
//            billNo	电子票据号码	String	20	是
//            random	电子校验码	String	20	是
//            4.1.15.4返回结果参数
//            参数	描述	类型	长度	必填	补充
//            result	返回结果标识	String	10	是	S0000
//            message	返回结果内容	String	不限	是	详见 B-1，JSON格式，BASE64 */
//        dataMap.put("billBatchCode",billBatchCode);
//        dataMap.put("billNo",billNo);
//        dataMap.put("random",random);
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
////        busNo	业务流水号	String	50	是	单位内部唯一
////        busType	业务标识	String	20	是	直接填写业务系统内部编码值，由医疗平台配置对照，列如：附录5 业务标识列表
////        值：02，标识门诊
////        payer	患者姓名	String	100	是
////        busDateTime	业务发生时间	String	17	是	格式：yyyyMMddHHmmssSSS
////        placeCode	开票点编码	String	50	是	医疗平台编码
////        payee	收费员	String	50	是
//        //2、调用接口
//        /*三级明细  最终打印的东西*/
//        Map<String,Object> retMap = callInterface(paramMap,"getBillDetail","获取票据明细");
////
//        if( retMap.containsKey("data")){
//            JSONObject dataJson = (JSONObject)(retMap.get("data"));
//            String pdffile = PdfUtil.writeFaPiaoPdf(dataJson,_pdf_template_fullname,_pdf_file_directory, billName, billBatchCode, billNo, payer, random, ivcDateTime,_pdf_field_paycompany,_pdf_query_url);
//            retMap.put("pdffile",pdffile);
//        }
//        return retMap;
//    }
//
//    @Override
//    public Object getValidBillBatchCode(){
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//
//        dataMap.put("placeCode",_placecode);
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
//
//        //2、调用接口
//        //获取票据有效票据代码列表接口
//        return callInterface_1(paramMap,"getValidBillBatchCode","获取纸质票据有效票据代码列表");
//    }
//    /*获取当期纸质票据可用号码接口  #电子票据系统终端位置编码*/
//    @Override
//    public Object getPaperBillNo(String pBillBatchCode,String Token){
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//
//        //1、处理参数
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//        //#  必传参数  电子票据系统终端位置编码
//        dataMap.put("placeCode",_placecode);
////        纸质票据代码毕传参数
//        dataMap.put("pBillBatchCode",pBillBatchCode);
//        //
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
//
//        return callInterface(paramMap,"getPaperBillNo","获取当前纸质票据可用号码");
//    }
//    // 业务系统根据电子票据信息，向医疗电子票据管理平台发起电子票据换开纸质票据请求，
//    // 生成纸质票据
//    @Override
//    public Object turnPaper(String billBatchCode,String billNo,String pBillBatchCode,String pBillNo){
//        String Token = ThreeHospitalsToken.getThreeHospitalsToken(bossinterf_baseurl_3, bossinterf_id, bossinterf_password);
//
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//
//        //1、处理参数
////        billBatchCode	电子票据代码	String	50	是
////        billNo	    电子票据号	    String	20	是
////        pBillBatchCode	纸质票据代码	String	50	是
////        pBillNo	    纸质票据号	    String	20	是
////        busDateTime	业务发生时间  	    String	17	是	yyyyMMddHHmmssSSS
////        placeCode	    开票点编码	    String	50	是	直接填写业务系统内部编码值，由医疗平台配置对照
////        operator	    经办人	        String	60	是
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//
//        dataMap.put("billBatchCode",billBatchCode);
//        dataMap.put("billNo",billNo);
//        dataMap.put("pBillBatchCode",pBillBatchCode);
//        dataMap.put("pBillNo",pBillNo);
//        dataMap.put("busDateTime",dd.format(new Date()));
//        dataMap.put("placeCode",_placecode);
//        dataMap.put("operator",_machinename);
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
//
//        return callInterface(paramMap,"turnPaper","换开纸质票据");
//    }
//    /* 获取票断  */
//    @Override
//    public Object getValidBillNo(){
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        Map<String,Object> paramMap = new HashMap<>();
//        Map<String,Object> dataMap = new HashMap<>();
//        //电子票据系统终端位置编码
//        dataMap.put("placeCode",_placecode);
//        //转换
//        String data = mapToBase64(dataMap);
//
//        paramMap.put("appid",_appid);
//        paramMap.put("data",data);
//        paramMap.put("noise",uuid);
//        paramMap.put("version","1.0");
//        /*签名*/
//        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
//        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
//        /*sign  签名*/
//        paramMap.put("sign", MD5(signOrigin1).toUpperCase());
//
////        String v = "{\n" +
////                "\t\"status\": \"S_OK\",\n" +
////                "\t\"message\": \"\",\n" +
////                "\t\"data\": {\n" +
////                "\"count\": \"1000\",\n" +
////                "\"billNoList\": [{\n" +
////                "\"billCode\": \"40001\",\n" +
////                "\"billBatchCode\": \"40001\",\n" +
////                "\t\t\t\"billBgnNo\": \"00001091\",\n" +
////                "\t\t\t\"billEndNo\": \"00002091\"\n" +
////                "}]\n" +
////                "\t}\n" +
////                "}\n";
////
////        return JSONObject.parse(v);
//
//        return callInterface(paramMap,"getValidBillNo","获取纸质/电子票据有效票据号段接口");
//    }
//
//
//    private Map<String,Object> callInterface(Map<String,Object> paramMap,String callMethod,String mCName) {
//        Map<String,Object> retMap = new HashMap<>();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //TODO
//        //设置请求头
//        headers.setBearerAuth("eWlodWFuOnlpaHVhbg==");
//        Map<String, Object> pathParam= new HashMap<>();
//        String responseText;
//        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(paramMap), headers);
//        try {
//
//            ResponseEntity<String> rss = restTemplate.exchange(_baseurl + callMethod, HttpMethod.POST, requestEntity, String.class, pathParam);
//            responseText = rss.getBody();
//        }catch (Exception ex){
//            retMap.put("status","S_FALSE");
//            retMap.put("message",ex.getMessage());
//            return retMap;
//        }
//        //3、解析返回值
//        JSONObject rensponseJson = JSONObject.parseObject(responseText);
//        String retString = null;
//        try {
//            retString= rensponseJson.getString("data");
//            byte[] data = Base64.decodeBase64(rensponseJson.getString("data"));
//            retString = new String(Base64.decodeBase64(rensponseJson.getString("data")),"UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        JSONObject retJson = JSONObject.parseObject(retString);
//        String result = retJson.getString("result");
//        String message = retJson.getString("message");
//        String realMessage = null;
//        try {
//            realMessage = new String(Base64.decodeBase64(message),"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        System.out.println("后台信息result:"+result);
//        if (result.equals("S0000")){
//            retMap.put("status","S_OK");
//            retMap.put("message",mCName + "成功");
//
//            try{
//                retMap.put("data", JSONObject.parseObject(realMessage));
//
//            }catch (Exception ex){
//                //retMap.put("data",realMessage);
//            }
//        }else{
//            retMap.put("status","S_FALSE");
//            retMap.put("message","无开票信息");
//            retMap.put("errmsg",result + ":" + realMessage);
//        }
//
//        return retMap;
//    }
//
//
//    //#########私有方法#########//
//    private Map<String,Object> callInterface_1(Map<String,Object> paramMap,String callMethod,String mCName) {
//        Map<String,Object> retMap = new HashMap<>();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //三医院获得token
//        String Token = ThreeHospitalsToken.getThreeHospitalsToken(bossinterf_baseurl_3, bossinterf_id, bossinterf_password);
//
//        //TODO
//        System.out.println("查询病人基本信息获取token获取到的token"+Token);
//        //设置请求头
//        headers.setBearerAuth(Token);
//
//        Map<String, Object> pathParam= new HashMap<>();
//        String responseText;
//        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(paramMap), headers);
//        HttpHeaders headers2 = requestEntity.getHeaders();
//        System.out.println("headers2"+headers2);
//        try {
//            //获得响应对象
////            说明：1）url: 请求地址；
////            2）method: 请求类型(如：POST,PUT,DELETE,GET)；
////            3）requestEntity: 请求实体，封装请求头，请求内容
////            4）responseType: 响应类型，根据服务接口的返回类型决定
////            5）uriVariables: url中参数变量值
//            ResponseEntity<String> rss = restTemplate.exchange(_baseurl, HttpMethod.POST, requestEntity, String.class, pathParam);
//            responseText = rss.getBody();
//            System.out.println("返回数据"+responseText);
//        }catch (Exception ex){
//            retMap.put("status","S_FALSE");
//            retMap.put("message",ex.getMessage());
//            return retMap;
//        }
//        Map<String, Object> body = JsonUtil.getBody(responseText);
//        String  status = (String)body.get("status");
//        if(status.equals("T")){
//            retMap.put("status","S_OK");
//            retMap.put("message",mCName + "成功");
//            String  content = (String)body.get("content");
//            String  pageNo = (String)body.get("pageNo");
//            String  total = (String)body.get("total");
//            retMap.put("data",content);
//            retMap.put("total",total);
//            retMap.put("pageNo",pageNo);
//        }else {
//            retMap.put("status","S_FALSE");
//            retMap.put("message","无开票信息");
//            retMap.put("errmsg",status);
//        }
//        return retMap;
//    }


    private String mapToBase64(Map<String,Object> map){
        String Str = JSON.toJSONString(map);
        Base64 base64 = new Base64();
        String base64Sign;
        try {
            base64Sign = base64.encodeToString(Str.getBytes("UTF-8"));
        }catch (Exception ex){
            return "Error:" + ex.getMessage();
        }
        return base64Sign;
    }

    private static String MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

}
