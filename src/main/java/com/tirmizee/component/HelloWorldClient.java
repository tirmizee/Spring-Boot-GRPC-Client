package com.tirmizee.component;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tirmizee.controller.dto.PersonDTO;
import com.tirmizee.grpc.HelloWorldServiceGrpc;
import com.tirmizee.grpc.Message;
import com.tirmizee.grpc.Person;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

@Component
public class HelloWorldClient{
	
	  private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldClient.class);

	  private HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;
	  
	  private HelloWorldServiceGrpc.HelloWorldServiceStub helloWorldServiceNonBlockingStub;
	  
	  @PostConstruct
	  private void init() {
		  ManagedChannel managedChannel = ManagedChannelBuilder
			  .forAddress("localhost", 9898).usePlaintext().build();

		  helloWorldServiceBlockingStub = HelloWorldServiceGrpc.newBlockingStub(managedChannel);
		  helloWorldServiceNonBlockingStub = HelloWorldServiceGrpc.newStub(managedChannel);
	  }
	  
	  public String blocking(PersonDTO personDTO) {
	    Person person = Person.newBuilder()
	    	.setFirstName(personDTO.getFirstName())
	        .setLastName(personDTO.getLastName())
	        .build();
	    LOGGER.info("client sending {}", person);

	    com.tirmizee.grpc.Message message = helloWorldServiceBlockingStub.sayHello(person);

	    return message.getMessage();
	  }
	  
	  public void nonBlocking(PersonDTO personDTO) {
		    Person person = Person.newBuilder()
		    	.setFirstName(personDTO.getFirstName())
		        .setLastName(personDTO.getLastName())
		        .build();
		    LOGGER.info("client sending {}", person);

		    com.tirmizee.grpc.Message message = null;
		    
		    helloWorldServiceNonBlockingStub.sayHello(person, new StreamObserver<com.tirmizee.grpc.Message>() {
				
				@Override
				public void onNext(Message value) {
					LOGGER.info("client onNext {}", value);
				}
				
				@Override
				public void onError(Throwable t) {
					LOGGER.info("client onError {}", t.getMessage());
				}
				
				@Override
				public void onCompleted() {
					LOGGER.info("client onCompleted {}", person);
				}
			});
		    
		    LOGGER.info("client received {}", message);

		  }

}
