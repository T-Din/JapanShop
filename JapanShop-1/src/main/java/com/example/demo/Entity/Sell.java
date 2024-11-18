package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Sell {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer Id;
private Integer sellPrice;
private String sellTime;
private Integer sellQuantity;

@ManyToOne
@JoinColumn(name = "shohin_id")
private Shohin shohin;
}
