package com.fetch.receiptprocessor.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class Receipt {
    @NotBlank(message = "Retailer name must not be null or empty.")
    private String retailer;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Purchase date must be in the format yyyy-MM-dd.")
    private String purchaseDate;

    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Purchase time must be in the format HH:mm.")
    private String purchaseTime;

    @NotNull(message = "Total must not be null.")
    @PositiveOrZero(message = "Total must be a non-negative value.")
    private String total;

    @NotNull(message = "Items list must not be null.")
    private List<@Valid Item> items;

    public Receipt(String retailer, String purchaseDate, String purchaseTime, String total, List<Item> items) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.total = total;
        this.items = items;
    }

    // Getters and setters
    public String getRetailer() { return retailer; }
    public void setRetailer(String retailer) { this.retailer = retailer; }
    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
    public String getPurchaseTime() { return purchaseTime; }
    public void setPurchaseTime(String purchaseTime) { this.purchaseTime = purchaseTime; }
    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
