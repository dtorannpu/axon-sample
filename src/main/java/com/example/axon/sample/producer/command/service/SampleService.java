package com.example.axon.sample.producer.command.service;

import com.example.axon.sample.producer.command.DocCreate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
  private final EventGateway eventGateway;

  public SampleService(EventGateway eventGateway) {
    this.eventGateway = eventGateway;
  }

  private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

  @CommandHandler
  public void run(DocCreate command) {
    logger.info("command accepted: {}", command);
    var event = new DocCreate(command.docId(), command.body());
    eventGateway.publish(event);
  }
}
