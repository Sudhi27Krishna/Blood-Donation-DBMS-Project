package com.bloodbank.backend.model;

import com.bloodbank.backend.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "request_inventory",  joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id", referencedColumnName = "bloodType")
    )
    private List<BloodInventory> inventory;
}
