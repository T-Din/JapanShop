package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Shohin;

public interface ShohinRepository extends JpaRepository<Shohin, Integer> {
	public List<Shohin> findByHinmeiLike(String key);

}
