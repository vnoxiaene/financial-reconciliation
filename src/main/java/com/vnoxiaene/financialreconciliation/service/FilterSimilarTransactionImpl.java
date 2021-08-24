package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FilterSimilarTransactionImpl implements FilterSimilarTransaction {

    public boolean isSimilar(FinancialTransaction leftTransaction, FinancialTransaction rightTransaction) {
        log.debug("verifying if leftTransaction:{} and " +
                "rightTransaction:{} are similar",leftTransaction, rightTransaction);
        if(leftTransaction.getTransactionID()
                .equals(rightTransaction.getTransactionID())
        &&
            leftTransaction.getTransactionAmount()
            .equals(rightTransaction.getTransactionAmount())
        &&
            leftTransaction.getWalletReference()
            .equals(rightTransaction.getWalletReference())
        ){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public List<FinancialTransaction> filter(List<FinancialTransaction> transactions) {

        return transactions.stream().distinct().collect(Collectors.toUnmodifiableList());
            }
}
