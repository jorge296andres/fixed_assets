package com.asd.fixedassets.repositories;

import com.asd.fixedassets.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAreaRepository extends JpaRepository<Area, Long> {
    Optional<Area> findByAreaNameAndCity(String areaName, String city);
}
