package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Sell;

public interface SellRepository extends JpaRepository<Sell, Integer>{

	List<Sell> findByShohinId(Integer shohinId);
}
