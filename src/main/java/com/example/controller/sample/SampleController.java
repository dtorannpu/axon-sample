package com.example.controller.sample;

import com.example.command.service.SampleCommandService;
import com.example.controller.sample.form.CreateForm;
import com.example.query.service.SampleQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sample")
public class SampleController {
  private final SampleCommandService sampleCommandService;
  private final SampleQueryService sampleQueryService;

  @GetMapping
  public String index(Model model) {
    List<GetResponse> data =
        sampleQueryService.findAll().stream().map(s -> new GetResponse(s.id(), s.body())).toList();
    model.addAttribute("data", data);
    return "sample/index";
  }

  @GetMapping("/create")
  public String showCreate(Model model) {
    model.addAttribute("createForm", new CreateForm());
    return "sample/create";
  }

  @PostMapping("/create")
  public String create(@Validated CreateForm form, BindingResult result) {
    if (result.hasErrors()) {
      return "sample/create";
    }

    sampleCommandService.request(form.getBody());

    return "redirect:/sample";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable Long id, Model model) {
    GetResponse response =
        sampleQueryService
            .get(id)
            .map(s -> new GetResponse(s.id(), s.body()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    model.addAttribute("data", response);

    return "sample/show";
  }
}
