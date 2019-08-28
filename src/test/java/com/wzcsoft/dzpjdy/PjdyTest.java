package com.wzcsoft.dzpjdy;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wzcsoft.dzpjdy.domain.User;
import com.wzcsoft.dzpjdy.service.DzpjInterfaceService;
import com.wzcsoft.dzpjdy.util.JsonUtil;
import com.wzcsoft.dzpjdy.util.ThreeHospitalsToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lyj
 * @date 2019/7/23 10:19
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PjdyTest {

    @Autowired
    private DzpjInterfaceService dzpjInterfaceService;

    @Test
    public void mytest() throws Exception {

//        //时间蹉
//        Long timeInMillis = Calendar.getInstance().getTimeInMillis();
//        //分装参数
//        HashMap<String, Object> basicmap = new HashMap<>();
//        HashMap<String, Object> bodymap = new HashMap<>();
//        HashMap<String, Object> detailmap = new HashMap<>();
//        //厂家代码
//        basicmap.put("vendorCode", "80001");
//        //医院代码
//        basicmap.put("hospitalCode", "001");
//        //分院代码
//        basicmap.put("branchCode", "01");
//        //医联体代码
//        basicmap.put("medicalCombo", "0");
//        //操作员号
//        basicmap.put("operatorNo", "0001");
//        //终端 ID
//        basicmap.put("terminalId", "123456");
//        //交易代码
//        basicmap.put(" transactionCode", "opt_0001");
//        //版本号
//        basicmap.put(" version", "V1.0.3");
//        //时间戳
//        basicmap.put(" timestamp", timeInMillis);
//        //是否分页
//        basicmap.put(" needPage", true);
//        //当前页数
//        basicmap.put(" pageNo", 1);
//        //每页条数
//        basicmap.put(" pageSize", 10);
//
//        //业务参数
//        detailmap.put("queryType", "1");
//        //todo ????  类型对应数据
//        detailmap.put("queryValue", "1");
//
//        bodymap.put("basic ", basicmap);
//        bodymap.put("detail ", detailmap);
//        bodymap.put("enc ", "F");
//
//
//
//
//        Map<String, Object> pathParam= new HashMap<>();
//        Map<String,Object> paramMap = new HashMap<>();
////        paramMap.put("cardno", "123");
//
//        Map<String,Object> retMap = new HashMap<>();
//        String _baseurl="http://localhost:8095/printlog/Test";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String myId="123";
//        String passwords="asdasddd";
//        headers.setBasicAuth(myId, passwords);
////        headers.setBearerAuth("aidhoaishdaoish");
//        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(bodymap), headers);
//        String body1 = requestEntity.getBody();
//        System.out.println("requestEntity"+requestEntity);
//        HttpHeaders headers1 = requestEntity.getHeaders();
//        String body = requestEntity.getBody();
//        System.out.println("body"+body);
//        System.out.println("headers1:"+headers1);
//
//
//
//        try {
//
////            说明：1）url: 请求地址；
////            2）method: 请求类型(如：POST,PUT,DELETE,GET)；
////            3）requestEntity: 请求实体，封装请求头，请求内容
////            4）responseType: 响应类型，根据服务接口的返回类型决定
////            5）uriVariables: url中参数变量值
////            String url, HttpMethod method,@Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables
//            ResponseEntity<String> rss = restTemplate.exchange(_baseurl , HttpMethod.POST, requestEntity, String.class, pathParam);
//            String str = rss.getBody();
////            HashMap<String, Object> rempmap = JsonUtil.getBody(str);
//
////            System.out.println("返回的map"+rempmap);
//        }catch (Exception ex){
//            retMap.put("status","S_FALSE");
//            retMap.put("message",ex.getMessage());
//        }

    }

    @Test
    public void mytestJSON() throws Exception {
        System.out.println("00000");
//        String str ="\n" +
//                "{\n" +
//                "\"result\": {\n" +
//                "\"content\": [\n" +
//                "{\"patName\":\"1\",\"caseNo\":\"123123\",\"patId\":\"4399\",\"patAge\":\"18\",\"patSex\":\"1\"}\n" +
//                "],\n" +
//                "\"pageNo\": 1,\n" +
//                "\"pageSize\": 10,\n" +
//                "\"total\": 5,\n" +
//                "\"totalPage\": 1\n" +
//                "},\n" +
//                "\"status\": T, \"msgCode\": \"\",\n" +
//                "\"msg\": \"\",\n" +
//                "\"inParamSize\": \"\",\n" +
//                "\"transId\": \"\", \"responseTime\": 500\n" +
//                "}";


        String str = "{\n" +
                "\"result\": {\n" +
                "\"content\": [\n" +
                "{\"patName\":\"1\",\"caseNo\":\"1\",\"patId\":\"312\",\"patAge\":\"123\",\"patBirthDate\":\"1990-12-17\"}\n" +
                "],\n" +
                "\"pageNo\": 1,\n" +
                "\"pageSize\": 10,\n" +
                "\"total\": 5,\n" +
                "\"totalPage\": 1\n" +
                "},\n" +
                "\"status\": T, \"msgCode\": \"\",\n" +
                "\"msg\": \"\",\n" +
                "\"inParamSize\": \"\",\n" +
                "\"transId\": \"\", \"responseTime\": 500\n" +
                "}";


//            HashMap<String, Object> body = JsonUtil.getBody(str);
//            System.out.println("返回"+body);
//            String str="{\"result\":{\"total\":5,\"pageNo\":1,\"totalPage\":1,\"pageSize\":10,\"content\":[{\"patId\":\"4399\",\"patAge\":\"18\",\"patSex\":\"1\",\"patName\":\"1\",\"caseNo\":\"123123\"}]},\"msg\":\"\",\"transId\":\"\",\"responseTime\":500,\"inParamSize\":\"\",\"msgCode\":\"\",\"status\":\"T\"}";
//            org.json.JSONObject jsonObject = new org.json.JSONObject(str);
//            String status = jsonObject.getString("status");
//            System.out.println("status"+status);
//            String result1 = jsonObject.getString("result");
//            System.out.println(result1);
//            org.json.JSONObject result = jsonObject.getJSONObject("result");
//            String content = result.getString("content");
//            System.out.println(content);
//            JSONArray jsonArray = JSON.parseArray(content);
//            int size = jsonArray.size();
//            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//            for (int i = 0; i < size; i++){
//                JSONObject jsonObject_1 = jsonArray.getJSONObject(i);
//                String patName = jsonObject_1.getString("patName");
//                String caseNo = jsonObject_1.getString("caseNo");
//                String patId = jsonObject_1.getString("patId");
//                String patAge = jsonObject_1.getString("patAge");
//                String patSex = jsonObject_1.getString("patSex");
//
//                objectObjectHashMap.put("patName", patName);
//                objectObjectHashMap.put("caseNo", caseNo);
//                objectObjectHashMap.put("patId", patId);
//                objectObjectHashMap.put("patAge", patAge);
//                objectObjectHashMap.put("patSex", patSex);
//            }
//            System.out.println("00000"+objectObjectHashMap);


//            List<User> list = JSONObject.parseObject(content, List.class);
//            HashMap<Object, Object> rempmap = new HashMap<>();
//            for(int i=0;i<list.size();i++) {
//                User user = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)), User.class);
//                System.out.println(user);
//                String  caseNo = user.getCaseNo();
//                String   patBirthDate = user.getPatBirthDate();
//                Byte patSex = user.getPatSex();
//                String  patId = user.getPatId();
//                String  patName = user.getPatName();
//
//                rempmap.put("caseNo", caseNo);
//                rempmap.put("patBirthDate", patBirthDate);
//                rempmap.put("patSex", patSex);
//                rempmap.put("patId", patId);
//                rempmap.put("patName", patName);
//            }
//            System.out.println("00000"+rempmap);
//                String  caseNo = user.getCaseNo();
//                String   patBirthDate = user.getPatBirthDate();
//                Byte patSex = user.getPatSex();
//                String  patId = user.getPatId();
//                String  patName = user.getPatName();
//
//                rempmap.put("caseNo", caseNo);
//                rempmap.put("patBirthDate", patBirthDate);
//                rempmap.put("patSex", patSex);
//                rempmap.put("patId", patId);
//                rempmap.put("caseNo", patBirthDate);
//                rempmap.put("patName", patName);
//
//            System.out.println("00000"+rempmap);

    }

    @Test
    public void register() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");
        String s = map.get("1");
        System.out.println(s);
        // 第一种：普遍使用，二次取值


    }

    @Test
    public void mytestTime() throws Exception {
        Object patientInfo = dzpjInterfaceService.getPatientInfo("1231");
        System.out.println("返回值11111111" + patientInfo);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void mytestT() throws Exception {
//            HashMap<Object, Object> pathParam = new HashMap<>();
//            Map<String, Object> paramMap = new HashMap<>();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(paramMap), headers);
//            String _baseurl="http://localhost:8095/printlog/Test";
//            ResponseEntity<String> rss = restTemplate.exchange( _baseurl, HttpMethod.POST, requestEntity, String.class, pathParam);
//           String  responseText = rss.getBody();
//            JSONObject rensponseJson = JSONObject.parseObject(responseText);
//            String s = new String(rensponseJson.getString("result"));
//            System.out.println(s);
    }

    static int a;

    @Test
    public void mytest312321() throws Exception {
        a = 10;
    }

    @Test
    public void mytest123() throws Exception {
//            String str="周杰伦";
//            String s = new String(str.getBytes("UTF-8"), "UTF-8");
//            System.out.println(s);
//            String s1 = new String(s.getBytes("UTF-8"));
//            System.out.println(s1);
        String old = "&#x5468;&#x6770;&#x4F26; ";//默认环境，已是UTF-8编码
        String gb = new String(old.getBytes("UTF-8"), "UTF-8");
        System.out.println("" + gb);
        String u8 = new String(gb.getBytes("UTF-8"), "UTF-8");
        System.out.println(u8);// 没有乱码
    }

    @Test
    public void mytest32131() throws Exception {
        String str = "[{\"patBirthDate\":\"1960-10-11\",\"nationality\":\"CN\",\"nation\":\"H\",\"patId\":\"898989\",\"patAge\":\"80\",\"patSex\":\"1\",\"patName\":\"张麻子\",\"remark\":\"备注信息\",\"certId\":\"51110203132890001121\",\"caseNo\":\"332211\",\"maritalStatus\":\"已婚\"}]";
        String s = str.replaceAll("[\\[\\]]", "");
        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(jsonObject);
    }

    @Test
    public void mytest99888() throws Exception {
        Map<String, Object> retMap = new HashMap<>();
        Date inputDate = null, endDate, startDate;
        SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            inputDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-10-10");
        } catch (Exception ex) {
            retMap.put("status", "S_FALSE");
            retMap.put("message", "日期格式不正确");
        }
        startDate = new Date(inputDate.getTime() + (-7) * 24 * 60 * 60 * 1000L);
        endDate = new Date(inputDate.getTime() + 24 * 60 * 60 * 1000L - 1);
        String startDateStr = dd.format(startDate);
        String endDateStr = dd.format(endDate);
        System.out.println(startDateStr);
        System.out.println(endDateStr);
    }
    @Test
    public void mytestjson()throws Exception{
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("a", "1");
        objectObjectHashMap.put("b", "2");
        objectObjectHashMap.put("c", "3");
        String a = (String)objectObjectHashMap.get("a");
        System.out.println(a);
    }
        @Test
        public void mytest412()throws Exception{
//            String str="&#x6210;&#x90FD;&#x5E02;&#x7B2C;&#x4E09;&#x4EBA;&#x6C11;&#x533B;&#x9662;";
//            String decode = URLDecoder.decode(str, "utf-8");
//            System.out.println(decode);

            String str="";
            String decodeStr = URLDecoder.decode(str, "utf-8");
            System.out.println("解码:" + decodeStr);
        }
}
