package ma.ilem.inventorymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ilem.inventorymanagement.pojo.AssignmentType;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@Table(name = "USERS_ITEMS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Assignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    @Size(min = 15, max = 3000)
    @Column(name = "user_item_message", nullable = false)
    private String message;

    @Min(0)
    @Max(999)
    @NotNull
    @Column(name = "user_item_quantity", nullable = false)
    private Long quantity;

    @Enumerated
    @Column(name = "user_item_type", nullable = false)
    private AssignmentType type;

    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modifiedBy;
}
