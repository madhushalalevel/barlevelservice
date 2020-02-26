package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, CompanyMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "company.id", target = "companyId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "companyId", target = "company")
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
