package com.example.command.dto;

import java.util.UUID;

public record SampleCommand(UUID docId, String body) {}
