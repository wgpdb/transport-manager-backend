package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.Vendor;
import com.wgpdb.transportmanager.exception.VendorNotFoundException;
import com.wgpdb.transportmanager.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorDbService {

    private final VendorRepository vendorRepository;

    public Vendor getVendor(final Long id) throws VendorNotFoundException {
        return vendorRepository.findById(id).orElseThrow(VendorNotFoundException::new);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor saveVendor(final Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor updateVendor(final Vendor vendor) throws VendorNotFoundException {
        if (vendorRepository.existsById(vendor.getId())) {
            return vendorRepository.save(vendor);
        } else {
            throw new VendorNotFoundException();
        }
    }

    public void deleteVendor(final Long id) throws VendorNotFoundException {
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
        } else {
            throw new VendorNotFoundException();
        }
    }
}