package com.example.controller.sample.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateForm {
  @NotBlank private String body;
}
