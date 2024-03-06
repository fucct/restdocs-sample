package com.fucct.restdocssample.controller.request;

import org.springframework.web.multipart.MultipartFile;

public record SampleRequest(
    String form1,
    String form2,
    MultipartFile part1
) {
}
