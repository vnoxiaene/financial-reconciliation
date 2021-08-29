package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.exception.getUnmatchedReportsException;
import com.vnoxiaene.financialreconciliation.model.MatchingReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReconciliationService {

    MatchingReport compare(List<MultipartFile> files) throws IOException, getUnmatchedReportsException;

}
