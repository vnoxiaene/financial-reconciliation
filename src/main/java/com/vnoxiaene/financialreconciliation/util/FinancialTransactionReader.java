package com.vnoxiaene.financialreconciliation.util;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.vnoxiaene.financialreconciliation.model.FinancialTransaction;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
public class FinancialTransactionReader {

    public static List<FinancialTransaction> read(MultipartFile file){
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = mapper.schemaFor(FinancialTransaction.class)
                    .withSkipFirstDataRow(true)
                    .withoutQuoteChar()
                    ;
            MappingIterator<FinancialTransaction> transactionMappingIterator = mapper
                    .registerModule(new JavaTimeModule())
                    .readerFor(FinancialTransaction.class)
                    .with(schema).readValues(file.getInputStream());
            List<FinancialTransaction> financialTransactions = transactionMappingIterator.readAll();
            log.debug("List of transactions: {}",new Gson().toJson(financialTransactions));
            return financialTransactions;
        } catch (IOException e) {
            log.error(e);
            return Collections.emptyList();
        }
    }
}
