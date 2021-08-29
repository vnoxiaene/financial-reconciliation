package com.vnoxiaene.financialreconciliation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "profileName", "transactionDate"
        , "transactionAmount"
        , "transactionNarrative"
        , "transactionDescription", "transactionID"
        , "transactionType", "walletReference" })
public class FinancialTransaction {
    private String profileName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
