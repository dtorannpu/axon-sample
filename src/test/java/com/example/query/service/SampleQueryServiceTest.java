package com.example.query.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.query.dto.SampleDTO;
import com.example.query.request.SampleFindAllQuery;
import com.example.query.request.SampleQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SampleQueryServiceTest {

  @Mock private QueryGateway queryGateway;

  @InjectMocks private SampleQueryService sampleQueryService;

  @BeforeEach
  void setUp() {
    // Setup mock behavior if needed
  }

  @Test
  void get_shouldReturnOptionalSampleDTO() {
    // Given
    Long id = 1L;
    SampleDTO expectedDTO = new SampleDTO(id, "test body");
    when(queryGateway.query(any(SampleQuery.class), any(ResponseType.class)))
        .thenReturn(CompletableFuture.completedFuture(Optional.of(expectedDTO)));

    // When
    Optional<SampleDTO> result = sampleQueryService.get(id);

    // Then
    assertTrue(result.isPresent());
    assertEquals(expectedDTO, result.get());
    verify(queryGateway, times(1)).query(any(SampleQuery.class), any(ResponseType.class));
  }

  @Test
  void get_shouldReturnEmptyOptionalWhenNotFound() {
    // Given
    Long id = 999L;
    when(queryGateway.query(any(SampleQuery.class), any(ResponseType.class)))
        .thenReturn(CompletableFuture.completedFuture(Optional.empty()));

    // When
    Optional<SampleDTO> result = sampleQueryService.get(id);

    // Then
    assertFalse(result.isPresent());
    verify(queryGateway, times(1)).query(any(SampleQuery.class), any(ResponseType.class));
  }

  @Test
  void findAll_shouldReturnListOfSampleDTOs() {
    // Given
    List<SampleDTO> expectedList =
        Arrays.asList(new SampleDTO(1L, "body1"), new SampleDTO(2L, "body2"));
    when(queryGateway.query(any(SampleFindAllQuery.class), any(ResponseType.class)))
        .thenReturn(CompletableFuture.completedFuture(expectedList));

    // When
    List<SampleDTO> result = sampleQueryService.findAll();

    // Then
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedList, result);
    verify(queryGateway, times(1)).query(any(SampleFindAllQuery.class), any(ResponseType.class));
  }

  @Test
  void findAll_shouldReturnEmptyListWhenNoData() {
    // Given
    when(queryGateway.query(any(SampleFindAllQuery.class), any(ResponseType.class)))
        .thenReturn(CompletableFuture.completedFuture(List.of()));

    // When
    List<SampleDTO> result = sampleQueryService.findAll();

    // Then
    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(queryGateway, times(1)).query(any(SampleFindAllQuery.class), any(ResponseType.class));
  }
}
