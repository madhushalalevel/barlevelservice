package com.mycompany.myapp.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    private String name;

    private Integer phoneNumber;

    private String email;


    private Long companyId;

    private Long addressId;

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

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", email='" + getEmail() + "'" +
            ", company=" + getCompanyId() +
            ", address=" + getAddressId() +
            "}";
    }
}
