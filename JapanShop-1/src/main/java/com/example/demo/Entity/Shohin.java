package com.example.demo.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Shohin {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;
private String hinmei;
private Integer genka;
private String memo;

@OneToMany(mappedBy = "shohin")
private List<Buy> buy;
@OneToMany(mappedBy = "shohin")
private List<Sell> sell;

public Integer zaiko() { 
int totalBuy = buy.stream().mapToInt(Buy::getBuyQuantity).sum();
int totalSell = sell.stream().mapToInt(Sell::getSellQuantity).sum();
return totalBuy - totalSell;
}
}
