package com.example.demo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "medical_record")
public class MedicalRecord extends BaseEntity {

    @Column(name = "image_urls")
    private String imageUrls;

    @Column(name = "labels")
    private String labels;

    @Column(name = "meta_data")
    private String metaData;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_updated")
    private Date lastUpdate;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
}
