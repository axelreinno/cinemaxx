package com.academy.cinemaxx.controllers;

import com.academy.cinemaxx.dtos.response.PresignedUrlResponseDto;
import com.academy.cinemaxx.dtos.response.ResponseDTO;
import com.academy.cinemaxx.services.FileService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/v1/files")
public class FileController {

    FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/thumbnail")
    public ResponseEntity<ResponseDTO<PresignedUrlResponseDto>> generatePresignedUploadUrl(
            @NotBlank(message = "filename is required")
            @RequestParam
            String filename
    ) throws Exception {
        PresignedUrlResponseDto response = fileService.generatePresignedUploadUrl(filename);
        return ResponseEntity.ok(ResponseDTO.success(response));
    }
}
