package com.rcai.shared.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public final class TradeEvent {
    @JsonProperty("eventId")
    private final UUID eventId;
    
    @JsonProperty("tradeId")
    private final UUID tradeId;

    @JsonProperty("correlationId")
    private final String correlationId;

    @JsonProperty("serviceName")
    private final String serviceName;

    @JsonProperty("eventType")
    private final TradeAction eventType;

    @JsonProperty("status")
    private final TradeStatus status;

    @JsonProperty("instrument")
    private final TradeInstrument instrument;

    @JsonProperty("side")
    private final TradeSide side;

    @JsonProperty("notionalAmount")
    private final BigDecimal notionalAmount;

    @JsonProperty("requestedPrice")
    private final BigDecimal requestedPrice;

    @JsonProperty("executionPrice")
    private final BigDecimal executionPrice;

    @JsonProperty("orderType")
    private final OrderType orderType;

    @JsonProperty("executionVenue")
    private final ExecutionVenue executionVenue;

    @JsonProperty("latencyMs")
    private final long latencyMs;

    @JsonProperty("errorCode")
    private final String errorCode;

    @JsonProperty("timestamp")
    private final Instant timestamp;

    @JsonProperty("schemaVersion")
    private final int schemaVersion;
}
