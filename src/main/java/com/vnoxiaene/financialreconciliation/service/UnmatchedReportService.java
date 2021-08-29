package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.exception.getUnmatchedReportsException;
import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import com.vnoxiaene.financialreconciliation.model.Result;
import com.vnoxiaene.financialreconciliation.model.UnmatchedReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UnmatchedReportService {

    List<UnmatchedReport> getUnmatchedReports(MultipartFile file01, MultipartFile file02, List<FinancialTransaction> transactionList01, List<FinancialTransaction> transactionList02, Result result) throws getUnmatchedReportsException;

}

