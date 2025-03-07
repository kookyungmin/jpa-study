package net.happykoo.jpa.repository;

import net.happykoo.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
