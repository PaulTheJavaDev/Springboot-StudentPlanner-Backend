package de.pls.stundenplaner.model;

import java.util.UUID;

public record User(String firstName, String lastName, UUID uuid) {}