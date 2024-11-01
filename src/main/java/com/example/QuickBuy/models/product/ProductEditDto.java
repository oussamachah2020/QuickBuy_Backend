package com.example.QuickBuy.models.product;

public class ProductEditDto {
    private String name;
    private String description;
    private Integer availableQuantity;
    private Boolean available;
    private Float unitPrice; // Move unitPrice here

    public ProductEditDto(String name, String description, Integer availableQuantity, Boolean available, Float unitPrice) {
        this.name = name;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.available = available;
        this.unitPrice = unitPrice;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }
}
