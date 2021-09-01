package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Inquiry;


public interface InquiryService {

	void insert(Inquiry inquiry);
	
	List<Inquiry>getAll();
	
	Optional<Inquiry> getInquiry(int id);
	
	void update(Inquiry inquiry);
	
	void delete(int id);
	
	
}
