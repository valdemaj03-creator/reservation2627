package be.iccbxl.pid.reservation2627.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.reservation2627.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    List<User> findByLastname(String lastname);
    User findById(long id);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
}