package com.douzone.jblog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.FileUploadService;

@Controller

@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping({ "", "/{pathNo1}", "/{pathNo1}/{pathNo2}" })
	public String index(@PathVariable("id") String id, @PathVariable("pathNo1") Optional<Long> pathNo1,
			@PathVariable("pathNo2") Optional<Long> pathNo2, Model model) {
		Long categoryNo = 0L;
		Long postNo = 0L;

		if (pathNo2.isPresent()) { // category, post 2개 다 있을 경우
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if (pathNo1.isPresent()) { // category만 있을 경우
			categoryNo = pathNo1.get();
		} else { // 2개 다 없을 경우 (첫 페이지)
			categoryNo = categoryService.getCategoryOne(id).getNo();
		}

		model.addAttribute("categoryList", categoryService.getCategories(id));
		model.addAttribute("postList", postService.getPosts(categoryNo));
		model.addAttribute("postOne", postService.getPostOne(categoryNo, postNo));
		model.addAttribute("blog", blogService.getBlog(id));
		model.addAttribute("id", id);
		return "blog/main";
	}

	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, @AuthUser UserVo authUser, Model model) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		model.addAttribute("blog", blogService.getBlog(id));
		return "blog/admin/basic";
	}

	@Auth
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public String blogmodify(@PathVariable("id") String id, @AuthUser UserVo authUser,
			@RequestParam("logo-file") MultipartFile multipartfile, BlogVo blogVo) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		String url = fileUploadService.restoreImage(multipartfile);
		blogVo.setLogo(url);
		blogVo.setId(id);
		blogService.modify(blogVo);
		return "redirect:/" + id + "/admin/basic";
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, @AuthUser UserVo authUser, Model model) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		model.addAttribute("blog", blogService.getBlog(id));
		model.addAttribute("categoryList", categoryService.getCategories(id));
		return "blog/admin/category";
	}

	@Auth
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String addCategory(@PathVariable("id") String id, @AuthUser UserVo authUser, CategoryVo categoryVo) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		categoryVo.setBlogId(id);
		categoryService.addCategory(categoryVo);
		return "redirect:/" + id + "/admin/category";
	}

	@Auth
	@RequestMapping("/admin/category/delete/{no}")
	public String removeCategory(@PathVariable("id") String id, @AuthUser UserVo authUser,
			@PathVariable("no") Long no) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		categoryService.removeCategory(id, no);
		return "redirect:/" + id + "/admin/category";
	}

	@Auth
	@RequestMapping(value = "/admin/write", method = RequestMethod.GET)
	public String adminWrite(@PathVariable("id") String id, @AuthUser UserVo authUser, Model model) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		model.addAttribute("blog", blogService.getBlog(id));
		model.addAttribute("categoryList", categoryService.getCategories(id));
		return "blog/admin/write";
	}

	@Auth
	@RequestMapping(value = "/admin/write", method = RequestMethod.POST)
	public String addPost(@PathVariable("id") String id, @AuthUser UserVo authUser, PostVo postVo) {
		if (!authUser.getId().equals(id)) {
			return "redirect:/";
		}

		postService.addPost(id, postVo);
		return "redirect:/" + id;
	}
}