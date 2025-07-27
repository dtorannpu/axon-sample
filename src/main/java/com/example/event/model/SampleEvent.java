package com.example.event.model;

import java.util.UUID;

public record SampleEvent(UUID docId, String body) {}
