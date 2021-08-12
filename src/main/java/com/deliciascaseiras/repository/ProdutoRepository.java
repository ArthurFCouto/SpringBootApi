package com.deliciascaseiras.repository;

import com.deliciascaseiras.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
