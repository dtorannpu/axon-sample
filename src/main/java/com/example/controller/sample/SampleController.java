package com.example.controller.sample;

import com.example.query.service.SampleQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sample")
public class SampleController {
  private final SampleQueryService sampleQueryService;

  @GetMapping
  public String index(Model model) {
    List<GetResponse> data =
        sampleQueryService.findAll().stream().map(s -> new GetResponse(s.id(), s.body())).toList();
    model.addAttribute("data", data);
    return "sample/index";
  }
}
