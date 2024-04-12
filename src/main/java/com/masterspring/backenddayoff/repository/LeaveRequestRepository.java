package com.masterspring.backenddayoff.repository;

import com.masterspring.backenddayoff.entity.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByUserId(Long userId);

    public Page<LeaveRequest> findAllById(Long userId, Pageable pageable);

    public Page<LeaveRequest> findAllByUserFullNameContainingIgnoreCaseOrReasonContainingIgnoreCase(String fullNameKeyword, String ReasonKeyword, Pageable pageable);
}
