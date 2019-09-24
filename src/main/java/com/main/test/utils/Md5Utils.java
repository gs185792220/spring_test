package com.main.test.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

public class Md5Utils {

    /**
     * md5 签名逻辑
     * @param map
     * @param signKey
     * @return
     */
    public static String sign(Map<String, Object> map, String signKey){
        if(map==null){
            return null;
        }
        List<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<keyList.size();i++){
            String key = keyList.get(i);
            Object value = map.get(key);
            sb.append(key+"="+value+"&");
        }
        String signStr = sb.substring(0, sb.length()-1)+signKey;
        System.out.println("before sign: "+signStr);
        String md5Str = DigestUtils.md5Hex(signStr);
        System.out.println("after sign: "+md5Str);
        return md5Str;
    }

    /**
     * 验证md5 签名
     * @param appSecret
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean verifySign(String appSecret, HttpServletRequest request) throws Exception  {
        Map<String, Object> params = new HashMap<>();
        String signStr = request.getParameter("sign");
        if(StringUtils.isBlank(signStr)){
            throw new RuntimeException("There is no signature field in the request parameter!");
        }

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign")) {
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }

        if (!sign( params ,appSecret).equals(signStr)) {
            return false;
        }
        return true;
    }
}
