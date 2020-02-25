package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.BarCodeType;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * fieldName
     */
    @Column(name = "product_id")
    private Integer productID;

    @Column(name = "name")
    private String name;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "price")
    private Double price;

    @Column(name = "container_type")
    private String containerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "bar_code_type")
    private BarCodeType barCodeType;

    @Column(name = "tenant_id")
    private String tenantId;

    @OneToMany(mappedBy = "product")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductPositions> productPositions = new HashSet<>();

    @OneToOne(mappedBy = "product")
    @JsonIgnore
    private ProductImage productImage;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Inventory inventory;

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

    public Product productID(Integer productID) {
        this.productID = productID;
        return this;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public Product barCode(String barCode) {
        this.barCode = barCode;
        return this;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getVolume() {
        return volume;
    }

    public Product volume(Double volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getType() {
        return type;
    }

    public Product type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public Product subType(String subType) {
        this.subType = subType;
        return this;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getContainerType() {
        return containerType;
    }

    public Product containerType(String containerType) {
        this.containerType = containerType;
        return this;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public BarCodeType getBarCodeType() {
        return barCodeType;
    }

    public Product barCodeType(BarCodeType barCodeType) {
        this.barCodeType = barCodeType;
        return this;
    }

    public void setBarCodeType(BarCodeType barCodeType) {
        this.barCodeType = barCodeType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Product tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Set<ProductPositions> getProductPositions() {
        return productPositions;
    }

    public Product productPositions(Set<ProductPositions> productPositions) {
        this.productPositions = productPositions;
        return this;
    }

    public Product addProductPositions(ProductPositions productPositions) {
        this.productPositions.add(productPositions);
        productPositions.setProduct(this);
        return this;
    }

    public Product removeProductPositions(ProductPositions productPositions) {
        this.productPositions.remove(productPositions);
        productPositions.setProduct(null);
        return this;
    }

    public void setProductPositions(Set<ProductPositions> productPositions) {
        this.productPositions = productPositions;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public Product productImage(ProductImage productImage) {
        this.productImage = productImage;
        return this;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Product inventory(Inventory inventory) {
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
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productID=" + getProductID() +
            ", name='" + getName() + "'" +
            ", barCode='" + getBarCode() + "'" +
            ", quantity=" + getQuantity() +
            ", volume=" + getVolume() +
            ", type='" + getType() + "'" +
            ", subType='" + getSubType() + "'" +
            ", price=" + getPrice() +
            ", containerType='" + getContainerType() + "'" +
            ", barCodeType='" + getBarCodeType() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
