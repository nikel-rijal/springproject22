package com.bway.springproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bway.springproject.model.Employee;
import com.bway.springproject.model.Product;
import com.bway.springproject.repository.ProductRepository;
import com.bway.springproject.service.EmployeeService;

@RestController
public class EmployeeRestController {

	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/api/emp/list")
	public List<Employee> getAllEmpls() {
		
		return empService.getAllEmployee();
	}
	
	@PostMapping("/api/emp/add")
	public String add(@RequestBody Employee emp) {
		
		empService.addEmployee(emp);
		
		return "added success";
	}
	
	@GetMapping("/api/emp/{id}")
	public Employee getOne(@PathVariable("id") Long id) {
		
		return empService.getEmpById(id);
	}
	
	@DeleteMapping("/api/emp/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		empService.deleteEmployee(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PutMapping("/api/emp/update")
	public ResponseEntity<?> update(@RequestBody Employee emp) {
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	//consume/use existing RestApi and store data in db
	@GetMapping("/api/loadapi")
	public String loadApi() {
		
		RestTemplate temp = new RestTemplate();
		Product[] prods = temp.getForObject("https://fakestoreapi.com/products", Product[].class);
		
		prodRepo.saveAll(List.of(prods));
		
		return "success";
	}
}
