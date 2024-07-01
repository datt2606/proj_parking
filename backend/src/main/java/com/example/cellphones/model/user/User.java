package com.example.cellphones.model.user;

import javax.persistence.*;

import com.example.cellphones.model.parking.Customer;
import com.example.cellphones.model.parking.Employee;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( length = 30, nullable = false, unique = true)
    private String username;

    @Column( nullable = false, unique = true)
    private String password;

    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

    @OneToOne(mappedBy = "user", fetch = LAZY, cascade = ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return enabled;
    }
}
