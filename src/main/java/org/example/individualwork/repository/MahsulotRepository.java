package org.example.individualwork.repository;

import org.example.individualwork.model.Mahsulot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahsulotRepository extends JpaRepository<Mahsulot, Long> {
}
