package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.exception.getUnmatchedReportsException;
import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import com.vnoxiaene.financialreconciliation.model.Report;
import com.vnoxiaene.financialreconciliation.model.Result;
import com.vnoxiaene.financialreconciliation.model.UnmatchedReport;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class UnmatchedReportServiceImpl implements UnmatchedReportService {

    private final FilterSimilarTransaction filterSimilarTransaction;

    public UnmatchedReportServiceImpl(FilterSimilarTransaction filterSimilarTransaction) {
        this.filterSimilarTransaction = filterSimilarTransaction;
    }

    @Override
    public List<UnmatchedReport> getUnmatchedReports(MultipartFile file01, MultipartFile file02, List<FinancialTransaction> transactionList01, List<FinancialTransaction> transactionList02, Result result) throws getUnmatchedReportsException {
        try {
            var matchingRecords = 0l;
            var unmatchedRecords = 0l;
            var unmatchedReports = new ArrayList<UnmatchedReport>();

            for (FinancialTransaction transaction: transactionList01
            ) {
                if (transactionList02.contains(transaction)) {
                    log.debug("::: matching record: {}",transaction);
                    matchingRecords++;
                }else if(transactionList02.stream().anyMatch(t-> filterSimilarTransaction.isSimilar(t, transaction))){
                    log.debug("::: unmatched record, but similar: {}",transaction);
                    List<FinancialTransaction> similarFinancialTransactions = transactionList02.stream().filter(t -> filterSimilarTransaction.isSimilar(t, transaction)).distinct().collect(Collectors.toList());
                    for (FinancialTransaction similarFinancialTransaction :
                            similarFinancialTransactions) {
                        Report report01 = Report.builder()
                                .filename(file01.getOriginalFilename())
                                .amount(transaction.getTransactionAmount())
                                .date(transaction.getTransactionDate())
                                .reference(transaction.getWalletReference())
                                .build();
                        Report report02 = Report.builder()
                                .filename(file02.getOriginalFilename())
                                .amount(similarFinancialTransaction.getTransactionAmount())
                                .date(similarFinancialTransaction.getTransactionDate())
                                .reference(similarFinancialTransaction.getWalletReference())
                                .build();
                        unmatchedReports.add(UnmatchedReport.builder()
                                .leftReport(report01)
                                .rightReport(report02)
                                .build());
                    }
                    unmatchedRecords++;
                }else{
                    log.debug("::: unmatched record: {}",transaction);
                    Report report01 = Report.builder()
                            .filename(file01.getOriginalFilename())
                            .amount(transaction.getTransactionAmount())
                            .date(transaction.getTransactionDate())
                            .reference(transaction.getWalletReference())
                            .build();
                    Report report02 = Report.builder()
                            .filename(file02.getOriginalFilename())
                            .build();
                    unmatchedReports.add(UnmatchedReport.builder()
                            .leftReport(report01)
                            .rightReport(report02)
                            .build());
                    unmatchedRecords++;
                }
            }
            result.setFilename(file01.getOriginalFilename());
            result.setTotalRecords(transactionList01.stream().count());
            result.setMatchingRecords(matchingRecords);
            result.setUnmatchedRecords(unmatchedRecords);
            return unmatchedReports;
        } catch (Exception e) {
            log.error("::: ERROR when getUnmatchedReports. ",e);
            throw new getUnmatchedReportsException("There was an error trying to get unmatched reports.");
        }
    }
}
