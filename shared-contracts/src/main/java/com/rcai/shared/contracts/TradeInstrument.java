package com.rcai.shared.contracts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeInstrument {
    String baseCurrency;
    String quoteCurrency;
    int pricePrecision;
    String symbol;
}
