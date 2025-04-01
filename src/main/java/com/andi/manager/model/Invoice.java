package com.andi.manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private LocalDate date;


    @ElementCollection
    private List<Item> items;
    private String logoPath;

    @Embeddable
    @Data
    public static class Item{
        private String name;
        private int quantity;
        private double price;

        public double getSubtotal(){
            return quantity * price;
        }
    }
}
