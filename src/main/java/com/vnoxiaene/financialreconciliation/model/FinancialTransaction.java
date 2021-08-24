package com.vnoxiaene.financialreconciliation.model;

import lombok.*;
import org.h2.mvstore.tx.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@EqualsAndHashCode
@Builder
@Getter
@AllArgsConstructor
public class FinancialTransaction {
    private String profileName;
    private LocalDateTime transactionDate;
    private BigDecimal transactionAmount;
    private String transactionNarrative;
    private TransactionDescription transactionDescription;
    private BigDecimal transactionID;
    private Byte transactionType;
    private String walletReference;

    @Override
    public String toString() {
        return "FinancialTransaction{" +
                "profileName='" + profileName + '\'' +
                ", transactionDate=" + transactionDate +
                ", transactionAmount=" + transactionAmount +
                ", transactionNarrative='" + transactionNarrative + '\'' +
                ", transactionDescription=" + transactionDescription +
                ", transactionID=" + transactionID +
                ", transactionType=" + transactionType +
                ", walletReference='" + walletReference + '\'' +
                '}';
    }
}
