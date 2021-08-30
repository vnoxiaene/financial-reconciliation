package com.vnoxiaene.financialreconciliation.controller;

import com.vnoxiaene.financialreconciliation.exception.getUnmatchedReportsException;
import com.vnoxiaene.financialreconciliation.service.ReconciliationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

@Log4j2
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/files")
public class FileUploadController {

    private final ReconciliationService reconciliationService;

    @Autowired
    public FileUploadController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> compare(@RequestParam("file01") MultipartFile file01, @RequestParam("file02") MultipartFile file02){
        log.debug("starting comparing files...");
        try {
            return ResponseEntity.ok().body(reconciliationService.compare(Arrays.asList(file01,file02)));

        } catch (FileNotFoundException e){
            log.warn(e);
            return ResponseEntity.ok().body(e.getMessage());
        } catch (IOException e) {
            log.error(e);
            return ResponseEntity.internalServerError().build();
        } catch (getUnmatchedReportsException e) {
            log.error(e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
