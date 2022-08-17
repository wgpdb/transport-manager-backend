package com.wgpdb.transportmanager.controller;

import com.wgpdb.transportmanager.domain.Vendor;
import com.wgpdb.transportmanager.domain.dto.VendorDto;
import com.wgpdb.transportmanager.exception.VendorNotFoundException;
import com.wgpdb.transportmanager.mapper.VendorMapper;
import com.wgpdb.transportmanager.service.dbservice.VendorDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorDbService vendorDbService;
    private final VendorMapper vendorMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<VendorDto> getVendor(@PathVariable Long id) throws VendorNotFoundException {
        return ResponseEntity.ok(vendorMapper.INSTANCE.mapToVendorDto(vendorDbService.getVendor(id)));
    }

    @GetMapping
    public ResponseEntity<List<VendorDto>> getAllVendors() {
        List<Vendor> vendors = vendorDbService.getAllVendors();
        return ResponseEntity.ok(vendorMapper.INSTANCE.mapToVendorDtoList(vendors));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addVendor(@RequestBody VendorDto vendorDto) {
        Vendor vendor = vendorMapper.INSTANCE.mapToVendor(vendorDto);
        vendorDbService.saveVendor(vendor);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VendorDto> updateVendor(@RequestBody VendorDto vendorDto) throws VendorNotFoundException {
        Vendor vendor = vendorMapper.INSTANCE.mapToVendor(vendorDto);
        Vendor savedVendor = vendorDbService.updateVendor(vendor);
        return ResponseEntity.ok(vendorMapper.INSTANCE.mapToVendorDto(savedVendor));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) throws VendorNotFoundException {
        vendorDbService.deleteVendor(id);
        return ResponseEntity.ok().build();
    }
}