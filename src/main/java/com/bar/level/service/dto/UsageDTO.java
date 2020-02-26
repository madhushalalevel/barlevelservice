package com.bar.level.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bar.level.domain.Usage} entity.
 */
public class UsageDTO implements Serializable {

    private Long id;

    private Integer usageId;

    private Integer productID;

    private Integer branchID;

    private Integer zoneID;

    private Integer shelfID;

    private Integer usage;

    private ZonedDateTime datetime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUsageId() {
        return usageId;
    }

    public void setUsageId(Integer usageId) {
        this.usageId = usageId;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
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

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(ZonedDateTime datetime) {
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

        UsageDTO usageDTO = (UsageDTO) o;
        if (usageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsageDTO{" +
            "id=" + getId() +
            ", usageId=" + getUsageId() +
            ", productID=" + getProductID() +
            ", branchID=" + getBranchID() +
            ", zoneID=" + getZoneID() +
            ", shelfID=" + getShelfID() +
            ", usage=" + getUsage() +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
