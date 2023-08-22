package com.viamatica.login.percistence.crud;

import com.viamatica.login.percistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByIdPerson(int idPerson);

}
