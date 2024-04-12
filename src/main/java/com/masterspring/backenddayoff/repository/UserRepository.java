package com.masterspring.backenddayoff.repository;

import com.masterspring.backenddayoff.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String username, String password);
}
