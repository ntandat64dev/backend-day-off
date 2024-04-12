package com.masterspring.backenddayoff.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "`users`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Long id;

    @Column(name = "`email`", unique = true, nullable = false)
    private String email;

    @Column(name = "`password`", nullable = false)
    private String password;

    @Column(name = "`full_name`")
    private String fullName;

    @Column(name = "`birthdate`")
    private LocalDate birthdate;

    @Column(name = "`work_date`")
    private LocalDate workDate;

    @Column(name = "`address`")
    private String address;

    @Column(name = "`phone`")
    private String phone;

    /**
     * 0 - Manager
     * 1 - Admin
     * 2 - User
     */
    @Column(name = "`role`", nullable = false)
    private Integer role;

    @ManyToOne
    @JoinColumn(name = "`department_id`", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "user")
    private List<LeaveRequest> leaveRequests;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private LeaveRemain leaveRemain;

    @ManyToOne
    @JoinColumn(name = "`manager_id`")
    private User manager;

    public void setLeaveRemain(LeaveRemain leaveRemain) {
        leaveRemain.setUser(this);
        this.leaveRemain = leaveRemain;
    }
}
