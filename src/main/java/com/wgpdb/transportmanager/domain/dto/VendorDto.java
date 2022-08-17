package com.wgpdb.transportmanager.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VendorDto {

    private Long id;
    private Long taxId;
    private String name;
}