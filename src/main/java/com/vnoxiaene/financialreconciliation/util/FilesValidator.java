package com.vnoxiaene.financialreconciliation.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class FilesValidator {

    public static void validate(List<MultipartFile> files) throws FileNotFoundException {
        for (MultipartFile file :
                files) {
            if (file == null) {
                throw new FileNotFoundException("Please check if two files were uploaded");
            }
            }
    }
}
