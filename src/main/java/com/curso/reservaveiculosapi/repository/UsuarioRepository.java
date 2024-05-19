package com.curso.reservaveiculosapi.repository;

import com.curso.reservaveiculosapi.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT u FROM UsuarioEntity u LEFT JOIN FETCH u.perfis WHERE u.login = :login")
    UserDetails findByLogin(@Param("login") String login);

    Optional<UsuarioEntity> findUsuarioEntityByLogin(String login);

}
