package com.fetch.receiptprocessor.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class Item {
    @NotBlank(message = "Short description must not be null or empty.")
    private String shortDescription;

    @NotNull(message = "Price must not be null.")
    @PositiveOrZero(message = "Price must be a non-negative value.")
    private String price;


    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    // Getters and setters
    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
}
