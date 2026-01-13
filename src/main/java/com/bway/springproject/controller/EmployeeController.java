package com.bway.springproject.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bway.springproject.model.Department;
import com.bway.springproject.model.Employee;
import com.bway.springproject.service.DepartmentService;
import com.bway.springproject.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/employee")
	public String getEmployee(Model model) {
		
		List<Department> dlist = deptService.getAllDepts();
		model.addAttribute("dlist", dlist);
		return "EmployeeForm";
	}
	
	@PostMapping("/employee")
	public String postEmployee(@ModelAttribute Employee emp) {
		
		empService.addEmployee(emp);
		return "redirect:/employee";
	}
	
	@GetMapping("/employeeList")
	public String getAllEmployee(Model model) {
		
		List<Employee> elist = empService.getAllEmployee();
		model.addAttribute("elist", elist);
		
		return "EmployeeListForm";
	}
	
	@PostMapping("employee/view")
	public String postAllEmployee() {
		
		return "redirect:/employeeList";
	}
	
	@GetMapping("/employee/edit")
	public String editEmployee(@RequestParam("id") long id, Model model) {
		
		Employee e = empService.getEmpById(id);
		model.addAttribute("data", e);
		List<Department> dlist = deptService.getAllDepts();
		model.addAttribute("dlist", dlist);
		
		
		return "EmployeeEditForm";
	}
	
	@PostMapping("/employee/update")
	public String updateEmployee(@ModelAttribute Employee emp, Model model) {
		
		empService.update(emp);
		return "redirect:/employeeList";
	}
	
	@GetMapping("/employee/delete")
	public String deletdEmployee(@RequestParam("id") long id, Model model) {
		
		empService.deleteEmployee(id);
		return "redirect:/employeeList";
	}
	
	@GetMapping("/employee/view")
	public String viewEmployee(@RequestParam("id") long id, Model model) {
		
		Employee e = empService.getEmpById(id);
		model.addAttribute("data",e);
		List<Department> dept = deptService.getAllDepts();
		model.addAttribute("dlist",dept);
		return "ViewEmployeeForm";
	}
}
