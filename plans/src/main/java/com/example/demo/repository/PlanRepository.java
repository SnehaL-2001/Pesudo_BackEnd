package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {

	
	List<Plan> findByPlanPrice(Double price);

	

	Plan findByName(String name);

}
