package com.vnoxiaene.financialreconciliation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {
    private Long totalRecords;
    private Long matchingRecords;
    private Long unmatchedRecords;
    private String filename;
}
