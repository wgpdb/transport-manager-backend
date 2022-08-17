package com.wgpdb.transportmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wgpdb.transportmanager.domain.enumerantion.Currency;
import com.wgpdb.transportmanager.domain.enumerantion.PaymentType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "expense_date")
    private LocalDate expenseDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @JsonIgnore
    @OneToMany(
            targetEntity = ExpenseItem.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "expense_id", referencedColumnName = "id")
    private List<ExpenseItem> expenseItems;

    @Column(name = "total_net_value")
    private BigDecimal totalNetValue;

    @Column(name = "total_gross_value")
    private BigDecimal totalGrossValue;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "payment_type")
    private PaymentType paymentType;
}