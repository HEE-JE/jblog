package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;

	public List<CategoryVo> findAllById(String id) {
		return sqlSession.selectList("category.findAllById", id);
	}

	public CategoryVo findOneById(String id) {
		return sqlSession.selectOne("category.findOneById", id);
	}

	public boolean insertCategory(CategoryVo categoryVo) {
		return sqlSession.insert("category.insertCategory", categoryVo) == 1;
	}

	public boolean deleteCategory(String id, Long no) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("no", no);
		return sqlSession.delete("category.deleteCategory", map) == 1;
	}
}