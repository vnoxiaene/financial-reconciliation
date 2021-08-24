package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;

import java.util.List;

public interface FilterSimilarTransaction {

    boolean isSimilar(FinancialTransaction leftTransaction, FinancialTransaction rightTransaction);

    List<FinancialTransaction> filter(List<FinancialTransaction> transactions);
}
