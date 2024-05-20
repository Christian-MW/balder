package com.balder.api.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balder.api.dto.request.*;
import com.balder.api.dto.response.*;
import com.balder.api.service.ChatGPTService;

@RestController
@RequestMapping(value="/API/AI/ChatGPT")
@CrossOrigin(origins = "*")
public class ChatGPTController {
	private static Logger log = Logger.getLogger(ChatGPTController.class);
	@Autowired
	ChatGPTService chatGPTService;
	
	@PostMapping(value="/ProcessMessage", 
	consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin(origins = "*")
    public ResponseEntity<?> test(@RequestBody ChatGPTRequest request) {
		return chatGPTService.ProcessMessage(request);
   }
}
