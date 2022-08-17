package com.wgpdb.transportmanager.domain;

import com.wgpdb.transportmanager.domain.enumerantion.VatRate;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "expense_items")
public class ExpenseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "net_value")
    private BigDecimal netValue;

    @Column(name = "vat_rate")
    private VatRate vatRate;

    @Column(name = "gross_value")
    private BigDecimal grossValue;
}