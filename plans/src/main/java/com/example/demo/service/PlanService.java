package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Plan;
import com.example.demo.repository.PlanRepository;
@Service
public class PlanService {
	
	private final PlanRepository planRepository;
	@Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan addPlan(Plan plan) {
        return planRepository.save(plan);
    }

	public List<Plan> getAllPlans() {
		System.out.println(planRepository.findAll());
		 return planRepository.findAll();
	}

	 public Plan getPlanById(Long id) {
	        return planRepository.findById(id).orElse(null);
	    }

	    public Plan updatePlan(Plan plan) {
	        return planRepository.save(plan);
	    }

		
			public void deletePlanById(Long planId) {
		        planRepository.deleteById(planId);
		    }

//			public List<Plan> findByKeyword(String search) {
//				 // For example, you can use a custom method in your repository
//		        return planRepository.findByName(search);
//			}
//
//			public Plan findPlanByName(String name) {
//				  return  (Plan) planRepository.findByName(name);
//				
//			}

			public Plan getPlanByName(String name) {
		        return planRepository.findByName(name);
		    }

			public List<Plan> searchPlansByPrice(Double price) {
				return planRepository.findByPlanPrice(price);
			}
			
		}

