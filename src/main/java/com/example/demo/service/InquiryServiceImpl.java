package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.InquiryDao;
import com.example.demo.entity.Inquiry;


@Service
public class InquiryServiceImpl implements InquiryService {

	//Interfaceの型で宣言する。
	private final InquiryDao dao;
	
	@Autowired public InquiryServiceImpl(InquiryDao dao) {
		this.dao=dao;
	}
	
	@Override
	public void insert(Inquiry inquiry) {
		dao.insertInquiry(inquiry);
	}

	@Override
	public List<Inquiry> getAll() {
		return dao.getAll();
		
	}

	@Override
	public void update(Inquiry inquiry) {
		
		if(dao.updateInquiry(inquiry)==0){
			throw new InquiryNotFoundException("指定された問い合わせが存在しません");
		}
	}

	@Override
	public void delete(int id) {
		if(dao.deleteByIdInquiry(id) == 0) {
			throw new InquiryNotFoundException("削除するタスクが存在しません");
		}
	}

	@Override
	public Optional<Inquiry> getInquiry(int id) {

		try {
			return dao.findById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new InquiryNotFoundException("指定された問い合わせが存在しません");
		}
	}


}
