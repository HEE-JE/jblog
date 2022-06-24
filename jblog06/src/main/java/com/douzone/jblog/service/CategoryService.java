package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryVo> getCategories(String id) {
		return categoryRepository.findAllById(id);
	}

	public CategoryVo getCategoryOne(String id) {
		return categoryRepository.findOneById(id);
	}

	public void addCategory(CategoryVo categoryVo) {
		categoryRepository.insertCategory(categoryVo);
	}

	public void removeCategory(String id, Long no) {
		categoryRepository.deleteCategory(id, no);
	}
}