package com.curso.reservaveiculosapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usu_usuario", schema = "public")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_nr_id")
    private Long id;

    @Column(name = "usu_tx_nome")
    private String nome;

    @Column(name = "usu_tx_login")
    private String login;

    @Column(name = "usu_tx_senha")
    private String senha;

    @ManyToMany
    @JoinTable(
            name = "usp_usuario_perfil",
            joinColumns = @JoinColumn(name = "usu_nr_id"),
            inverseJoinColumns = @JoinColumn(name = "per_nr_id"))
    private List<PerfilEntity> perfis;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VeiculoUsuarioEntity> veiculos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(perfis != null) {
            for (PerfilEntity perfil : perfis) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + perfil.getNome().toUpperCase()));
            }
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
