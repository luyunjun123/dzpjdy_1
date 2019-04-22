package com.wzcsoft.dzpjdy.service;

import org.apache.axis.client.Call;

import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;

//import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 邢直 on 2019/1/9.
 */
@org.springframework.stereotype.Service
public class HisInterfaceServiceImpl implements HisInterfaceService {

    @Value("${hisinterf.operator}")
    private String _operator;

    @Override
    public Object getPatientInfo(String cardno){
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

    @Override
    public Object setTicketinfo(String ebillno,String pbillno,String pbillbatchcode,String pFlag){
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

            } catch (DocumentException e) {
                e.printStackTrace();
                retMap.put("status", "S_FALSE");
                retMap.put("message", "HIS返回信息解析出错！");
            }
        }
        return retMap;
    }


//    私有方法
    private String getResByAxis(String xmlString){
        String endpoint = "http://10.89.1.47:8089/founderWebs/services/ICalculateServiceSCSZL";
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
}
