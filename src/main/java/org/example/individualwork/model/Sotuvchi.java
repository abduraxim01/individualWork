package org.example.individualwork.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Table(name = "sotuvchilar")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sotuvchi implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 25)
    private String username;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(length = 25)
    private String name;

    @OneToMany(mappedBy = "sotuvchi", cascade = CascadeType.ALL)
    private List<Mahsulot> mahsulots;

    private String image;

    private LocalDateTime toDate;

    //    @UpdateTimestamp
    @CreationTimestamp
    private Timestamp created_at;

    @Enumerated(EnumType.STRING)
    private Rollar role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return toDate.isAfter(LocalDateTime.now());
    }
}
