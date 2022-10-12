package com.asd.fixedassets.repositories;

import com.asd.fixedassets.domain.FixedAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IFixedAssetsRepository extends JpaRepository<FixedAssets, Long> {

    List<FixedAssets> findByType(String type);

    List<FixedAssets> findByPurchaseDate(LocalDate date);

    Optional<FixedAssets> findBySerial(String serial);

}
