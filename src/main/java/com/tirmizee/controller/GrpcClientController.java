package com.tirmizee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tirmizee.component.HelloWorldClient;

@RestController
public class GrpcClientController {
	
	@Autowired
	private HelloWorldClient helloWorldClient;
	
	@GetMapping(path = "/grpc/message/{message}")
	public String sendMessage(@PathVariable String message) {
		return null;
	}

}
