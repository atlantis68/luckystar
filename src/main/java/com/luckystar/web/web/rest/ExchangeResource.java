package com.luckystar.web.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.domain.UserInfo;
import com.luckystar.web.repository.UserInfoRepository;
import com.luckystar.web.security.SecurityUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@RestController
@RequestMapping("/api")
public class ExchangeResource {

    private final Logger log = LoggerFactory.getLogger(ExchangeResource.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

	private static OkHttpClient okHttpClient;

	private static String startKey;

	private static String endKey;

	static {
		okHttpClient = new OkHttpClient();
		startKey = "id=\"num_bean\">";
		endKey = "</em>";
	}

    @GetMapping("/get-remaining-bean")
    @Timed
    public ResponseEntity<Double> getRemainingBean(Long id) {
    	log.info("{} request {}, id = {}", SecurityUtils.getCurrentUserLogin(), "getRemainingBean", id);
    	Double remainingBean = null;
    	try {
    		UserInfo userInfo = userInfoRepository.getUserInfoById(id);
            if(userInfo != null) {
    			Request request = new Request.Builder()
	    		    .url("http://fanxing.kugou.com/index.php?action=userExchargeList")
	    		    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
	    		    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
	    		    .addHeader("Cache-Control", "max-age=0")
	    		    .addHeader("Connection", "keep-alive")
	    		    .addHeader("Host", "fanxing.kugou.com")
	    		    .addHeader("Upgrade-Insecure-Requests", "1")
	    		    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
	    		    .addHeader("Cookie", userInfo.getCookie())
	    		    .build();
    			Response response = sendHttp(request);
    			if(response != null && response.isSuccessful()) {
//    				byte[] responseBytes=response.body().bytes();
//    				String result = new String(responseBytes,"UTF-8");
    				String result = response.body().string();
    				int start = result.indexOf(startKey);
    				if(start > -1) {
    					int end = result.indexOf(endKey, start);
    					if(end > -1) {
    						remainingBean = Double.parseDouble(result.substring(start + startKey.length(), end));
    					}
    				}
    			}
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        return new ResponseEntity<Double>(remainingBean != null ? remainingBean : 0d, HttpStatus.OK);
    }

    @GetMapping("/exchange-remaining-bean")
    @Timed
    public ResponseEntity<JSON> exchangeRemainingBean(Long id, Long bean) {
    	log.info("{} request {}, id = {}, bean = {}", SecurityUtils.getCurrentUserLogin(), "exchangeRemainingBean", id, bean);
    	JSONObject result = null;
    	try {
    		UserInfo userInfo = userInfoRepository.getUserInfoById(id);
            if(userInfo != null) {
            	long lastTime = System.currentTimeMillis();
    			Request request = new Request.Builder()
	    		    .url("http://fanxing.kugou.com/UServices/Settlement/SettlementService/apply?args=[" + bean + "]&_=" + lastTime)
	    		    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
	    		    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
	    		    .addHeader("Cache-Control", "max-age=0")
	    		    .addHeader("Connection", "keep-alive")
	    		    .addHeader("Host", "fanxing.kugou.com")
	    		    .addHeader("Upgrade-Insecure-Requests", "1")
	    		    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
	    		    .addHeader("Cookie", userInfo.getCookie())
	    		    .build();
    			Response response = sendHttp(request);
    			if(response != null && response.isSuccessful()) {
    				result = JSON.parseObject(response.body().string());
    				if(result != null) {
    					result.remove("servertime");
    					result.remove("callback");
    					result.remove("data");
    					result.remove("errorno");
    				}

    			}
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        return new ResponseEntity<JSON>(result, HttpStatus.OK);
    }

	private Response sendHttp(Request request) throws Exception {
		return okHttpClient.newCall(request).execute();
	}
}
