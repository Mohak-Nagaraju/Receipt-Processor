package com.fetch.receiptprocessor.model;

public class ReceiptSummaryResponse {
    private String id;
    private int points;

    public ReceiptSummaryResponse(String id, int points) {
        this.id = id;
        this.points = points;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
