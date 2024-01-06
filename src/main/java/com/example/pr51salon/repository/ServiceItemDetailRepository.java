package com.example.pr51salon.repository;

import com.example.pr51salon.model.ServiceItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceItemDetailRepository extends JpaRepository<ServiceItemDetail, Long> {

}
