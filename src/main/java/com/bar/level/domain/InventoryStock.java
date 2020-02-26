package com.bar.level.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

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

    @Column(name = "stock_count")
    private Integer stockCount;

    @Column(name = "datetime")
    private ZonedDateTime datetime;

    @OneToOne
    @JoinColumn(unique = true)
    private Inventory inventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public InventoryStock datetime(ZonedDateTime datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public InventoryStock inventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
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
            ", stockCount=" + getStockCount() +
            ", datetime='" + getDatetime() + "'" +
            "}";
    }
}
