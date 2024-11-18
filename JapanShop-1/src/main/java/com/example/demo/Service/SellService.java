package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Sell;
import com.example.demo.Repository.SellRepository;
@Service
public class SellService {
	@Autowired
	private SellRepository sellRepository;

	public Sell saveAndFlush(Sell sell) {
		return sellRepository.saveAndFlush(sell);

	}

	public List<Sell> getAllSellByShohinId(Integer shohinId) {
		return sellRepository.findByShohinId(shohinId);
	}

	@Transactional
	public void deleteSell(Integer id) {
		sellRepository.deleteById(id);
	}
	public Optional<Sell> getSellById(Integer id) {
		return sellRepository.findById(id);
	}
}
