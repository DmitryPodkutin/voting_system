package com.gmail.podkutin.dmitry.votingsystem.repository;

import com.gmail.podkutin.dmitry.votingsystem.model.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    Optional<User> getByEmail(String email);

    default List<User> getAll() {
        return findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }
}
