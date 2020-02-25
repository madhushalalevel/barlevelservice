package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A InventoryStock.
 */
@Entity
@Table(name = "inventory_stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InventoryStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "inventory_id")
    private Integer inventoryId;

    @Column(name = "product_id")
    private Integer productID;

    @Column(name = "stock_count")
    private Integer stockCount;

    @Column(name = "datetime")
    private Long datetime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public InventoryStock inventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
        return this;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getProductID() {
        return productID;
    }

    public InventoryStock productID(Integer productID) {
        this.productID = productID;
        return this;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public InventoryStock stockCount(Integer stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Long getDatetime() {
        return datetime;
    }

    public InventoryStock datetime(Long datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryStock)) {
            return false;
        }
        return id != null && id.equals(((InventoryStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InventoryStock{" +
            "id=" + getId() +
            ", inventoryId=" + getInventoryId() +
            ", productID=" + getProductID() +
            ", stockCount=" + getStockCount() +
            ", datetime=" + getDatetime() +
            "}";
    }
}
