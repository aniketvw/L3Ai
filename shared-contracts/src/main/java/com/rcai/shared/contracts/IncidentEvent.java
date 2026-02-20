package com.rcai.shared.contracts;

import java.time.Instant;

public record IncidentEvent(String service, String incidentId, String state, Instant timestamp) {
}
