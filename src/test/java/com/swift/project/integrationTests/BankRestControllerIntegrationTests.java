package com.swift.project.integrationTests;


import com.swift.project.DTOs.BanksByCountryDTO;
import com.swift.project.DTOs.HqDTO;
import com.swift.project.DTOs.Message;
import com.swift.project.DTOs.SingleBankDTO;
import com.swift.project.exceptions.BankNotFoundException;
import com.swift.project.other.Messages;
import com.swift.project.service.BankService;
import com.swift.project.service.XlsxParseService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashMap;
import java.util.Map;

import static com.swift.project.other.constants.ISO2_CODE_IDENTIFIER;
import static com.swift.project.other.constants.SWIFT_CODE_IDENTIFIER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BankRestControllerIntegrationTests {
    @MockitoBean
    private XlsxParseService xlsxParseService;

    /*we do not want to test on the xslx file, but rather on the H2 database
    input specified in test-data.sql section.
     */

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BankService bankService;

    private HttpEntity<Map<String, Object>> generate_req_entity(String SwiftCode) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("address", "123 Bank Street");
        requestBody.put("bankName", "Example Bank");
        requestBody.put("countryISO2", "PL");
        requestBody.put("countryName", "Poland");
        requestBody.put("isHeadquarter", true);
        requestBody.put("swiftCode", SwiftCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(requestBody, headers);
    }

    @Test
    void testEndpoint2Success() {
        ResponseEntity<BanksByCountryDTO> response = restTemplate.exchange(
                "/v1/swift-codes/country/PL",
                HttpMethod.GET,
                null,
                BanksByCountryDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getCountryBanks().isEmpty());
    }

    @Test
    void testEndpoint2IntegrationWithAdvice() {
        ResponseEntity<Message> response = restTemplate.exchange(
                "/v1/swift-codes/country/a",
                HttpMethod.GET,
                null,
                Message.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Messages.illegalISO2CodeExceptionErrorMessage(), response.getBody().getMessage());

        ResponseEntity<Message> response2 = restTemplate.exchange(
                "/v1/swift-codes/country/ZZ",
                HttpMethod.GET,
                null,
                Message.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals(Messages.bankNotFoundExceptionErrorMessage(ISO2_CODE_IDENTIFIER, "ZZ"), response2.getBody().getMessage());
    }

    @Test
    void testEndpoint1Success() {
        ResponseEntity<SingleBankDTO> response = restTemplate.exchange(
                "/v1/swift-codes/BREXPLPWWAL",
                HttpMethod.GET,
                null,
                SingleBankDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getIsHeadquarter());
        assertEquals("BREXPLPWWAL", response.getBody().getSwiftCode());

        ResponseEntity<HqDTO> response2 = restTemplate.exchange(
                "/v1/swift-codes/BREXPLPWXXX",
                HttpMethod.GET,
                null,
                HqDTO.class
        );
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertTrue(response2.getBody().getIsHeadquarter());
        assertEquals("BREXPLPWXXX", response2.getBody().getSwiftCode());
        assertEquals(3, response2.getBody().getBranches().size());
    }

    @Test
    void testEndpoint1IntegrationWithAdvice() {
        ResponseEntity<Message> response = restTemplate.exchange(
                "/v1/swift-codes/BREL",
                HttpMethod.GET,
                null,
                Message.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Messages.illegalSwiftCodeExceptionErrorMessage(), response.getBody().getMessage());
        ResponseEntity<Message> response2 = restTemplate.exchange(
                "/v1/swift-codes/BRELAAAAAA",
                HttpMethod.GET,
                null,
                Message.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals(Messages.bankNotFoundExceptionErrorMessage(SWIFT_CODE_IDENTIFIER, "BRELAAAAAA"), response2.getBody().getMessage());
    }

    @Test
    void testEndpoint3Success() {
        ResponseEntity<Message> response = restTemplate.exchange(
                "/v1/swift-codes/",
                HttpMethod.POST,
                generate_req_entity("EXAMPLXXX"),
                Message.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Messages.bankSuccessfullyAdded("EXAMPLXXX"), response.getBody().getMessage());
        assertDoesNotThrow(() -> bankService.getBank("EXAMPLXXX"));
    }

    @Test
    void testEndpoint3IntegrationWithAdvice() {
        ResponseEntity<Message> response = restTemplate.exchange(
                "/v1/swift-codes/",
                HttpMethod.POST,
                generate_req_entity("EX"),
                Message.class
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(Messages.illegalSwiftCodeExceptionErrorMessage(), response.getBody().getMessage());
    }

    @Test
    @Transactional
    void testEndpoint4SuccessAndIntegration() {
        ResponseEntity<Message> response = restTemplate.exchange(
                "/v1/swift-codes/CITIPLPXXXX",
                HttpMethod.DELETE,
                null,
                Message.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Messages.bankSuccessfullyDeleted("CITIPLPXXXX"), response.getBody().getMessage());
        assertThrows(BankNotFoundException.class, () -> bankService.getBank("CITIPLPXXXX"));
        ResponseEntity<Message> response2 = restTemplate.exchange(
                "/v1/swift-codes/CITIPLPXXXX",
                HttpMethod.DELETE,
                null,
                Message.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals(Messages.bankNotFoundExceptionErrorMessage(SWIFT_CODE_IDENTIFIER, "CITIPLPXXXX"), response2.getBody().getMessage());
    }
}
