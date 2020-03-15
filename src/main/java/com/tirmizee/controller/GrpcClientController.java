package com.tirmizee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tirmizee.component.HelloWorldClient;
import com.tirmizee.controller.dto.PersonDTO;

@RestController
public class GrpcClientController {
	
	@Autowired
	private HelloWorldClient helloWorldClient;
	
	@GetMapping(path = "/grpc/blocking/person")
	public String blockingMessage(PersonDTO personDTO) {
		return helloWorldClient.blocking(personDTO);
	}
	
	@GetMapping(path = "/grpc/nonblocking/person")
	public String nonBlockingMessage(PersonDTO personDTO) {
		helloWorldClient.nonBlocking(personDTO);
		return "Success";
	}

}
