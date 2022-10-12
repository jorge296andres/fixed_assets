package com.asd.fixedassets.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "area", uniqueConstraints= {@UniqueConstraint(columnNames={"area_name", "city"})})
public class Area {
    @Id
    @Column(name = "area_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long areaId;

    @Column(name = "area_name", nullable = false)
    private String areaName;

    @Column(name = "city", nullable = false)
    private String city;

    @OneToMany(
            mappedBy = "area",
            orphanRemoval = true)
    private Set<FixedAssets> fixedAssets = new HashSet<>();

    public void addFixedAssets(FixedAssets fixedAsset) {
        fixedAssets.add(fixedAsset);
        fixedAsset.setArea(this);
    }

    public void removeFixedAssets(FixedAssets fixedAsset) {
        fixedAssets.remove(fixedAsset);
        fixedAsset.setArea(null);
    }

}
