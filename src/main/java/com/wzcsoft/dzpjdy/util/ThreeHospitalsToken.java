package com.wzcsoft.dzpjdy.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzcsoft.dzpjdy.domain.TokenResultVo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获得token工具类
 * @author lyj
 * @date 2019/7/24 14:47
 */
public class ThreeHospitalsToken {


    //三医院获取token
    public static String  getThreeHospitalsToken( String _baseurl,String usernamae,String passwords) {
        //传递的值
       // Map<String, Object> paramMap = new HashMap<>();
       // Map<String, Object> retMap = new HashMap<>();
        // http://localhost:18140/auth/oauth/token?scope=server&grant_type=client_credentials
       // RestTemplate restTemplate = new RestTemplate();
      //  HttpHeaders headers = new HttpHeaders();

//        headers.setContentType(MediaType.APPLICATION_JSON);
       /// MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
       // headers.setContentType(type);
//        String username = new String(Base64.encodeBase64(usernamae.getBytes()));
//        String password = new String(Base64.encodeBase64(passwords.getBytes()));
//        headers.setBasicAuth(username,password);
        //headers.add("Authorization", "Basic eWlodWFuOnlpaHVhbg==");

//        HttpEntity<String> requestEntity = new HttpEntity<String>(paramMap.toString(), headers);
//        HttpHeaders headers1 = requestEntity.getHeaders();
//        System.out.println("headers1"+headers1);
        //URL传参
//        HashMap<Object, Object> pathParam = new HashMap<>();

        try {
//            ResponseEntity<String> rss = restTemplate.exchange(_baseurl , HttpMethod.POST, requestEntity, String.class, pathParam);
//            String str = rss.getBody();
//            String accessToken = JsonUtil.getAccessToken(str);
//            System.out.println("获取到的token"+accessToken);
//            return accessToken;
            RestTemplate template = new RestTemplate();
            //http://10.100.3.113:18140/auth/oauth/token?scope=server&grant_type=client_credentials
            String url = "http://10.100.3.113:18140/auth/oauth/token?scope=server&grant_type=client_credentials";
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(type);
            headers.add("Authorization", "Basic eWlodWFuOnlpaHVhbg==");
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            HttpEntity<String> request = new HttpEntity<String>(map.toString(), headers);
            ResponseEntity<TokenResultVo> response = template.postForEntity(url, request, TokenResultVo.class);
            String token = response.getBody().getAccess_token();
            System.out.println("获取到的token:" + token);
            return token;
        } catch (Exception ex) {
            System.out.println("获取token异常:"+ex.getMessage());

            return null;
        }
    };

}
