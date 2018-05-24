package com.luckystar.web.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codahale.metrics.annotation.Timed;
import com.luckystar.web.repository.UserInfoRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@RestController
@RequestMapping("/streaming")
public class StreamingResource {

    private final Logger log = LoggerFactory.getLogger(StreamingResource.class);

    @Autowired
    private UserInfoRepository userInfoRepository;


//    @PostMapping("/get-url")
//    @Timed
//    public ResponseEntity<String> getURL(Long id) {
//    	String streamingUrl = "";
//        String roomId = userInfoRepository.getRoomIdById(id);
//        if(roomId != null && roomId.length() > 0) {
//        	StringBuffer sb = new StringBuffer();
//        	sb.append("http://service.fanxing.kugou.com/video/fx/live/getstreamaddr/instant/")
//        		.append(roomId).append(".jsonp?roomId=").append(roomId)
//        		.append("&pid=207&version=1000&liveType=1&streamType=3&channel=fx&ua=fx-mobile-h5&kugouId=0&jsonpcallback=jsonp_")
//        		.append(System.currentTimeMillis()).append("_").append((int)Math.floor(10000 + Math.random() * 90000));
//        	Request request = new Request.Builder()
//			    .url(sb.toString())
//			    .addHeader("Accept", "*/*")
//			    .addHeader("Accept-Encoding", "gzip, deflate, sdch")
//			    .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
//			    .addHeader("Cache-Control", "no-cache")
//			    .addHeader("Connection", "keep-alive")
//			    .addHeader("Host", "service.fanxing.kugou.com")
//			    .addHeader("Pragma", "no-cache")
//			    .addHeader("Referer", "http://mfanxing.kugou.com/staticPub/rmobile/sharePage/normalRoom/views/index.html?roomId=" + roomId)
//			    .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
//			    .build();
//        	Response response = null;
//			try {
//				response = sendHttp(request);
//				if(response != null && response.isSuccessful()) {
//					String result = response.body().string();
//					int start = result.indexOf("{");
//					int end = result.lastIndexOf("}");
//					if(start > 0 && end > 0 && end + 1 > start) {
//						result = result.substring(start, end + 1);
//						JSONObject resultJson = JSON.parseObject(result);
//						String errorno = resultJson.get("responseCode").toString();
//						if(errorno.equals("0")) {
//							JSONObject info = JSON.parseObject(resultJson.get("data").toString());
//							JSONArray urls = JSON.parseArray(info.get("hls").toString());
//							for(int i = 0; i < urls.size(); i++) {
//								String url = urls.getString(i);
//								//策略1
////								if(url.indexOf("playlist") > -1 && url.indexOf("?") == -1) {
////									streamingUrl = url;
////									break;
////								}
////								if(url.indexOf("hls") > -1 && url.indexOf("?") > -1) {
////									streamingUrl = url;
////									String streamingIp = userInfoRepository.getStreamingIpById(id);
////									if(streamingIp != null && streamingIp.length() > 0) {
////										start = streamingUrl.indexOf("//");
////										if(start > 0) {
////											end = streamingUrl.indexOf(":", start);
//////											if(end == -1) {
//////												end = streamingUrl.indexOf("/", start + 2);
//////											}
////										}
////										if(start > 0 && end > 0 && end > start) {
////											log.info("before streamingUrl = {}", streamingUrl);
////											String orgIp = streamingUrl.substring(start + 2, end);
////											streamingUrl = streamingUrl.replace(orgIp, streamingIp);
////											log.info("after streamingUrl = {}", streamingUrl);
////										}
////									}
////									break;
////								}
//								//策略2
//								if(url != null && url.indexOf("?") > -1) {
//									streamingUrl = url;
//									String streamingIp = userInfoRepository.getStreamingIpById(id);
//									if(streamingIp != null && streamingIp.length() > 0) {
//										start = streamingUrl.indexOf("//");
//										if(start > 0) {
//											end = streamingUrl.indexOf(":", start);
////											if(end == -1) {
////												end = streamingUrl.indexOf("/", start + 2);
////											}
//										}
//										if(start > 0 && end > 0 && end > start) {
//											log.info("before streamingUrl = {}", streamingUrl);
//											String orgIp = streamingUrl.substring(start + 2, end);
//											streamingUrl = streamingUrl.replace(orgIp, streamingIp);
//											log.info("after streamingUrl = {}", streamingUrl);
//										}
//									}
//									break;
//								}
//							}
//						}
//					}
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        return new ResponseEntity<String>(streamingUrl, HttpStatus.OK);
//    }

//	private Response sendHttp(Request request) throws Exception {
//		return okHttpClient.newCall(request).execute();
//	}

    @GetMapping("/roomId")
    @Timed
    public ResponseEntity<String> roomId(Long id) {
        String roomId = userInfoRepository.getRoomIdById(id);
        return new ResponseEntity<String>(roomId, HttpStatus.OK);
    }
}
