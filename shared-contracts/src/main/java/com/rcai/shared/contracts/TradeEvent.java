package com.rcai.shared.contracts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public final class TradeEvent {
    private final UUID eventId;
    private final UUID tradeId;
    private final String correlationId;
    private final String serviceName;
    private final TradeAction eventType;
    private final TradeStatus status;
    private final TradeInstrument instrument;
    private final TradeSide side;
    private final BigDecimal notionalAmount;
    private final BigDecimal requestedPrice;
    private final BigDecimal executionPrice;
    private final OrderType orderType;
    private final ExecutionVenue executionVenue;
    private final long latencyMs;
    private final String errorCode;
    private final Instant timestamp;
    private final int schemaVersion;
}