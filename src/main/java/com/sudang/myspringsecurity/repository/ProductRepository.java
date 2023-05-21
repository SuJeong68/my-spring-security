package com.sudang.myspringsecurity.repository;

import com.sudang.myspringsecurity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
