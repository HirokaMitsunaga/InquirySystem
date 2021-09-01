package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Inquiry;

public interface InquiryDao {

	void insertInquiry(Inquiry inquiry);
	
	List<Inquiry>getAll();
	
	Optional<Inquiry> findById(int id);
	
	int updateInquiry(Inquiry inquiry);
	
	int deleteByIdInquiry(int id);

	List<Inquiry> findByInquiry(int inquiryId);
	
	
}
