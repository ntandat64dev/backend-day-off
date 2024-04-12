package com.masterspring.backenddayoff.repository;

import com.masterspring.backenddayoff.entity.LeaveRemain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRemainRepository extends JpaRepository<LeaveRemain, Long> {
    LeaveRemain findByUserId(Long userId);
}
