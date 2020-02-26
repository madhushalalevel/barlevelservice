package com.bar.level.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bar.level.domain.InventoryStock} entity.
 */
public class InventoryStockDTO implements Serializable {

    private Long id;

    private Integer stockCount;

    private ZonedDateTime datetime;


    private Long inventoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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
            ", stockCount=" + getStockCount() +
            ", datetime='" + getDatetime() + "'" +
            ", inventoryId=" + getInventoryId() +
            "}";
    }
}
