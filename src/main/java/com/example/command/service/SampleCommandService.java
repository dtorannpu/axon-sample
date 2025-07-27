package com.example.command.service;

import com.example.command.dto.SampleCommand;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SampleCommandService {

  private final CommandGateway commandGateway;

  @CommandHandler
  public UUID request(String body) {
    var id = UUID.randomUUID();
    var command = new SampleCommand(id, body);
    log.info("command send: {}", command);
    commandGateway.sendAndWait(command);
    return id;
  }
}
