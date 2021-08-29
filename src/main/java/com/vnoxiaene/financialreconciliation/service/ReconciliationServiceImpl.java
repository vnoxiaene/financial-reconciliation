package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.exception.getUnmatchedReportsException;
import com.vnoxiaene.financialreconciliation.model.*;
import com.vnoxiaene.financialreconciliation.util.FilesValidator;
import com.vnoxiaene.financialreconciliation.util.FinancialTransactionReader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ReconciliationServiceImpl implements ReconciliationService {

    private final UnmatchedReportService unmatchedReportService;

    @Autowired
    public ReconciliationServiceImpl(UnmatchedReportService unmatchedReportService) {
        this.unmatchedReportService = unmatchedReportService;
    }


    @Override
    public MatchingReport compare(List<MultipartFile> files) throws FileNotFoundException, getUnmatchedReportsException {
        FilesValidator.validate(files);
        MatchingReport matchingReport = new MatchingReport();
        List<List<FinancialTransaction>> financialTransactions = files.stream().map(FinancialTransactionReader::read).distinct().collect(Collectors.toUnmodifiableList());
        var results = Arrays.asList(new Result(), new Result());
        var unmatchedReports = new ArrayList<UnmatchedReport>();
        unmatchedReports.addAll(unmatchedReportService.getUnmatchedReports(files.get(0),files.get(1), financialTransactions.get(0), financialTransactions.get(1), results.get(0) ));
        unmatchedReports.addAll(unmatchedReportService.getUnmatchedReports(files.get(1),files.get(0), financialTransactions.get(1), financialTransactions.get(0), results.get(1) ));
        matchingReport.setResults(results);
        matchingReport.setUnmatchedReports(unmatchedReports);
        return matchingReport;
    }


}
