package com.example.demo.reponsitory;

import com.example.demo.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
//    Page<Customer> findByCreatedBy(Long userId, Pageable pageable);

//    Page<Customer> findByCategory(Long categoryId, Pageable pageable);

//    Page<Customer> findByTags(List<Tag> tags, Pageable pageable);

//    Long countByCreatedBy(Long userId);
}
