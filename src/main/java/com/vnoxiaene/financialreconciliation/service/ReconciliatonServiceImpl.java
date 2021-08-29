package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.*;
import com.vnoxiaene.financialreconciliation.util.FinancialTransactionReader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ReconciliatonServiceImpl implements ReconciliatonService {

    private final FilterSimilarTransaction filterSimilarTransaction;

    @Autowired
    public ReconciliatonServiceImpl(FilterSimilarTransaction filterSimilarTransaction) {
        this.filterSimilarTransaction = filterSimilarTransaction;
    }

    @Override
    public MatchingReport compare(MultipartFile file01, MultipartFile file02){
        MatchingReport matchingReport = new MatchingReport();
        List<FinancialTransaction> financialTransactions01 = FinancialTransactionReader.read(file01);
        List<FinancialTransaction> financialTransactions02 = FinancialTransactionReader.read(file02);
        List<FinancialTransaction> transactionList01 = financialTransactions01.stream()
                .distinct().collect(Collectors.toUnmodifiableList());
        List<FinancialTransaction> transactionList02 = financialTransactions02.stream().distinct().collect(Collectors.toUnmodifiableList());
        Result result01 = new Result();
        Result result02 = new Result();

        List<UnmatchedReport> unmatchedReports01 = getUnmatchedReports(file01, file02, transactionList01, transactionList02, result01);
        List<UnmatchedReport> unmatchedReports02 = getUnmatchedReports(file02, file01, transactionList02, transactionList01, result02);

        matchingReport.setResults(Arrays.asList(result01, result02));
        unmatchedReports01.addAll(unmatchedReports02);
        matchingReport.setUnmatchedReports(unmatchedReports01);
        return matchingReport;
    }

    private ArrayList<UnmatchedReport> getUnmatchedReports(MultipartFile file01, MultipartFile file02, List<FinancialTransaction> transactionList01, List<FinancialTransaction> transactionList02, Result result) {
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
    }
}
