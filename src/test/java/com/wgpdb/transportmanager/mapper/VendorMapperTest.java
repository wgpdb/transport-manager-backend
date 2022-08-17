package com.wgpdb.transportmanager.mapper;

import com.wgpdb.transportmanager.domain.Vendor;
import com.wgpdb.transportmanager.domain.dto.VendorDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {

    @Test
    void mapToVendorDtoTest() {
        //Given
        Vendor vendor = Vendor.builder()
                .taxId(1234567899L)
                .name("test name")
                .build();

        //When
        VendorDto vendorDto = VendorMapper.INSTANCE.mapToVendorDto(vendor);

        //Then
        assertNotNull(vendorDto);
        assertEquals(1234567899L, vendorDto.getTaxId());
        assertEquals("test name", vendorDto.getName());
    }

    @Test
    void mapToVendorTest() {
        //Given
        VendorDto vendorDto = VendorDto.builder()
                .taxId(1122334455L)
                .name("test name")
                .build();

        //When
        Vendor vendor = VendorMapper.INSTANCE.mapToVendor(vendorDto);

        //Then
        assertNotNull(vendorDto);
        assertEquals(1122334455L, vendor.getTaxId());
        assertEquals("test name", vendor.getName());
    }

    @Test
    void mapToVendorDtoListTest() {
        //Given
        Vendor vendor = Vendor.builder()
                .taxId(1234567899L)
                .name("test name")
                .build();

        List<Vendor> vendorList = new ArrayList<>();
        vendorList.add(vendor);

        //When
        List<VendorDto> vendorDtoList = VendorMapper.INSTANCE.mapToVendorDtoList(vendorList);

        //Then
        assertNotNull(vendorDtoList);
        assertEquals(1234567899L, vendorDtoList.get(0).getTaxId());
        assertEquals("test name", vendorDtoList.get(0).getName());
    }
}