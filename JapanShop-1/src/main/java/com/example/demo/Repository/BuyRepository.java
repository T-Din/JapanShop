package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Buy;


public interface BuyRepository extends JpaRepository<Buy, Integer> {

	List<Buy> findByShohinId(Integer shohinId);
}
