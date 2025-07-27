package com.example.command.handler;

import com.example.command.dto.SampleCommand;
import com.example.event.model.SampleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SampleCommandHandler {
  private final EventGateway eventGateway;

  @CommandHandler
  public void run(SampleCommand command) {
    var event = new SampleEvent(command.docId(), command.body());
    log.info("event created: {}", event);
    eventGateway.publish(event);
  }
}
