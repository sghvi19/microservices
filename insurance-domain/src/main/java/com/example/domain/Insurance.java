package com.example.domain;

import com.example.domain.enums.FranchiseOrUnlimited;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
public class Insurance {
    @NotNull
    @Column(name = "insurance_amount", nullable = false)
    private BigDecimal insuranceAmount;


    @Column(name = "insurance_pension", insertable = false)
    private BigDecimal insurancePension;

    @NotNull
    @Column(name = "franchised_or_Unlimited", nullable = false)
    private FranchiseOrUnlimited franchiseOrUnlimited;

    @Column(name = "franchise_amount")
    private BigDecimal franchisedAmount;
}
