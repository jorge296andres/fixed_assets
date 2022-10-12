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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_number", unique = true)
    private String documentNumber;

    @OneToMany(
            mappedBy = "person",
            orphanRemoval = true)
    private Set<FixedAssets> fixedAssets = new HashSet<>();

    public void addFixedAssets(FixedAssets fixedAsset) {
        fixedAssets.add(fixedAsset);
        fixedAsset.setPerson(this);
    }

    public void removeFixedAssets(FixedAssets fixedAsset) {
        fixedAssets.remove(fixedAsset);
        fixedAsset.setPerson(null);
    }
}
