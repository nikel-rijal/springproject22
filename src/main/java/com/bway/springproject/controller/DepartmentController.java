package com.bway.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bway.springproject.model.Department;
import com.bway.springproject.service.DepartmentService;
import com.bway.springproject.utils.DepartmentExcelView;
import com.bway.springproject.utils.DepartmentPdfView;

@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentService deptService;

	@GetMapping("/department")
	public String getDepartment() {
		
		return "DepartmentForm";
	}
	
	@PostMapping("/department")
	public String postDepartment(@ModelAttribute Department dept, Model model) {
		
		if(dept.getDeptName().isBlank() || dept.getDeptName() == null || dept.getDeptHead().isBlank() || dept.getDeptHead() == null || dept.getDeptPhone().isBlank() || dept.getDeptPhone() == null ) {
			
			model.addAttribute("nullValue", "Please enter details!!");
			return "DepartmentForm";
		}
		deptService.addDept(dept);
		
		return "redirect:/department";
	}
	
	@GetMapping("/departmentList")
	public String getAllDepts(Model model) {
		
		model.addAttribute("dlist", deptService.getAllDepts());
		return "DepartmentListForm";
	}
	
	@GetMapping("/dept/delete")
	public String delete(@RequestParam("id") int id) {
		
		deptService.deleteDept(id);
		return "redirect:/departmentList";
	}
	
	@GetMapping("/dept/edit")
	public String edit(@RequestParam("id") int id, Model model) {
		
		model.addAttribute("dModel", deptService.getDeptById(id));
		return "DepartmentEditForm";
	}
	
	@PostMapping("/dept/update")
	public String update(@ModelAttribute Department dept) {
		
		deptService.updateDept(dept);
		return "redirect:/departmentList";
	}
	
	@GetMapping("/dept/excel")
	public ModelAndView getExcel() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("dList", deptService.getAllDepts());
		mv.setView(new DepartmentExcelView());
		
		return mv;
	}
	
	@GetMapping("/dept/pdf")
	public ModelAndView getPdf() {
		
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("dList", deptService.getAllDepts());
		mv.setView(new DepartmentPdfView());
		
		return mv;
	}
}
