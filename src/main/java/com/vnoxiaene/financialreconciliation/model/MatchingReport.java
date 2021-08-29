package com.vnoxiaene.financialreconciliation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MatchingReport {

    private List<Result> results;
    private List<UnmatchedReport> unmatchedReports;


}
