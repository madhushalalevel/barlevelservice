package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProductVariousPositions.
 */
@Entity
@Table(name = "product_various_positions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductVariousPositions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "x_axis")
    private Integer xAxis;

    @Column(name = "y_axis")
    private Integer yAxis;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    @JsonIgnoreProperties("productVariousPositions")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getxAxis() {
        return xAxis;
    }

    public ProductVariousPositions xAxis(Integer xAxis) {
        this.xAxis = xAxis;
        return this;
    }

    public void setxAxis(Integer xAxis) {
        this.xAxis = xAxis;
    }

    public Integer getyAxis() {
        return yAxis;
    }

    public ProductVariousPositions yAxis(Integer yAxis) {
        this.yAxis = yAxis;
        return this;
    }

    public void setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
    }

    public Integer getOrder() {
        return order;
    }

    public ProductVariousPositions order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public ProductVariousPositions product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductVariousPositions)) {
            return false;
        }
        return id != null && id.equals(((ProductVariousPositions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductVariousPositions{" +
            "id=" + getId() +
            ", xAxis=" + getxAxis() +
            ", yAxis=" + getyAxis() +
            ", order=" + getOrder() +
            "}";
    }
}
