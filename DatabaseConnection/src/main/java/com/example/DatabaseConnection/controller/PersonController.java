package com.example.DatabaseConnection.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DatabaseConnection.exception.ResourceNotFoundException;
import com.example.DatabaseConnection.model.*;
import com.example.DatabaseConnection.repository.*;


@RestController
@RequestMapping("/api")
public class PersonController {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private MySpringBootRepository mySpringBootRepository;
	
	@GetMapping("/person/{personId}/orders")
	public Page<Order> getAllOrdersByPersonId(@PathVariable (value = "personId") Long personId, Pageable pageable){
		return orderRepository.findByPersonId(personId,  pageable);
	}
	
	@PostMapping("person/{personId}/orders")
	public Order createComment(@PathVariable (value = "personId") Long personId,
			@Valid @RequestBody Order order) {
		return mySpringBootRepository.findById(personId).map(MySpringBootDataModel -> {
			order.setPerson(MySpringBootDataModel);
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("Person", "id", order));
	}
	
	@PutMapping("/person/{personId)/orders/{orderId}")
	public Order updateOrder(@PathVariable (value = "personId") Long personId,
			@PathVariable (value = "orderId") Long orderId,
			@Valid @RequestBody Order orderRequest) {
		if(!mySpringBootRepository.existsById(personId)) {
			throw new ResourceNotFoundException("Person", "Id", orderRequest);
		}
		
		return orderRepository.findById(orderId).map(order -> {
			order.setTitle(orderRequest.getTitle());
			return orderRepository.save(order);
		}).orElseThrow(() -> new ResourceNotFoundException("OrderId", "id", orderRequest));
	}
	
	@DeleteMapping("/person/{personId}/orders/{orderId}")
	public ResponseEntity<?> deleteComment(@PathVariable (value = "personId") Long personId,
			@PathVariable (value = "orderId") Long orderId){
		if(!mySpringBootRepository.existsById(personId)) {
			throw new ResourceNotFoundException("Person", "Id", personId);
		}
		
		return orderRepository.findById(orderId).map(order -> {
			orderRepository.delete(order);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Order Id", orderId.toString(), null));
		
	}
	
	
	
	
	
	
	
}
