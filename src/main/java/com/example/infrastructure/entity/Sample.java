package com.example.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Sample {
  @Id @GeneratedValue private Long id;
  private String body;
}
