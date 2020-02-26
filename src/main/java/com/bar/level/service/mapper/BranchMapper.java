package com.bar.level.service.mapper;


import com.bar.level.domain.*;
import com.bar.level.service.dto.BranchDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, CompanyMapper.class})
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    BranchDTO toDto(Branch branch);

    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "removeInventory", ignore = true)
    @Mapping(source = "companyId", target = "company")
    Branch toEntity(BranchDTO branchDTO);

    default Branch fromId(Long id) {
        if (id == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(id);
        return branch;
    }
}
