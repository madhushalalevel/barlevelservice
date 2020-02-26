package com.bar.level.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Usage.
 */
@Entity
@Table(name = "usage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Usage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "usage_id")
    private Integer usageId;

    @Column(name = "product_id")
    private Integer productID;

    @Column(name = "branch_id")
    private Integer branchID;

    @Column(name = "zone_id")
    private Integer zoneID;

    @Column(name = "shelf_id")
    private Integer shelfID;

    @Column(name = "usage")
    private Integer usage;

    @Column(name = "datetime")
    private ZonedDateTime datetime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUsageId() {
        return usageId;
    }

    public Usage usageId(Integer usageId) {
        this.usageId = usageId;
        return this;
    }

    public void setUsageId(Integer usageId) {
        this.usageId = usageId;
    }

    public Integer getProductID() {
        return productID;
    }

    public Usage productID(Integer productID) {
        this.productID = productID;
        return this;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public Usage branchID(Integer branchID) {
        this.branchID = branchID;
        return this;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public Integer getZoneID() {
        return zoneID;
    }

    public Usage zoneID(Integer zoneID) {
        this.zoneID = zoneID;
        return this;
    }

    public void setZoneID(Integer zoneID) {
        this.zoneID = zoneID;
    }

    public Integer getShelfID() {
        return shelfID;
    }

    public Usage shelfID(Integer shelfID) {
        this.shelfID = shelfID;
        return this;
    }

    public void setShelfID(Integer shelfID) {
        this.shelfID = shelfID;
    }

    public Integer getUsage() {
        return usage;
    }

    public Usage usage(Integer usage) {
        this.usage = usage;
        return this;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public Usage datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usage)) {
            return false;
        }
        return id != null && id.equals(((Usage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Usage{" +
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
