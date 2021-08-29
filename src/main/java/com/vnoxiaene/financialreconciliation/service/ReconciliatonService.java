package com.vnoxiaene.financialreconciliation.service;

import com.vnoxiaene.financialreconciliation.model.MatchingReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ReconciliatonService {

    MatchingReport compare(MultipartFile file01, MultipartFile file02) throws IOException;

}
