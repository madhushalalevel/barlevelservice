package com.mycompany.myapp.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.BarCodeType;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    /**
     * fieldName
     */
    @ApiModelProperty(value = "fieldName")
    private Integer productID;

    private String name;

    private String barCode;

    private Integer quantity;

    private Double volume;

    private String type;

    private String subType;

    private Double price;

    private String containerType;

    private BarCodeType barCodeType;

    private String tenantId;


    private Long inventoryId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public BarCodeType getBarCodeType() {
        return barCodeType;
    }

    public void setBarCodeType(BarCodeType barCodeType) {
        this.barCodeType = barCodeType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
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
            ", inventory=" + getInventoryId() +
            "}";
    }
}
