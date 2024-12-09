package com.fetch.receiptprocessor.controller;

import com.fetch.receiptprocessor.model.Receipt;
import com.fetch.receiptprocessor.model.ReceiptPointsResponse;
import com.fetch.receiptprocessor.model.ReceiptResponse;
import com.fetch.receiptprocessor.model.ReceiptSummaryResponse;
import com.fetch.receiptprocessor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(new ReceiptResponse(id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<ReceiptPointsResponse> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ReceiptPointsResponse(points));
    }

    @GetMapping
    public ResponseEntity<List<ReceiptSummaryResponse>> getAllReceipts() {
        List<ReceiptSummaryResponse> receipts = receiptService.getAllReceipts();
        return ResponseEntity.ok(receipts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceiptPointsResponse> updateReceipt(
            @PathVariable String id,
            @Valid @RequestBody Receipt updatedReceipt) {
        ReceiptPointsResponse response = receiptService.updateReceipt(id, updatedReceipt);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceipt(@PathVariable String id) {
        receiptService.deleteReceipt(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ReceiptSummaryResponse>> filterReceipts(
            @RequestParam int minPoints,
            @RequestParam int maxPoints) {
        List<ReceiptSummaryResponse> filteredReceipts = receiptService.filterReceipts(minPoints, maxPoints);
        return ResponseEntity.ok(filteredReceipts);
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getReceiptAnalytics() {
        Map<String, Object> analytics = receiptService.getReceiptAnalytics();
        return ResponseEntity.ok(analytics);
    }

//    @PostMapping("/bulkupload")
//    public ResponseEntity<List<ReceiptSummaryResponse>> uploadReceipts(@Valid @RequestBody List<Receipt> receipts) {
//        // Process the list of receipts
//        List<ReceiptSummaryResponse> responses = receiptService.processBulkReceipts(receipts);
//
//        // Return a list of ReceiptSummaryResponse objects
//        return ResponseEntity.ok(responses);
//    }







}
