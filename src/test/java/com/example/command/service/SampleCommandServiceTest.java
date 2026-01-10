package com.example.command.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.command.dto.SampleCommand;
import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SampleCommandServiceTest {

  @Mock private CommandGateway commandGateway;

  @InjectMocks private SampleCommandService sampleCommandService;

  @BeforeEach
  void setUp() {
    // Setup mock behavior if needed
  }

  @Test
  void request_shouldReturnUUIDAndSendCommand() {
    // Given
    String body = "test body";

    // When
    UUID result = sampleCommandService.request(body);

    // Then
    assertNotNull(result);
    verify(commandGateway, times(1)).sendAndWait(any(SampleCommand.class));
  }

  @Test
  void request_shouldCreateCommandWithGeneratedIdAndBody() {
    // Given
    String body = "test content";

    // When
    UUID result = sampleCommandService.request(body);

    // Then
    assertNotNull(result);
    verify(commandGateway)
        .sendAndWait(
            argThat(
                command -> {
                  if (command instanceof SampleCommand sampleCommand) {
                    return sampleCommand.body().equals(body) && sampleCommand.docId() != null;
                  }
                  return false;
                }));
  }

  @Test
  void request_shouldGenerateDifferentUUIDsForMultipleCalls() {
    // Given
    String body = "test body";

    // When
    UUID firstId = sampleCommandService.request(body);
    UUID secondId = sampleCommandService.request(body);

    // Then
    assertNotEquals(firstId, secondId);
    verify(commandGateway, times(2)).sendAndWait(any(SampleCommand.class));
  }
}
