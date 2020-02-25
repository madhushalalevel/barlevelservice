package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Inventory} entity.
 */
public class InventoryDTO implements Serializable {

    private Long id;

    private Integer productID;

    private Integer tennentID;

    private Integer branchID;

    private Integer zoneID;

    private Integer shelfID;

    private Integer currentStockCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getTennentID() {
        return tennentID;
    }

    public void setTennentID(Integer tennentID) {
        this.tennentID = tennentID;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public Integer getZoneID() {
        return zoneID;
    }

    public void setZoneID(Integer zoneID) {
        this.zoneID = zoneID;
    }

    public Integer getShelfID() {
        return shelfID;
    }

    public void setShelfID(Integer shelfID) {
        this.shelfID = shelfID;
    }

    public Integer getCurrentStockCount() {
        return currentStockCount;
    }

    public void setCurrentStockCount(Integer currentStockCount) {
        this.currentStockCount = currentStockCount;
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
            ", productID=" + getProductID() +
            ", tennentID=" + getTennentID() +
            ", branchID=" + getBranchID() +
            ", zoneID=" + getZoneID() +
            ", shelfID=" + getShelfID() +
            ", currentStockCount=" + getCurrentStockCount() +
            "}";
    }
}
