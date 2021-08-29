package com.vnoxiaene.financialreconciliation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private String filename;
    private LocalDateTime date;
    private String reference;
    private BigDecimal amount;
}
