package com.fucct.restdocssample.controller;

import com.fucct.restdocssample.controller.request.SampleRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @PostMapping(value = "/v1/sample", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> modifyMemberV1(@ModelAttribute SampleRequest sampleRequest) {
        return ResponseEntity.ok().build();
    }
}
