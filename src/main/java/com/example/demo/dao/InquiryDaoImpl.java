package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Inquiry;

@Repository
public class InquiryDaoImpl implements InquiryDao {

	private final JdbcTemplate jdbctemplate;
	
	@Autowired
	public InquiryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbctemplate=jdbcTemplate;

	}
	
	@Override
	public void insertInquiry(Inquiry inquiry) {
		jdbctemplate.update("INSERT INTO inquiry(name,email,contents,created)VALUES(?,?,?,?)",
		inquiry.getName(),inquiry.getEmail(),inquiry.getContents(),inquiry.getCreated());

	}

	@Override
	public List<Inquiry> getAll() {
		String sql="SELECT id,name,email,contents,created FROM inquiry";
		List<Map<String,Object>>resultList=jdbctemplate.queryForList(sql);
		List<Inquiry>list=new ArrayList<Inquiry>();
		for(Map<String,Object> result:resultList) {
			Inquiry inquiry=new Inquiry();
			inquiry.setId((int)result.get("id"));
			inquiry.setName((String)result.get("name"));
			inquiry.setEmail((String)result.get("email"));
			inquiry.setContents((String)result.get("contents"));
			inquiry.setCreated(((LocalDateTime)result.get("created")));
			list.add(inquiry);
		}
		return list;
	}

	@Override
	public int updateInquiry(Inquiry inquiry) {
		return jdbctemplate.update("UPDATE inquiry SET name=?,email=?,contents=?,created=? WHERE id=?",
		inquiry.getName(),inquiry.getEmail(),inquiry.getContents(),inquiry.getCreated(),inquiry.getId());
	}

	@Override
	public int deleteByIdInquiry(int id) {
		return jdbctemplate.update("DELETE FROM inquiry WHERE id=?",id);
	}

	@Override
	public Optional<Inquiry> findById(int id) {
		String sql="SELECT id,name,email,contents,created FROM inquiry WHERE id=?";
		Map<String, Object> result = jdbctemplate.queryForMap(sql, id);
		Inquiry inquiry=new Inquiry();
		
		inquiry.setId((int)result.get("id"));
		inquiry.setName((String)result.get("name"));
		inquiry.setEmail((String)result.get("email"));
		inquiry.setContents((String)result.get("contents"));
		inquiry.setCreated(((LocalDateTime)result.get("created")));
		
		//InquiryをOptionalでラップする
		Optional<Inquiry>inquiryOpt=Optional.ofNullable(inquiry);
		
		return inquiryOpt;
	}

	@Override
	public List<Inquiry> findByInquiry(int inquiryId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
