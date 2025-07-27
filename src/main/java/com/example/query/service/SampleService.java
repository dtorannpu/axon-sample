package com.example.query.service;

import com.example.event.model.SampleEvent;
import com.example.infrastructure.entity.Sample;
import com.example.infrastructure.repository.SampleRepository;
import com.example.query.dto.SampleDTO;
import com.example.query.request.SampleQuery;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SampleService {

  private final SampleRepository repository;

  @Transactional
  public void create(SampleEvent event) {
    Sample sample = new Sample();
    sample.setBody(event.body());
    repository.save(sample);
  }

  public SampleDTO findById(SampleQuery query) {
    return repository
        .findById(query.id())
        .map(s -> new SampleDTO(s.getId(), s.getBody()))
        .orElse(null);
  }

  public List<SampleDTO> findAll() {
    return repository.findAll().stream()
        .map(s -> new SampleDTO(s.getId(), s.getBody()))
        .collect(Collectors.toList());
  }
}
