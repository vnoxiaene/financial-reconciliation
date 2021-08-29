package com.vnoxiaene.financialreconciliation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnmatchedReport {

    private Report rightReport;
    private Report leftReport;
}
