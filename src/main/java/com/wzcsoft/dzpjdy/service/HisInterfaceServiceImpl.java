package com.wzcsoft.dzpjdy.service;

import org.apache.axis.client.Call;

import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;

//import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 邢直 on 2019/1/9.
 */
@org.springframework.stereotype.Service
public class HisInterfaceServiceImpl implements HisInterfaceService {

    @Value("${hisinterf.operator}")
    private String _operator;

    @Value("${hisinterf.code}")
    private String _hisintcode;

    @Value("${hisinterf.serverip}")
    private String _serverip;

    @Value("${hisinterf.port}")
    private String _port;

    @Value("${self.transno}")
    private String _selftransno;

    @Value("${self.ipaddress}")
    private String _selfipaddress;



    @Override
    public Object getPatientInfo(String cardno){
        if(_hisintcode.equals("1")){
            return getPatientInfo_1(cardno);
        }else if (_hisintcode.equals("2")){
            return getPatientInfo_2(cardno);
        }else {
            Map<String,Object> retMap = new HashMap<>();
            retMap.put("status", "S_FALSE");
            retMap.put("message", "不支持的His代码！");
            return retMap;
        }
    }

    @Override
    public Object setTicketinfo(String ebillno,String pbillno,String pbillbatchcode,String pFlag){
        if(_hisintcode.equals("1")){
            return setTicketinfo_1(ebillno, pbillno, pbillbatchcode, pFlag);
        }else {
            return null;
        }
    }


    private Object getPatientInfo_1(String cardno){
        Map<String,Object> retMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        Document doc = null;

        //测试数据
//        dataMap.put("name", "王招财");
//        dataMap.put("cardno", cardno);
//        dataMap.put("sex", "男" );
//        dataMap.put("age", 1);
//        dataMap.put("socialno", "6221197904110012");
//        retMap.put("status", "S_OK");
//        retMap.put("message", "");
//        retMap.put("data", dataMap);

//        return retMap;
        //测试数据结束

        //xmlString的格式
        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<funderService serverName='invoice_bhc_cardcheck'><value><![CDATA[<Request><CardNo>" + cardno + "</CardNo></Request>]]></value></funderService>");

        //获取返回值
        String responseXml = getResByAxis(xmlString.toString());

        //解析Xml
        if(responseXml.equals(null)){
            retMap.put("status", "S_FALSE");
            retMap.put("message", "调用HIS接口出错！");
        }else {
            try {
                doc = DocumentHelper.parseText(responseXml);
                Element rootElt = doc.getRootElement();

                String resultCode = rootElt.elementTextTrim("ResultCode");
                String errorMsg = rootElt.elementTextTrim("ErrorMsg");

                if (resultCode.equals("0")){
                    Iterator items = rootElt.elementIterator("items");
                    Element patientInfo = (Element) items.next();

                    String patientid = patientInfo.elementTextTrim("PATIENT_ID");
                    String name = patientInfo.elementTextTrim("NAME");
                    String sexcode = patientInfo.elementTextTrim("SEX");
                    String age = patientInfo.elementTextTrim("AGE");
                    String socialno = patientInfo.elementTextTrim("SOCIAL_NO");
                    String sex;
                    if(sexcode.equals("1")){
                        sex = "男";
                    }else{
                        sex = "女";
                    }
                    dataMap.put("patientid",patientid);
                    dataMap.put("name", name);
                    dataMap.put("cardno", cardno);
                    dataMap.put("sex", sex );
                    dataMap.put("age", age);
                    dataMap.put("socialno", socialno);

                    retMap.put("status", "S_OK");
                    retMap.put("message", "");
                    retMap.put("data", dataMap);
                }else{
                    retMap.put("status", "S_FALSE");
                    retMap.put("message", errorMsg);
                }

            } catch (DocumentException e) {
                e.printStackTrace();
                retMap.put("status", "S_FALSE");
                retMap.put("message", "HIS返回信息解析出错！");
            }
        }
        return retMap;
    }

    private Object getPatientInfo_2(String cardno){
        Map<String,Object> retMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        Document doc = null;
        SimpleDateFormat dd=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Socket client = new Socket(_serverip, Integer.parseInt(_port));
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            String sendStr ="<?xml version=\"1.0\" encoding=\"utf-16\"?><RequestMarkInfo><TransCode>001DQKP</TransCode><MarkType>1</MarkType><MarkNO>" + cardno + "</MarkNO><BankTransNO></BankTransNO><SelfMachine><SelfIP>" + _selfipaddress + "</SelfIP><SelfTransNo>" + _selftransno + "</SelfTransNo></SelfMachine></RequestMarkInfo>";
            byte[] buff = sendStr.getBytes("GB2312");
            int len = buff.length;
            sendStr = String.format("%08d", len) + sendStr;
            //System.out.println("发送报文：" + sendStr);
            out.write(sendStr.getBytes("GB2312"));
            InputStream inFromServer = client.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inFromServer, "GB2312");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区

            char[] lenbuff = new char[8];
            bufferedReader.read(lenbuff,0,8);
            String lenStr = new String(lenbuff);
            int resplen = Integer.parseInt(lenStr);
            lenbuff = new char[resplen];
            bufferedReader.read(lenbuff,0,resplen);
            String responseXml = new String(lenbuff).trim();

            //解析
            //System.out.println("服务器响应： " + responseXml);
            try {
                doc = DocumentHelper.parseText(responseXml);
                Element rootElt = doc.getRootElement();

                String resultCode = rootElt.elementTextTrim("Result");
                String errorMsg = rootElt.elementTextTrim("Err");
                String status = rootElt.elementTextTrim("State");


                if (resultCode.equals("1") && status.equals("1")){
                    Iterator items = rootElt.elementIterator("PatientInfo");
                    Element patientInfo = (Element) items.next();

                    String patientid = patientInfo.elementTextTrim("CardNO");
                    String name = patientInfo.elementTextTrim("Name");
                    String sexcode = patientInfo.elementTextTrim("Sex");
                    String BirthDay = patientInfo.elementTextTrim("BirthDay");
                    String socialno = patientInfo.elementTextTrim("IDNO");
                    String sex;
                    int age = 0;
                    if (!BirthDay.equals("")){
                        age = getAgeByBirth(dd.parse(BirthDay));
                    }

                    if(sexcode.equals("F")){
                        sex = "女";
                    }else{
                        sex = "男";
                    }
                    dataMap.put("patientid",patientid);
                    dataMap.put("name", name);
                    dataMap.put("cardno", cardno);
                    dataMap.put("sex", sex );
                    dataMap.put("age", age);
                    dataMap.put("socialno", socialno);

                    retMap.put("status", "S_OK");
                    retMap.put("message", "");
                    retMap.put("data", dataMap);
                }else{
                    if (resultCode.equals("1") && !status.equals("1")){
                        errorMsg = status.equals("0")?"就诊卡无效！":"就诊卡已注销";
                    }
                    retMap.put("status", "S_FALSE");
                    retMap.put("message", errorMsg);
                }

            } catch (DocumentException e) {
                e.printStackTrace();
                retMap.put("status", "S_FALSE");
                retMap.put("message", "HIS返回信息解析出错！");
            }

        }catch (Exception e){
            retMap.put("status", "S_FALSE");
            retMap.put("message", e.getMessage());
        }
        return retMap;
    }

    private Object setTicketinfo_1(String ebillno,String pbillno,String pbillbatchcode,String pFlag){
        Map<String,Object> retMap = new HashMap<>();
        Document doc = null;

        StringBuilder xmlString = new StringBuilder();
        xmlString.append("<funderService serverName='zz_switch_invoices'><value><![CDATA[<Request><ebillno>" + ebillno + "</ebillno><ebillbatchcode>" + pbillbatchcode + "</ebillbatchcode><turn_p_opera>" + _operator + "</turn_p_opera><pbillno>" + pbillno + "</pbillno><flag>" + pFlag + "</flag></Request>]]></value></funderService>");

        //获取返回值
        String responseXml = getResByAxis(xmlString.toString());

        //解析Xml
        if(responseXml.equals(null)){
            retMap.put("status", "S_FALSE");
            retMap.put("message", "调用HIS接口出错！");
        }else {
            try {
                doc = DocumentHelper.parseText(responseXml);
                Element rootElt = doc.getRootElement();

                String resultCode = rootElt.elementTextTrim("ResultCode");
                String errorMsg = rootElt.elementTextTrim("ErrorMsg");

                if (resultCode.equals("0")){
                    retMap.put("status", "S_OK");
                    retMap.put("message", "");
                }else{
                    retMap.put("status", "S_FALSE");
                    retMap.put("message", errorMsg);
                }

            } catch (Exception e) {
                e.printStackTrace();
                retMap.put("status", "S_FALSE");
                retMap.put("message", "HIS返回信息解析出错！");
            }
        }
        return retMap;
    }



    private String getResByAxis(String xmlString){
        String endpoint = "http://" + _serverip + ":" + _port + "/founderWebs/services/ICalculateServiceSCSZL";
        org.apache.axis.client.Service service = new Service();

        try {
            org.apache.axis.client.Call call = (Call)service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            call.setOperationName("funInterFace");
            String ret = (String) call.invoke(new Object[] {xmlString});
            return ret;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

     private int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }
}
