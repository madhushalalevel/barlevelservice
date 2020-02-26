package com.bar.level.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bar.level.domain.Shelf} entity.
 */
public class ShelfDTO implements Serializable {

    private Long id;

    private String name;

    private String discription;

    private String tenantId;


    private Long zoneId;

    private String zoneName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShelfDTO shelfDTO = (ShelfDTO) o;
        if (shelfDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shelfDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShelfDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", discription='" + getDiscription() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", zoneId=" + getZoneId() +
            ", zoneName='" + getZoneName() + "'" +
            "}";
    }
}
