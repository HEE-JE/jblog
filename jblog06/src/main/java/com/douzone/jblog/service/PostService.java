package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	public List<PostVo> getPosts(Long categoryNo) {
		return postRepository.findAllByNo(categoryNo);
	}

	public PostVo getPostOne(Long categoryNo, Long postNo) {
		return postRepository.findOneByNo(categoryNo, postNo);
	}

	public void addPost(String id, PostVo postVo) {
		postRepository.insertPost(id, postVo);
	}
}