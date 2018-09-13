/**
 * 
 */
package com.doj.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.doj.app.pojo.Order;

/**
 * @author Dinesh.Rajput
 *
 */
public interface SolrOrderRepository extends SolrCrudRepository<Order, Long> {
	
	Order findByOrderid(Long orderid);
	
    @Query("odesc:*?0*")
    Page<Order> findByOrderDescription(String searchTerm, Pageable pageable);
    
    @Query("odesc:*?0* OR oname:*?0* OR pname:*?0*")
    Page<Order> findByCustomerQuery(String searchTerm, Pageable pageable);
 
    @Query(name = "Order.findByNamedQuery")
    Page<Order> findByNamedQuery(String searchTerm, Pageable pageable);
}
