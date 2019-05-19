package com.wzcsoft.dzpjdy.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzcsoft.dzpjdy.util.PdfUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 邢直 on 2019/1/9.
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

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object getBillList(String timeStr,String patientId,String cardNo,int pageNo,int pageSize){
        //初始化变量
        Map<String,Object> retMap = new HashMap<>();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");


        //1、时间条件转换
        Date inputDate,endDate,startDate;
        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");

        try {
            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(timeStr);
        }catch (Exception ex){
            retMap.put("status", "S_FALSE");
            retMap.put("message", "日期格式不正确");
            return retMap;
        }
        startDate = new Date(inputDate.getTime() + (-7) * 24 * 60 * 60 * 1000L);
        endDate = new Date(inputDate.getTime() + 24 * 60 * 60 * 1000L -1);
        String startDateStr = dd.format(startDate);
        String endDateStr = dd.format(endDate);

        //2、处理接口参数
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("busBgnDate",startDateStr);
        dataMap.put("busEndDate",endDateStr);
//        dataMap.put("busType","02");
        dataMap.put("cardType","3101");
        dataMap.put("cardNo",cardNo);
        //dataMap.put("patientId",patientId);
        dataMap.put("pageNo",pageNo);
        dataMap.put("pageSize",pageSize);
        String data = mapToBase64(dataMap);

        paramMap.put("appid",_appid);
        paramMap.put("data",data);
        paramMap.put("noise",uuid);
        paramMap.put("version","1.0");

        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
        paramMap.put("sign", MD5(signOrigin1).toUpperCase());

        //3、调用接口
        return callInterface(paramMap,"getEBillUnPrintList","获取打印列表");
    }

    @Override
    public Object getBillInfo(String billName,String billBatchCode,String billNo,String payer,String random,String ivcDateTime){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //1、处理参数
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("billBatchCode",billBatchCode);
        dataMap.put("billNo",billNo);
        dataMap.put("random",random);
        String data = mapToBase64(dataMap);

        paramMap.put("appid",_appid);
        paramMap.put("data",data);
        paramMap.put("noise",uuid);
        paramMap.put("version","1.0");

        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
        paramMap.put("sign", MD5(signOrigin1).toUpperCase());

        //2、调用接口
        Map<String,Object> retMap = callInterface(paramMap,"getBillDetail","获取票据明细");

        if(retMap.containsKey("data")){
            JSONObject dataJson = (JSONObject)(retMap.get("data"));
            String pdffile = PdfUtil.writeFaPiaoPdf(dataJson,_pdf_template_fullname,_pdf_file_directory, billName, billBatchCode, billNo, payer, random, ivcDateTime,_pdf_field_paycompany);
            retMap.put("pdffile",pdffile);
        }
        return retMap;
    }

    @Override
    public Object getValidBillBatchCode(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("placeCode",_placecode);
        String data = mapToBase64(dataMap);

        paramMap.put("appid",_appid);
        paramMap.put("data",data);
        paramMap.put("noise",uuid);
        paramMap.put("version","1.0");

        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
        paramMap.put("sign", MD5(signOrigin1).toUpperCase());

        //2、调用接口
        return callInterface(paramMap,"getValidBillBatchCode","获取纸质票据有效票据代码列表");
    }

    @Override
    public Object getPaperBillNo(String pBillBatchCode){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        //1、处理参数
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("placeCode",_placecode);
        dataMap.put("pBillBatchCode",pBillBatchCode);
        String data = mapToBase64(dataMap);

        paramMap.put("appid",_appid);
        paramMap.put("data",data);
        paramMap.put("noise",uuid);
        paramMap.put("version","1.0");

        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
        paramMap.put("sign", MD5(signOrigin1).toUpperCase());

        return callInterface(paramMap,"getPaperBillNo","获取当前纸质票据可用号码");
    }

    @Override
    public Object turnPaper(String billBatchCode,String billNo,String pBillBatchCode,String pBillNo){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        SimpleDateFormat dd=new SimpleDateFormat("yyyyMMddHHmmssSSS");

        //1、处理参数
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("billBatchCode",billBatchCode);
        dataMap.put("billNo",billNo);
        dataMap.put("pBillBatchCode",pBillBatchCode);
        dataMap.put("pBillNo",pBillNo);
        dataMap.put("busDateTime",dd.format(new Date()));
        dataMap.put("placeCode",_placecode);
        dataMap.put("operator",_machinename);
        String data = mapToBase64(dataMap);

        paramMap.put("appid",_appid);
        paramMap.put("data",data);
        paramMap.put("noise",uuid);
        paramMap.put("version","1.0");

        String signOrigin = "appid=" + _appid +"&data=" + data + "&noise=" + uuid;
        String signOrigin1 = signOrigin + "&key=" + _key + "&version=1.0";
        paramMap.put("sign", MD5(signOrigin1).toUpperCase());

        return callInterface(paramMap,"turnPaper","换开纸质票据");
    }

    @Override
    public Object getValidBillNo(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Map<String,Object> paramMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();

        dataMap.put("placeCode",_placecode);
        String data = mapToBase64(dataMap);

        paramMap.put("appid",_appid);
        paramMap.put("data",data);
        paramMap.put("noise",uuid);
        paramMap.put("version","1.0");

//        String v = "{\n" +
//                "\t\"status\": \"S_OK\",\n" +
//                "\t\"message\": \"\",\n" +
//                "\t\"data\": {\n" +
//                "\"count\": \"1000\",\n" +
//                "\"billNoList\": [{\n" +
//                "\"billCode\": \"40001\",\n" +
//                "\"billBatchCode\": \"40001\",\n" +
//                "\t\t\t\"billBgnNo\": \"00001091\",\n" +
//                "\t\t\t\"billEndNo\": \"00002091\"\n" +
//                "}]\n" +
//                "\t}\n" +
//                "}\n";
//
//        return JSONObject.parse(v);

        return callInterface(paramMap,"getValidBillNo","获取纸质/电子票据有效票据号段接口");
    }

    //#########私有方法#########//
    private Map<String,Object> callInterface(Map<String,Object> paramMap,String callMethod,String mCName) {
        Map<String,Object> retMap = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> pathParam= new HashMap<>();
        String responseText;
        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(paramMap), headers);
        try {
            ResponseEntity<String> rss = restTemplate.exchange(_baseurl + callMethod, HttpMethod.POST, requestEntity, String.class, pathParam);
            responseText = rss.getBody();
        }catch (Exception ex){
            retMap.put("status","S_FALSE");
            retMap.put("message",ex.getMessage());
            return retMap;
        }

        //3、解析返回值
        JSONObject rensponseJson = JSONObject.parseObject(responseText);
        String retString = null;
        try {
            retString = new String(Base64.decodeBase64(rensponseJson.getString("data")),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject retJson = JSONObject.parseObject(retString);
        String result = retJson.getString("result");
        String message = retJson.getString("message");
        String realMessage = null;
        try {
            realMessage = new String(Base64.decodeBase64(message),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (result.equals("S0000")){
            retMap.put("status","S_OK");
            retMap.put("message",mCName + "成功");

            try{
                retMap.put("data",JSONObject.parseObject(realMessage));

            }catch (Exception ex){
                //retMap.put("data",realMessage);
            }


        }else{
            retMap.put("status","S_FALSE");
            retMap.put("message","未查询到待开票记录");
        }

        return retMap;
    }


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
