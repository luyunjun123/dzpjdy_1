package com.wzcsoft.dzpjdy.service;

import com.wzcsoft.dzpjdy.domain.Bill;
import com.wzcsoft.dzpjdy.domain.Result;
import com.wzcsoft.dzpjdy.util.ReadUtil;
import com.wzcsoft.dzpjdy.util.XmlUtil;
import org.apache.axis.client.Call;

import org.apache.axis.client.Service;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;

//import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
    //his数据回传端口
    @Value("${self.ipaddress_1}")
    private String _selfipaddress_1;
    @Value("${self.transno_1}")
    private String _selftransno_1;


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


    //肿瘤医院数据返回
    @Override
    public Object setTicketinfo(String ebillno,String pbillno,String pbillbatchcode,String pFlag){
        if(_hisintcode.equals("1")){
            return setTicketinfo_1(ebillno, pbillno, pbillbatchcode, pFlag);
        }else {
            return null;
        }
    }
    //省医院数据返回,String billNo  ,String chargetime
    @Override
    public Object setpainfobycard(String patientid,String billno) {
        System.out.println("进入his数据回传方法");
        Map<String,Object> retMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //范围为60天
        c.add(Calendar.DATE, - 60);
        Date time = c.getTime();
        String endDate = sdf.format(time);
        System.out.println(endDate);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        String beginDate = dateFormat.format(date);
        System.out.println(beginDate);
        try {
            Socket client = new Socket(_serverip, Integer.parseInt(_port));
            OutputStream outputStream = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outputStream);
            //根据病人id获取回传开票信息
            String sendStr = "<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
                    "<RequestOutInvoice>" +
                    "<TransCode>051MZFP</TransCode>" +
                    " <CardNo>" + patientid + "</CardNo>" +
                    " <BeginDate>" + endDate+ "</BeginDate>" +
                    " <EndDate>" +beginDate + "</EndDate>" +
                    " </RequestOutInvoice>";

            byte[] buff = sendStr.getBytes("GB2312");
            int len = buff.length;
            sendStr = String.format("%08d", len) + sendStr;
//            System.out.println("请求his数据发送报文：" + sendStr);
            //发送数据
            out.write(sendStr.getBytes("GB2312"));
//            取数据
            InputStream inFromServer = client.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inFromServer, "GB2312");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
            char[] lenbuff = new char[8];
            bufferedReader.read(lenbuff,0,8);
            String lenStr = new String(lenbuff);
            int resplen = Integer.parseInt(lenStr);
            lenbuff = new char[resplen];
            bufferedReader.read(lenbuff,0,resplen);
            //是取出前后空格
            String responseXml = new String(lenbuff).trim();
//            System.out.println("his回传数据"+responseXml);

            //测试数据
//            String responseXml="<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
//                        "<ReponseOutInvoice>" +
//                        "<Result>1</Result>" +
//                        "<Err>成功</Err>" +
//                        "<SumRows>5</SumRows> " +
//                        "<OutInvoice>" +
//                        "<InvoiceNO>01190002111278</InvoiceNO>" +
//                        "<InvoiceSeq>200305191</InvoiceSeq>" +
//                        "<PrintNo>1/1</PrintNo>" +
//                        "<Oper>YY1017</Oper>" +
//                        "<Name>何攀</Name>" +
//                        "<InvoiceName>四川省医疗卫生单位门诊结算票据（电子）</InvoiceName>" +
//                        "<EBillNo>0002111278</EBillNo>" +
//                        "<EBillBatchCode>51060119</EBillBatchCode>" +
//                        "<ERandom>cb3e03</ERandom>" +
//                        "<ECreateTime>20190717</ECreateTime>" +
//                        "<TotalCost>￥261.10</TotalCost>" +
//                        "<UpTotCost>贰佰陆拾壹元壹角整</UpTotCost>" +
//                        "<ItemTypeTotal>" +
//                        "<ItemTypeName>西药费</ItemTypeName>" +
//                        "<ItemTypeCost>261.10</ItemTypeCost>" +
//                        "<ItemTypeCode>01</ItemTypeCode>" +
//                        "</ItemTypeTotal>" +
//                        "<FeeItem>" +
//                        "<RecipeNO>60586384</RecipeNO>" +
//                        "<SeqNO>1</SeqNO>" +
//                        "<ItemName>▲阿魏酸哌嗪片|宝盛康(亨达盛康)(基)(川)</ItemName>" +
//                        "<Qty>2.00</Qty>" +
//                        "<Unit>瓶</Unit>" +
//                        "<Specs>50mg*180片/瓶</Specs>" +
//                        "<Cost>85.72</Cost>" +
//                        "<Price>42.86</Price> " +
//                        "</FeeItem> " +
//                        "<FeeItem> " +
//                        "<RecipeNO>60586384</RecipeNO> " +
//                        "<SeqNO>2</SeqNO>" +
//                        " <ItemName>▲培哚普利叔丁胺片|雅施达(基)</ItemName>" +
//                        " <Qty>2.00</Qty>" +
//                        " <Unit>盒</Unit>" +
//                        " <Specs>8mg*15片/盒</Specs>" +
//                        " <Cost>175.38</Cost>" +
//                        " <Price>87.69</Price>" +
//                        " </FeeItem> " +
//                    "</OutInvoice>" +
//                    "<OutInvoice>" +
//                    "<InvoiceNO>01190002111278</InvoiceNO>" +
//                    "<InvoiceSeq>20030519113213</InvoiceSeq>" +
//                    "<PrintNo>1/1</PrintNo>" +
//                    "<Oper>YY1017</Oper>" +
//                    "<Name>何攀</Name>" +
//                    "<InvoiceName>四川省医疗卫生单位门诊结算票据（电子）</InvoiceName>" +
//                    "<EBillNo>0002111278111</EBillNo>" +
//                    "<EBillBatchCode>51060119</EBillBatchCode>" +
//                    "<ERandom>cb3e03</ERandom>" +
//                    "<ECreateTime>20190717</ECreateTime>" +
//                    "<TotalCost>￥261.10</TotalCost>" +
//                    "<UpTotCost>贰佰陆拾壹元壹角整</UpTotCost>" +
//                    "<ItemTypeTotal>" +
//                    "<ItemTypeName>西药费</ItemTypeName>" +
//                    "<ItemTypeCost>261.10</ItemTypeCost>" +
//                    "<ItemTypeCode>01</ItemTypeCode>" +
//                    "</ItemTypeTotal>" +
//                    "<FeeItem>" +
//                    "<RecipeNO>60586384</RecipeNO>" +
//                    "<SeqNO>1</SeqNO>" +
//                    "<ItemName>▲阿魏酸哌嗪片|宝盛康(亨达盛康)(基)(川)</ItemName>" +
//                    "<Qty>2.00</Qty>" +
//                    "<Unit>瓶</Unit>" +
//                    "<Specs>50mg*180片/瓶</Specs>" +
//                    "<Cost>85.72</Cost>" +
//                    "<Price>42.86</Price> " +
//                    "</FeeItem> " +
//                    "<FeeItem> " +
//                    "<RecipeNO>60586384</RecipeNO> " +
//                    "<SeqNO>2</SeqNO>" +
//                    " <ItemName>▲培哚普利叔丁胺片|雅施达(基)</ItemName>" +
//                    " <Qty>2.00</Qty>" +
//                    " <Unit>盒</Unit>" +
//                    " <Specs>8mg*15片/盒</Specs>" +
//                    " <Cost>175.38</Cost>" +
//                    " <Price>87.69</Price>" +
//                    " </FeeItem> " +
//                    "</OutInvoice>";

            try {
                String substring = responseXml.substring(responseXml.indexOf("<OutInvoice>"), responseXml.lastIndexOf("</OutInvoice>") + "</OutInvoice>".length());
                String format = String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Root><Data>%s</Data></Root>", substring);
                Result r = XmlUtil.convertXmlStrToObject(Result.class,format);

                Bill bill = r.getBills()
                        .stream()
                        .filter(i -> i.geteBillNo().equals(billno))
                        .findFirst()
                        .orElse(null);
                String invoiceSeq = bill.getInvoiceSeq();
                System.out.println("获得对比号码"+invoiceSeq);

                //打印成功发返回数据
                getMZFPDYInfo_1(invoiceSeq,patientid);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("解析xml异常，返回数据非正常格式");
                retMap.put("status", "S_FALSE");
                retMap.put("message", "解析错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("his信息返回异常");
            retMap.put("status", "S_FALSE");
            retMap.put("message", "HIS返回信息解析出错！");
        }
            return retMap;
    }

    private Object getMZFPDYInfo_1(String invoiceSeq,String patientid){
        Map<String,Object> retMap = new HashMap<>();
        System.out.println("进入返回方法");
        try {
            Socket client = new Socket(_serverip, Integer.parseInt(_port));
            OutputStream outputStream = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outputStream);
            //返回his开票数据
            String sendStr ="<?xml version=\"1.0\" encoding=\"utf-16\"?>" +
                    "<RequestOutInvoicePrint><TransCode>052MZFPDY</TransCode>" +
                    "<InvoiceSeq>"+invoiceSeq+"</InvoiceSeq>" +
                    "<SelfMachine>" +
                    "<SelfIP>"+_selfipaddress+"</SelfIP>" +
                    "<SelfTransNo>"+_selftransno+"</SelfTransNo>" +
                    "</SelfMachine>" +
                    "<Reserved1>" +
                    "</Reserved1>" +
                    "<Reserved2>" +
                    "</Reserved2>" +
                    "<Reserved3>" +
                    "</Reserved3>" +
                    "<Branch>A</Branch>" +
                    "<Producer>HW</Producer>" +
                    "</RequestOutInvoicePrint>";
            byte[] buff = sendStr.getBytes("GB2312");
            int len = buff.length;
            sendStr = String.format("%08d", len) + sendStr;
            System.out.println("请求his数据发送报文：" + sendStr);
            //发送数据
            out.write(sendStr.getBytes("GB2312"));



            //取出返回信
           InputStream inFromServer = client.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inFromServer, "GB2312");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
            char[] lenbuff = new char[8];
            bufferedReader.read(lenbuff,0,8);
            String lenStr = new String(lenbuff);
            int resplen = Integer.parseInt(lenStr);
            lenbuff = new char[resplen];
            bufferedReader.read(lenbuff,0,resplen);
            //是取出前后空格
            String responseXml = new String(lenbuff).trim();
            System.out.println("his回传数据"+responseXml);
            //写入文件保存
            if(responseXml==null){
                responseXml="发生异常无状态";
            }
            ReadUtil.WriteStringToFile(invoiceSeq,patientid,responseXml);
            retMap.put("status", "S_OK");
            retMap.put("message", "返回数据成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("返回his异常无查询数据");
            retMap.put("status", "S_FALSE");
            retMap.put("message", "返回his异常！");
        }


        return retMap;
    };

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

       /* Map<String,Object> retMap = new HashMap<>();
        Map<String,Object> dataMap = new HashMap<>();
        Document doc = null;

        //测试数据
        dataMap.put("name", "王招财");
        dataMap.put("cardno", cardno);
        dataMap.put("sex", "男" );
        dataMap.put("age", 1);
        dataMap.put("socialno", "6221197904110012");
        retMap.put("status", "S_OK");
        retMap.put("message", "");
        retMap.put("data", dataMap);

        return retMap;*/

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
            System.out.println("his请求基本发送报文：" + sendStr);
            out.write(sendStr.getBytes("GB2312"));
            //获得 输入流
            InputStream inFromServer = client.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inFromServer, "GB2312");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区

            char[] lenbuff = new char[8];
            bufferedReader.read(lenbuff,0,8);
            String lenStr = new String(lenbuff);
            int resplen = Integer.parseInt(lenStr);
            lenbuff = new char[resplen];
            bufferedReader.read(lenbuff,0,resplen);
            //是取出前后空格
            String responseXml = new String(lenbuff).trim();
            System.out.println("his返回获取基本信息:"+responseXml);
        //    IOUtils.copy(inFromServer, new FileOutputStream("C:\\myfile\\myfile.txt"));
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
                 //   client.close();
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
