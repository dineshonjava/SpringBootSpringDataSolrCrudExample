/**
 * 
 */
package com.doj.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doj.app.pojo.Order;
import com.doj.app.repository.SolrOrderRepository;

/**
 * @author Dinesh.Rajput
 *
 */
@RestController
public class OrderController {
	
	@Autowired
	SolrOrderRepository solrOrderRepository;
	
	@PostMapping("/order")
	public String createOrder(@RequestBody Order order){
		String description = "Order Created";
		solrOrderRepository.save(order);
		return description;
	}
	
	@GetMapping("/order/{orderid}")
	public Order readOrder(@PathVariable Long orderid){
		return solrOrderRepository.findByOrderid(orderid);
	}
	
	@PutMapping("/order")
	public String updateOrder(@RequestBody Order order){
		String description = "Order Updated";
		solrOrderRepository.save(order);
		return description;
	}
	
	@DeleteMapping("/order/{orderid}")
	public String deleteOrder(@PathVariable Long orderid){
		String description = "Order Deleted";
		solrOrderRepository.delete(solrOrderRepository.findByOrderid(orderid));
		return description;
	}
	
	@GetMapping("/order/desc/{orderDesc}/{page}")
	public List<Order> findOrder(@PathVariable String orderDesc, @PathVariable int page){
		return solrOrderRepository.findByOrderDescription(orderDesc, PageRequest.of(page, 2)).getContent();
	}
	
	@GetMapping("/order/search/{searchTerm}/{page}")
	public List<Order> findOrderBySearchTerm(@PathVariable String searchTerm, @PathVariable int page){
		return solrOrderRepository.findByCustomerQuery(searchTerm, PageRequest.of(page, 2)).getContent();
	}
}
