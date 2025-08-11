package com.example.controller.sample;

import com.example.command.service.SampleCommandService;
import com.example.query.service.SampleQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sample")
public class SampleApiController {

  private final SampleCommandService sampleCommandService;
  private final SampleQueryService sampleQueryService;

  @GetMapping
  public List<GetResponse> list() {
    return sampleQueryService.findAll().stream()
        .map(s -> new GetResponse(s.id(), s.body()))
        .toList();
  }

  @GetMapping("{id}")
  public GetResponse get(@PathVariable Long id) {
    return sampleQueryService
        .get(id)
        .map(s -> new GetResponse(s.id(), s.body()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostResponse post(@RequestBody PostRequest req) {
    var id = sampleCommandService.request(req.body());
    return new PostResponse(id);
  }
}
