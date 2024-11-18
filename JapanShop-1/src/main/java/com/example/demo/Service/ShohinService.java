package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Shohin;
import com.example.demo.Repository.ShohinRepository;
@Service
public class ShohinService {

	@Autowired
    private ShohinRepository shohinRepository;

    public Shohin getbyId(Integer shohinId) {
        return shohinRepository.findById(shohinId).orElse(null);
    }


	public List<Shohin> findAll() {
		
		return shohinRepository.findAll();
	}

	public Shohin saveAndFlush(Shohin shohin) {
		return shohinRepository.saveAndFlush(shohin);
		
	}

}
