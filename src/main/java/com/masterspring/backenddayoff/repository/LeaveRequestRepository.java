package com.masterspring.backenddayoff.repository;

import com.masterspring.backenddayoff.entity.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    Page<LeaveRequest> findByUserId(Long userId, Pageable pageable);

    public Page<LeaveRequest> findAllById(Long userId, Pageable pageable);

    public Page<LeaveRequest> findAllByUserFullNameContainingIgnoreCaseOrReasonContainingIgnoreCase(String fullNameKeyword, String ReasonKeyword, Pageable pageable);
}
