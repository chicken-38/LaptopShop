package com.laptopshop.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptopshop.laptopshop.domain.Factory;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {

}
