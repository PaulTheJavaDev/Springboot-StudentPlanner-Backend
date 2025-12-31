package de.pls.stundenplaner.auth.dto.response;

import java.util.UUID;

public record LoginResponse(UUID sessionID) {}