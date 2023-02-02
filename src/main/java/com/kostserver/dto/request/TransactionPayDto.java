package com.kostserver.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TransactionPayDto {

    private Long id;
    private MultipartFile image;
}
