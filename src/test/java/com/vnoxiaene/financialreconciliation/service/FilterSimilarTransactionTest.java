package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class FilterSimilarTransactionTest {

    @Test
    public void returnTrueWhenSimilarTransaction(){
        FilterSimilarTransaction filterSimilarTransaction = new FilterSimilarTransactionImpl();
        FinancialTransaction transaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-20000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5")
                .transactionDate(LocalDateTime.now())
                .transactionType((byte) 0)
                .profileName("TEST1")
                .build();
        FinancialTransaction duplicateTransaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-20000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5")
                .transactionDate(LocalDateTime.now())
                .transactionType((byte) 1)
                .profileName("TEST2")
                .build();
        assertThat(filterSimilarTransaction.isSimilar(transaction, duplicateTransaction)).isTrue();
    }
    @Test
    public void returnFalseWhenNotSimilarTransaction(){
        FilterSimilarTransaction filterSimilarTransaction = new FilterSimilarTransactionImpl();
        FinancialTransaction transaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-20000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzB5")
                .transactionDate(LocalDateTime.now())
                .transactionType((byte) 0)
                .profileName("TEST1")
                .build();
        FinancialTransaction duplicateTransaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-20000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5")
                .transactionDate(LocalDateTime.now())
                .transactionType((byte) 0)
                .profileName("TEST1")
                .build();
        assertThat(filterSimilarTransaction.isSimilar(transaction, duplicateTransaction)).isFalse();
    }
}
