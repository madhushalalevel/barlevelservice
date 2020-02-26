package com.bar.level.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tennent_id")
    private Integer tennentID;

    @Column(name = "current_stock_count")
    private Integer currentStockCount;

    @OneToOne
    @JoinColumn(unique = true)
    private Product product;

    @OneToOne(mappedBy = "inventory")
    @JsonIgnore
    private InventoryStock inventoryStock;

    @ManyToOne
    @JsonIgnoreProperties("inventories")
    private Branch branch;

    @ManyToOne
    @JsonIgnoreProperties("inventories")
    private Zone zone;

    @ManyToOne
    @JsonIgnoreProperties("inventories")
    private Shelf shelf;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTennentID() {
        return tennentID;
    }

    public Inventory tennentID(Integer tennentID) {
        this.tennentID = tennentID;
        return this;
    }

    public void setTennentID(Integer tennentID) {
        this.tennentID = tennentID;
    }

    public Integer getCurrentStockCount() {
        return currentStockCount;
    }

    public Inventory currentStockCount(Integer currentStockCount) {
        this.currentStockCount = currentStockCount;
        return this;
    }

    public void setCurrentStockCount(Integer currentStockCount) {
        this.currentStockCount = currentStockCount;
    }

    public Product getProduct() {
        return product;
    }

    public Inventory product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public InventoryStock getInventoryStock() {
        return inventoryStock;
    }

    public Inventory inventoryStock(InventoryStock inventoryStock) {
        this.inventoryStock = inventoryStock;
        return this;
    }

    public void setInventoryStock(InventoryStock inventoryStock) {
        this.inventoryStock = inventoryStock;
    }

    public Branch getBranch() {
        return branch;
    }

    public Inventory branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Zone getZone() {
        return zone;
    }

    public Inventory zone(Zone zone) {
        this.zone = zone;
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public Inventory shelf(Shelf shelf) {
        this.shelf = shelf;
        return this;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", tennentID=" + getTennentID() +
            ", currentStockCount=" + getCurrentStockCount() +
            "}";
    }
}
