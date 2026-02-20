package com.rcai.shared.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class JsonSerializerTest {

    private final ObjectMapper mapper =
            new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void shouldSerializeTradeEventToCanonicalJsonShape() throws Exception {
        TradeInstrument instrument =
                TradeInstrument.builder()
                        .baseCurrency("EUR")
                        .quoteCurrency("USD")
                        .pricePrecision(5)
                        .symbol("EUR/USD")
                        .build();

        TradeEvent event =
                TradeEvent.builder()
                        .eventId(UUID.fromString("3f6c9b8e-5d5c-4b3e-9a1f-8c7e6a2d1f90"))
                        .tradeId(UUID.fromString("8a72c1f4-9d4b-4f1a-92a2-3b77e5c9a001"))
                        .correlationId("c9f1a2e4-7b3d-4a90-8e2a-119e77ddaa22")
                        .serviceName("execution-service")
                        .eventType(TradeAction.NEW)
                        .status(TradeStatus.EXECUTION_CONFIRMED)
                        .instrument(instrument)
                        .side(TradeSide.BUY)
                        .notionalAmount(new BigDecimal("1000000.00"))
                        .requestedPrice(new BigDecimal("1.09750"))
                        .executionPrice(new BigDecimal("1.09753"))
                        .orderType(OrderType.MARKET)
                        .executionVenue(ExecutionVenue.ECN_PRIMARY)
                        .latencyMs(18L)
                        .errorCode(null)
                        .timestamp(Instant.parse("2026-02-20T18:42:11Z"))
                        .schemaVersion(1)
                        .build();

        JsonNode actual = mapper.readTree(mapper.writeValueAsString(event));
        JsonNode expected = loadCanonicalJson();

        assertEquals(expected.get("eventId").asText(), actual.get("eventId").asText());
        assertEquals(expected.get("tradeId").asText(), actual.get("tradeId").asText());
        assertEquals(expected.get("correlationId").asText(), actual.get("correlationId").asText());
        assertEquals(expected.get("serviceName").asText(), actual.get("serviceName").asText());
        assertEquals(expected.get("eventType").asText(), actual.get("eventType").asText());
        assertEquals(expected.get("status").asText(), actual.get("status").asText());
        assertEquals(expected.get("instrument"), actual.get("instrument"));
        assertEquals(expected.get("side").asText(), actual.get("side").asText());
        assertDecimalEquals(expected.get("notionalAmount").decimalValue(), actual.get("notionalAmount").decimalValue());
        assertDecimalEquals(expected.get("requestedPrice").decimalValue(), actual.get("requestedPrice").decimalValue());
        assertDecimalEquals(expected.get("executionPrice").decimalValue(), actual.get("executionPrice").decimalValue());
        assertEquals(expected.get("orderType").asText(), actual.get("orderType").asText());
        assertEquals(expected.get("executionVenue").asText(), actual.get("executionVenue").asText());
        assertEquals(expected.get("latencyMs").asLong(), actual.get("latencyMs").asLong());
        assertEquals(expected.get("timestamp").asText(), actual.get("timestamp").asText());
        assertEquals(expected.get("schemaVersion").asInt(), actual.get("schemaVersion").asInt());
    }

    @Test
    void shouldDeserializeCanonicalJsonIntoTradeEvent() throws Exception {
        JsonNode expected = loadCanonicalJson();
        TradeEvent actual =
                mapper.readValue(
                        getClass().getResourceAsStream("/TradeModel.json"),
                        TradeEvent.class);

        assertNotNull(actual);
        assertEquals(UUID.fromString(expected.get("eventId").asText()), actual.getEventId());
        assertEquals(UUID.fromString(expected.get("tradeId").asText()), actual.getTradeId());
        assertEquals(expected.get("correlationId").asText(), actual.getCorrelationId());
        assertEquals(expected.get("serviceName").asText(), actual.getServiceName());
        assertEquals(TradeAction.valueOf(expected.get("eventType").asText()), actual.getEventType());
        assertEquals(TradeStatus.valueOf(expected.get("status").asText()), actual.getStatus());
        assertEquals(expected.get("instrument").get("baseCurrency").asText(), actual.getInstrument().getBaseCurrency());
        assertEquals(expected.get("instrument").get("quoteCurrency").asText(), actual.getInstrument().getQuoteCurrency());
        assertEquals(expected.get("instrument").get("pricePrecision").asInt(), actual.getInstrument().getPricePrecision());
        assertEquals(expected.get("instrument").get("symbol").asText(), actual.getInstrument().getSymbol());
        assertEquals(TradeSide.valueOf(expected.get("side").asText()), actual.getSide());
        assertDecimalEquals(expected.get("notionalAmount").decimalValue(), actual.getNotionalAmount());
        assertDecimalEquals(expected.get("requestedPrice").decimalValue(), actual.getRequestedPrice());
        assertDecimalEquals(expected.get("executionPrice").decimalValue(), actual.getExecutionPrice());
        assertEquals(OrderType.valueOf(expected.get("orderType").asText()), actual.getOrderType());
        assertEquals(ExecutionVenue.valueOf(expected.get("executionVenue").asText()), actual.getExecutionVenue());
        assertEquals(expected.get("latencyMs").asLong(), actual.getLatencyMs());
        assertEquals(Instant.parse(expected.get("timestamp").asText()), actual.getTimestamp());
        assertEquals(expected.get("schemaVersion").asInt(), actual.getSchemaVersion());
    }

    private JsonNode loadCanonicalJson() throws IOException {
        return mapper.readTree(getClass().getResourceAsStream("/TradeModel.json"));
    }

    private void assertDecimalEquals(BigDecimal expected, BigDecimal actual) {
        assertTrue(expected.compareTo(actual) == 0, "Expected " + expected + " but was " + actual);
    }
}
