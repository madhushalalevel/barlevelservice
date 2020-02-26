package com.bar.level.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bar.level.domain.Inventory} entity.
 */
public class InventoryDTO implements Serializable {

    private Long id;

    private Integer tennentID;

    private Integer currentStockCount;


    private Long productId;

    private Long branchId;

    private Long zoneId;

    private Long shelfId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTennentID() {
        return tennentID;
    }

    public void setTennentID(Integer tennentID) {
        this.tennentID = tennentID;
    }

    public Integer getCurrentStockCount() {
        return currentStockCount;
    }

    public void setCurrentStockCount(Integer currentStockCount) {
        this.currentStockCount = currentStockCount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventoryDTO inventoryDTO = (InventoryDTO) o;
        if (inventoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventoryDTO{" +
            "id=" + getId() +
            ", tennentID=" + getTennentID() +
            ", currentStockCount=" + getCurrentStockCount() +
            ", productId=" + getProductId() +
            ", branchId=" + getBranchId() +
            ", zoneId=" + getZoneId() +
            ", shelfId=" + getShelfId() +
            "}";
    }
}
