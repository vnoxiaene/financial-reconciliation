package com.vnoxiaene.financialreconciliation.controller;

import com.vnoxiaene.financialreconciliation.service.ReconciliatonService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping(value = "/files")
public class FileUploadController {

    private final ReconciliatonService reconciliatonService;

    @Autowired
    public FileUploadController(ReconciliatonService reconciliatonService) {
        this.reconciliatonService = reconciliatonService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> compare(@RequestParam("file01") MultipartFile file01, @RequestParam("file02") MultipartFile file02){
        try {
            return ResponseEntity.ok().body(reconciliatonService.compare(file01,file02));
        } catch (IOException e) {
          log.error(e);
          return ResponseEntity.internalServerError().build();
        }
    }

}
