/**
 * 
 */
package com.doj.app.repository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Repository;

import com.doj.app.pojo.Order;

/**
 * @author Dinesh.Rajput
 *
 */
@Repository
public class CustomSolrOrderRepository {
	
	@Resource
    private SolrTemplate solrTemplate;
	
    public List<Order> dynamicSearch(String searchTerm) {
        Criteria conditions = createConditions(searchTerm);
        SimpleQuery search = new SimpleQuery(conditions);
         
        search.addSort(sortByIdDesc());
 
        Page<Order> results = solrTemplate.queryForPage("Order", search, Order.class);
        return results.getContent();
    }
 
    private Criteria createConditions(String searchTerm) {
        Criteria conditions = null;
 
        for (String term: searchTerm.split(" ")) {
            if (conditions == null) {
                conditions = new Criteria("oid").contains(term)
                        .or(new Criteria("odesc").contains(term));
            }
            else {
                conditions = conditions.or(new Criteria("oid").contains(term))
                        .or(new Criteria("odesc").contains(term));
            }
        }
        return conditions;
    }
 
    private Sort sortByIdDesc() {
        return new Sort(Sort.Direction.DESC, "oid");
    }
    
}
