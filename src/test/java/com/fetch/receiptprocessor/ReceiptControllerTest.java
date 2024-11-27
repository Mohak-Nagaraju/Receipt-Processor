package com.fetch.receiptprocessor;

import com.fetch.receiptprocessor.controller.ReceiptController;
import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptPointsResponse;
import com.fetch.receiptprocessor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReceiptControllerTest {

    @InjectMocks
    private ReceiptController receiptController;

    @Mock
    private ReceiptService receiptService;

    private Receipt receipt;
    private String receiptId;

    @BeforeEach
    void setUp() {
        // Initializing mocks
        MockitoAnnotations.openMocks(this);

        // Set up the mock receipt and expected receipt ID
        receipt = new Receipt("Walmart", "2022-01-01", "13:01", "10.00", Arrays.asList());
        receiptId = UUID.randomUUID().toString(); // Generating a mock UUID for the test
    }

    @Test
    void testGetPoints() {
        // Mock the service response for points
        when(receiptService.getPoints(receiptId)).thenReturn(88);

        // Calling the controller method directly
        ResponseEntity<ReceiptPointsResponse> response = receiptController.getPoints(receiptId);

        // Extract the body, which should be a ReceiptPointsResponse object
        ReceiptPointsResponse responseBody = response.getBody();

        // Ensure the response body is not null and contains the correct points
        assert responseBody != null; // Ensure it's not null
        assertEquals(88, responseBody.getPoints()); // Verifying points
    }
}
