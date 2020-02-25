package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.InventoryStock} entity.
 */
public class InventoryStockDTO implements Serializable {

    private Long id;

    private Integer inventoryId;

    private Integer productID;

    private Integer stockCount;

    private Long datetime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventoryStockDTO inventoryStockDTO = (InventoryStockDTO) o;
        if (inventoryStockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventoryStockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventoryStockDTO{" +
            "id=" + getId() +
            ", inventoryId=" + getInventoryId() +
            ", productID=" + getProductID() +
            ", stockCount=" + getStockCount() +
            ", datetime=" + getDatetime() +
            "}";
    }
}
