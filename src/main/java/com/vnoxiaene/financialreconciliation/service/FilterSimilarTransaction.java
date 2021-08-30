package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;

public interface FilterSimilarTransaction {

    boolean isSimilar(FinancialTransaction leftTransaction, FinancialTransaction rightTransaction);

}
