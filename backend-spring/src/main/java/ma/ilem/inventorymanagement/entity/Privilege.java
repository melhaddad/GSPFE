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

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRIVILEGES")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Privilege implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Long id;

    @Column(name = "privilege_name", updatable = false, nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PrivilegeName name;

    @Column(name = "privilege_description")
    private String description;

    @Column(name = "privilege_show")
    private String show;

    @Column(name = "privilege_active")
    private boolean active = true;

    @JsonIgnore
    @NotAudited
    @ManyToMany
    @JoinTable(name = "ROLES_PRIVILEGES", joinColumns = {@JoinColumn(name = "privilege_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();

    @Column(name = "deleted")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted;

    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modifiedBy;

}
