package com.example.pr51salon.repository;

import com.example.pr51salon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

    boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	User findByPhoneNumber(String phoneNumber);

}
