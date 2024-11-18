package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Buy;
import com.example.demo.Repository.BuyRepository;

@Service
public class BuyService {
	@Autowired
	BuyRepository buyRepository;
	
	public Buy saveAndFlush(Buy buy) {
		return buyRepository.saveAndFlush(buy);

	}

	public List<Buy> getAllBuyByShohinId(Integer shohinId) {
		return buyRepository.findByShohinId(shohinId);
	}
	

	@Transactional
	public void deleteBuy(Integer id) {
		buyRepository.deleteById(id);
	}

	public Optional<Buy> getBuyById(Integer id) {
		return buyRepository.findById(id);
	}
}
