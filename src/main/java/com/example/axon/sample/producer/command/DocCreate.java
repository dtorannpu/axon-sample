package com.example.axon.sample.producer.command;

import java.util.UUID;

public record DocCreate(UUID docId, String body) {}
