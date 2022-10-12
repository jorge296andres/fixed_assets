package com.asd.fixedassets.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fixed_assets")
public class FixedAssets {
    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "serial", nullable = false, unique = true)
    private String serial;

    @Column(name = "type", nullable = false)
    private String type;
    /**
     * Peso
     */
    @Column(name = "weight", nullable = false)
    private Double weight;

    /**
     * Alto
     */
    @Column(name = "height", nullable = false)
    private Double height;

    /**
     * Ancho
     */
    @Column(name = "width", nullable = false)
    private Double width;

    /**
     * Largo
     */
    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "purchase_value", nullable = false)
    private Double purchaseValue;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
