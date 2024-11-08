package com.spring.ecommerce.setup.repositories;

import com.spring.ecommerce.setup.models.EcomProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcomProductRepository extends JpaRepository<EcomProduct,Long> {
}
