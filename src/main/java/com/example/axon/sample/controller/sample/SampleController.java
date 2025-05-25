package com.example.axon.sample.controller.sample;

import com.example.axon.sample.producer.command.DocCreate;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleController {
  private final CommandGateway commandGateway;

  public SampleController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetResponse get() {
    return new GetResponse("Hello World!");
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostResponse post(@RequestBody PostRequest req) {
    var id = UUID.randomUUID();
    var command = new DocCreate(id, req.body());
    commandGateway.sendAndWait(command);
    return new PostResponse(id);
  }
}
