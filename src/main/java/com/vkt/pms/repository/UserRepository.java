package com.vkt.pms.repository;

import com.vkt.pms.entity.User;
import com.vkt.pms.enums.Role;
import com.vkt.pms.responseDto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<List<User>> findByRole(Role role);
}
