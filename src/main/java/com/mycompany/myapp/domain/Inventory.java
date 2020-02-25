package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @Column(name = "product_id")
    private Integer productID;

    @Column(name = "tennent_id")
    private Integer tennentID;

    @Column(name = "branch_id")
    private Integer branchID;

    @Column(name = "zone_id")
    private Integer zoneID;

    @Column(name = "shelf_id")
    private Integer shelfID;

    @Column(name = "current_stock_count")
    private Integer currentStockCount;

    @OneToMany(mappedBy = "inventory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Product> employees = new HashSet<>();

    @OneToMany(mappedBy = "inventory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Company> employees = new HashSet<>();

    @OneToMany(mappedBy = "inventory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Branch> employees = new HashSet<>();

    @OneToMany(mappedBy = "inventory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Zone> employees = new HashSet<>();

    @OneToMany(mappedBy = "inventory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Shelf> employees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductID() {
        return productID;
    }

    public Inventory productID(Integer productID) {
        this.productID = productID;
        return this;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
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

    public Integer getBranchID() {
        return branchID;
    }

    public Inventory branchID(Integer branchID) {
        this.branchID = branchID;
        return this;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public Integer getZoneID() {
        return zoneID;
    }

    public Inventory zoneID(Integer zoneID) {
        this.zoneID = zoneID;
        return this;
    }

    public void setZoneID(Integer zoneID) {
        this.zoneID = zoneID;
    }

    public Integer getShelfID() {
        return shelfID;
    }

    public Inventory shelfID(Integer shelfID) {
        this.shelfID = shelfID;
        return this;
    }

    public void setShelfID(Integer shelfID) {
        this.shelfID = shelfID;
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

    public Set<Product> getEmployees() {
        return employees;
    }

    public Inventory employees(Set<Product> products) {
        this.employees = products;
        return this;
    }

    public Inventory addEmployee(Product product) {
        this.employees.add(product);
        product.setInventory(this);
        return this;
    }

    public Inventory removeEmployee(Product product) {
        this.employees.remove(product);
        product.setInventory(null);
        return this;
    }

    public void setEmployees(Set<Product> products) {
        this.employees = products;
    }

    public Set<Company> getEmployees() {
        return employees;
    }

    public Inventory employees(Set<Company> companies) {
        this.employees = companies;
        return this;
    }

    public Inventory addEmployee(Company company) {
        this.employees.add(company);
        company.setInventory(this);
        return this;
    }

    public Inventory removeEmployee(Company company) {
        this.employees.remove(company);
        company.setInventory(null);
        return this;
    }

    public void setEmployees(Set<Company> companies) {
        this.employees = companies;
    }

    public Set<Branch> getEmployees() {
        return employees;
    }

    public Inventory employees(Set<Branch> branches) {
        this.employees = branches;
        return this;
    }

    public Inventory addEmployee(Branch branch) {
        this.employees.add(branch);
        branch.setInventory(this);
        return this;
    }

    public Inventory removeEmployee(Branch branch) {
        this.employees.remove(branch);
        branch.setInventory(null);
        return this;
    }

    public void setEmployees(Set<Branch> branches) {
        this.employees = branches;
    }

    public Set<Zone> getEmployees() {
        return employees;
    }

    public Inventory employees(Set<Zone> zones) {
        this.employees = zones;
        return this;
    }

    public Inventory addEmployee(Zone zone) {
        this.employees.add(zone);
        zone.setInventory(this);
        return this;
    }

    public Inventory removeEmployee(Zone zone) {
        this.employees.remove(zone);
        zone.setInventory(null);
        return this;
    }

    public void setEmployees(Set<Zone> zones) {
        this.employees = zones;
    }

    public Set<Shelf> getEmployees() {
        return employees;
    }

    public Inventory employees(Set<Shelf> shelves) {
        this.employees = shelves;
        return this;
    }

    public Inventory addEmployee(Shelf shelf) {
        this.employees.add(shelf);
        shelf.setInventory(this);
        return this;
    }

    public Inventory removeEmployee(Shelf shelf) {
        this.employees.remove(shelf);
        shelf.setInventory(null);
        return this;
    }

    public void setEmployees(Set<Shelf> shelves) {
        this.employees = shelves;
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
            ", productID=" + getProductID() +
            ", tennentID=" + getTennentID() +
            ", branchID=" + getBranchID() +
            ", zoneID=" + getZoneID() +
            ", shelfID=" + getShelfID() +
            ", currentStockCount=" + getCurrentStockCount() +
            "}";
    }
}
