package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.model.Item;
import com.fetch.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPoints = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        // Performing validation before proceeding
        validateReceipt(receipt);

        int points = calculatePoints(receipt);
        String id = UUID.randomUUID().toString();
        receiptPoints.put(id, points);
        return id;
    }

    public Integer getPoints(String id) {
        return receiptPoints.get(id);
    }

    public int calculatePoints(Receipt receipt) {
        int points = 0;

        // First Rule: One point for every alphanumeric character in the retailer name.
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Second Rule : 50 points if the total is a round dollar amount with no cents.
        if (receipt.getTotal().endsWith(".00")) points += 50;

        // Third Rule : 25 points if the total is a multiple of 0.25.
        if (Double.parseDouble(receipt.getTotal()) % 0.25 == 0) points += 25;

        // Fourth Rule : 5 points for every two items on the receipt.
        points += (receipt.getItems().size() / 2) * 5;

        // Fifth Rule : Item description length multiple of 3.
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        // Sixth Rule : 6 points if the day in the purchase date is odd.
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 != 0) points += 6;

        // Seventh Rule : 10 points if the time of purchase is between 2:00 PM and 4:00 PM.
        int hour = Integer.parseInt(receipt.getPurchaseTime().split(":")[0]);
        int minute = Integer.parseInt(receipt.getPurchaseTime().split(":")[1]);
        if (hour == 14 || (hour == 15 && minute < 60)) points += 10;

        return points;
    }

    //Validation checks
    private void validateReceipt(Receipt receipt) {
        if (receipt.getRetailer() == null || receipt.getRetailer().trim().isEmpty()) {
            throw new IllegalArgumentException("Retailer name must not be null or empty.");
        }
        if (!receipt.getPurchaseDate().matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Purchase date must be in the format yyyy-MM-dd.");
        }
        if (!receipt.getPurchaseTime().matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Purchase time must be in the format HH:mm.");
        }
        if (receipt.getTotal() == null || Double.parseDouble(receipt.getTotal()) < 0) {
            throw new IllegalArgumentException("Total must be a valid non-negative decimal.");
        }
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription() == null || item.getShortDescription().trim().isEmpty()) {
                throw new IllegalArgumentException("Item short description must not be null or empty.");
            }
            if (item.getPrice() == null || Double.parseDouble(item.getPrice()) < 0) {
                throw new IllegalArgumentException("Item price must be a valid non-negative decimal.");
            }
        }
    }
}