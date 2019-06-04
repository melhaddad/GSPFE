package ma.ilem.inventorymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ilem.inventorymanagement.pojo.PrivilegeName;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable, UserDetails {

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    List<GrantedAuthority> authorities = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "user_last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "user_first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "user_address", nullable = false)
    private String address;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "user_function", nullable = false)
    private String function;

    @Email
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Size(min = 6)
    @NotBlank
    @Column(name = "user_password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(max = 30)
    @Column(name = "user_image", nullable = false)
    private String image = "default";

    @NotAudited
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USERS_ROLES", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

    @NotAudited
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Assignment> assignment = new ArrayList<>();

    @NotAudited
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Notification> notifications = new ArrayList<>();

    @Column(name = "deleted")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted;

    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modifiedBy;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities.clear();

        roles.forEach(role -> {
            if (!role.isDeleted()) {
                role.getPrivileges().forEach(privilege -> {
                    if (privilege.isActive() || privilege.getName().equals(PrivilegeName.ALL_PRIVILEGES)) {
                        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(privilege.getName().name());
                        if (!authorities.contains(grantedAuthority)) {
                            authorities.add(grantedAuthority);
                        }
                    }
                });
            }
        });

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
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
