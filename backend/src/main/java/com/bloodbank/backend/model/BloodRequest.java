package com.bloodbank.backend.model;

import com.bloodbank.backend.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BloodRequest {
    @Id
    @GeneratedValue
    private Long id;
    private String bloodType;
    private LocalDateTime date;
    private Integer qty;
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private BloodInventory inventory;
}
