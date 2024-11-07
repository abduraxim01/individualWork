package org.example.individualwork.repository;

import org.example.individualwork.model.Sotuvchi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SotuvchiRepository extends JpaRepository<Sotuvchi, Long> {

    Sotuvchi findByUsername(String username);

    boolean existsByUsername(String username);
}
