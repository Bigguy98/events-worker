package com.example.demo.repository;

import com.example.demo.repository.entity.MedicalRecord;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicalRecordRepository extends BaseRepository<MedicalRecord> {

    @Query("SELECT md FROM MedicalRecord md WHERE md.category.id = ?1 and md.status = ?2")
    List<MedicalRecord> findApprovedByCategoryId(Long categoryId, String approvedStatus);
}
