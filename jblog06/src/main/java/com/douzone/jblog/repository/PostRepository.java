package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> findAllByNo(Long categoryNo) {
		return sqlSession.selectList("post.findAllByNo", categoryNo);
	}

	public PostVo findOneByNo(Long categoryNo, Long postNo) {
		Map<String, Long> map = new HashMap<>();
		map.put("categoryNo", categoryNo);
		map.put("postNo", postNo);
		return sqlSession.selectOne("post.findOneByNo", map);
	}

	public void insertPost(String id, PostVo postVo) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("postVo", postVo);
		sqlSession.insert("post.insertPost", map);
	}
}