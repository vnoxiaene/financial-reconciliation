package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class FilterSimilarTransactionTest {

    @Test
    public void returnTrueWhenDuplicateTransaction(){
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
    public void returnFalseWhenNotDuplicateTransaction(){
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
    @Test
    public void returnDistinctTransactionWhenDuplicate(){
        FilterSimilarTransaction filterSimilarTransaction = new FilterSimilarTransactionImpl();
        FinancialTransaction transaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-20000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5")
                .transactionType((byte) 0)
                .profileName("TEST1")
                .build();
        FinancialTransaction duplicateTransaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-20000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzA5")

                .transactionType((byte) 0)
                .profileName("TEST1")
                .build();
        FinancialTransaction notDuplicateTransaction = FinancialTransaction.builder()
                .transactionAmount(BigDecimal.valueOf(-21000))
                .transactionID(BigDecimal.valueOf(584011808649511l))
                .walletReference("P_NzI2ODY2ODlfMTM4MjcwMTU2NS45MzB5")
                .transactionDate(LocalDateTime.now().plusHours(2))
                .transactionType((byte) 1)
                .profileName("TEST3")
                .build();
        var listOfTransactions = new ArrayList<FinancialTransaction>();
        listOfTransactions.add(transaction);
        listOfTransactions.add(duplicateTransaction);
        listOfTransactions.add(notDuplicateTransaction);
        assertThat(transaction).isIn(filterSimilarTransaction.filter(listOfTransactions));
        assertThat(notDuplicateTransaction).isIn(filterSimilarTransaction.filter(listOfTransactions));
        assertThat(filterSimilarTransaction.filter(listOfTransactions)).hasSize(2);
    }
}
