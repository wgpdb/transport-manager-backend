package com.wgpdb.transportmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tax_id")
    private Long taxId;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(
            targetEntity = Expense.class,
            mappedBy = "vendor",
            fetch = FetchType.LAZY
    )
    private List<Expense> expenses;
}