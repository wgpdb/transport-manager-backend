package com.wgpdb.transportmanager.service.dbservice;

import com.wgpdb.transportmanager.domain.Vendor;
import com.wgpdb.transportmanager.exception.VendorNotFoundException;
import com.wgpdb.transportmanager.repository.VendorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class VendorDbServiceTest {

    @Autowired
    private VendorDbService vendorDbService;

    @Autowired
    private VendorRepository vendorRepository;

    @Container
    private static MySQLContainer container = new MySQLContainer("mysql:latest");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @AfterEach
    void cleanup() {
        vendorRepository.deleteAll();
    }

    @Test
    void getVendorTest() throws VendorNotFoundException {
        //Given
        Vendor vendor = Vendor.builder()
                .taxId(5544332211L)
                .name("test name")
                .build();

        //When
        vendorDbService.saveVendor(vendor);
        Long id = vendor.getId();

        //Then
        assertTrue(vendorRepository.existsById(id));
        assertEquals(5544332211L, vendorDbService.getVendor(id).getTaxId());
        assertEquals("test name", vendorDbService.getVendor(id).getName());
    }

    @Test
    void getNonExistentVendorThrowsVendorNotFoundExceptionTest() {
        //Given
        Vendor vendor = Vendor.builder().build();

        //When
        vendorDbService.saveVendor(vendor);
        Long id = vendor.getId();
        Long nonExistentId = id + 1;

        //Then
        assertTrue(vendorRepository.existsById(id));
        assertThrows(VendorNotFoundException.class, () -> vendorDbService.getVendor(nonExistentId));
    }

    @Test
    void getAllVendorsTest() {
        //Given
        Vendor vendorOne = Vendor.builder().build();
        Vendor vendorTwo = Vendor.builder().build();

        //When
        vendorDbService.saveVendor(vendorOne);
        vendorDbService.saveVendor(vendorTwo);

        //Then
        assertEquals(2, vendorDbService.getAllVendors().size());
    }

    @Test
    void updateVendorTest() throws VendorNotFoundException {
        //Given
        Vendor vendor = Vendor.builder()
                .taxId(5544332211L)
                .name("test name")
                .build();

        vendorDbService.saveVendor(vendor);
        Long id = vendor.getId();

        //When
        Vendor updatedVendor = Vendor.builder()
                .id(id)
                .taxId(1122334455L)
                .name("updated name")
                .build();

        vendorDbService.updateVendor(updatedVendor);

        //Then
        assertEquals(1122334455L, vendorDbService.getVendor(id).getTaxId());
        assertEquals("updated name", vendorDbService.getVendor(id).getName());
    }

    @Test
    void deleteVendorTest() throws VendorNotFoundException {
        //Given
        Vendor vendor = Vendor.builder()
                .taxId(5544332211L)
                .name("test name")
                .build();

        vendorDbService.saveVendor(vendor);
        Long id = vendor.getId();

        //When
        vendorDbService.deleteVendor(id);

        //Then
        assertFalse(vendorRepository.existsById(id));
    }
}