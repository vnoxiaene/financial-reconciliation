package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
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

}
