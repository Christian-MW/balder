package com.balder.api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.balder.api.dto.request.*;
import com.balder.api.dto.response.*;

@Component
public interface ChatGPTService {
	ResponseEntity<?> ProcessMessage(ChatGPTRequest request);
}
