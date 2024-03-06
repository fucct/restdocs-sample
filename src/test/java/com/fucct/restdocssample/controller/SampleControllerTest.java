package com.fucct.restdocssample.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class SampleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(print())
            .build();
        RestAssuredMockMvc.given().mockMvc(mockMvc);
    }

    @Test
    void sample() {
        // given
        Map<String, Object> params = new HashMap<>();
        params.put("form1", "form1");
        params.put("form2", "form2");

        // when
        MockMvcResponse response = RestAssuredMockMvc.given().mockMvc(mockMvc)
            .accept(ContentType.JSON)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("part1", "test photo", "image/png")
            .formParams(params)
            .when()
            .post("/v1/sample");

        response.then()
            .apply(
                document("v1-modify",
                    preprocessRequest(
                        modifyUris()
                            .scheme("https")
                            .host("HOST")
                            .removePort(),
                        prettyPrint()),
                    preprocessResponse(
                        modifyUris()
                            .scheme("https")
                            .host("HOST")
                            .removePort(),
                        prettyPrint()),
                    formParameters(
                        parameterWithName("form1").description("form1"),
                        parameterWithName("form2").description("form2")
                    ),
                    requestParts(
                        partWithName("part1").description("part1")
                    )
                )
            )
            .statusCode(HttpStatus.OK.value());

    }
}
