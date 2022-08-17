package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Vendor;
import com.wgpdb.transportmanager.domain.dto.VendorDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

    VendorDto mapToVendorDto(Vendor vendor);
    Vendor mapToVendor(VendorDto vendorDto);
    List<VendorDto> mapToVendorDtoList(List<Vendor> vendors);
}