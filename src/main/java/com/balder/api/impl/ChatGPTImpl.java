package com.balder.api.impl;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.balder.api.controller.ChatGPTController;
import com.balder.api.dto.request.ChatGPTRequest;
import com.balder.api.service.ChatGPTService;
import com.balder.api.util.Utilities;
import com.google.gson.Gson;

@Service("ChatGPTImpl")
public class ChatGPTImpl implements ChatGPTService {
	private static Logger log = Logger.getLogger(ChatGPTImpl.class);
    @Value("${url.chatgpt.message.v1}")
    private String URL_CHATGPT_MESSAGE_V1;
    @Autowired
    Utilities utilities;

	@Override
	public ResponseEntity<?> ProcessMessage(ChatGPTRequest request) {
		log.info("############################################################");
		log.info("##############____processMessageChatgpt____#################");
		log.info("############################################################");
		log.info("=>Request-processMessageChatgpt: " + new Gson().toJson(request));
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String Allmessage = request.getPrompt() + " \n" + request.getMessage();
			String url = URL_CHATGPT_MESSAGE_V1;
			String token = "Bearer " + request.getToken();
			
			java.net.HttpURLConnection con = (java.net.HttpURLConnection) new java.net.URL(url).openConnection();
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-Type", "application/json");
	        con.setRequestProperty("Authorization", token);
	        org.json.JSONObject message = new org.json.JSONObject();
	        message.put("role", "user");
	        message.put("content", Allmessage);
	        List<org.json.JSONObject> obj = new ArrayList<org.json.JSONObject>();
	        obj.add(message);
	        
	        org.json.JSONObject data = new org.json.JSONObject();
	        data.put("model", "gpt-3.5-turbo");
	        data.put("messages", obj);
	        con.setDoOutput(true);
	        con.getOutputStream().write(data.toString().getBytes());

	        String output = new BufferedReader(new java.io.InputStreamReader(con.getInputStream())).lines()
	                .reduce((a, b) -> a + b).get();
	        String MessageChatGPT = new org.json.JSONObject(output).getJSONArray("choices").getJSONObject(0)
	        		.getJSONObject("message").getString("content");
	        
			map.put("code", 200);
			map.put("message", "OK");
			map.put("result", MessageChatGPT);
			ResponseEntity<?> res = utilities.getResponseEntity(map);
			return res;
		} catch (Exception ex) {
			map.put("code", 500);
			map.put("message", "ERROR");
			map.put("result", ex.getMessage());
			ResponseEntity<?> res = utilities.getResponseEntity(map);
			return res;
		}
	}

}
