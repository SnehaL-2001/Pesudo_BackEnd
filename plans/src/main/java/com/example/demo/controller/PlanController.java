package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Plan;

import com.example.demo.service.PlanService;

@RestController

public class PlanController {
	  private final PlanService planService;

	    @Autowired
	    public PlanController(PlanService planService) {
	        this.planService = planService;
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
	    @GetMapping("/pricing-plans")
	    public List<Plan> searchPlansByPrice(@RequestParam Double price) {
	        // Implement your search logic using the "price" parameter
	    	System.out.println(planService.searchPlansByPrice(price));
	        return planService.searchPlansByPrice(price);
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
	    @PostMapping("/addplan")
	    public Plan addPlan(@RequestBody Plan plan) {
	        return planService.addPlan(plan);
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
	    @GetMapping("/getplan")
	    public List<Plan> getAllPlans() {
	        return planService.getAllPlans();
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
	    @GetMapping("plans/{id}")
	    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
	        Plan plan = planService.getPlanById(id);
	        if (plan == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(plan, HttpStatus.OK);
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
	    @PutMapping("plans/{id}")
	    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
	        Plan existingPlan = planService.getPlanById(id);
	        if (existingPlan == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        plan.setId(id); // Ensure the ID is set
	        Plan updatedPlan = planService.updatePlan(plan);
	        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
	    @DeleteMapping("deleteplan/{planId}")
	    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
	        planService.deletePlanById(planId);
	        return ResponseEntity.noContent().build();
	    }
	    @CrossOrigin(origins="http://localhost:4200/")
		@GetMapping("/getplansbyname/{name}")
	    public ResponseEntity<Plan> getPlanByName(@PathVariable String name) {
	        Plan planss =  planService.getPlanByName(name);

	        if (planss != null) {
	            return new ResponseEntity<>(planss, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	
	   
}
