package ma.ilem.inventorymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ilem.inventorymanagement.pojo.AssignmentType;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ITEMS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name", nullable = false)
    @Size(min = 2, max = 30)
    @NotBlank
    private String name;

    @Column(name = "item_description", nullable = false)
    @Size(min = 2, max = 150)
    @NotBlank
    private String description;

    @Column(name = "item_quantity", nullable = false)
    @Min(0)
    @Max(999)
    @NotNull
    private Long quantity;

    @Column(name = "item_info", nullable = false)
    @Min(0)
    @Max(100)
    @NotNull
    private Long info;

    @Column(name = "item_warning", nullable = false)
    @Min(0)
    @Max(100)
    @NotNull
    private Long warning;

    @Column(name = "item_error", nullable = false)
    @Min(0)
    @Max(100)
    @NotNull
    private Long error;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @NotAudited
    @OneToMany(mappedBy = "item")
    private List<Assignment> assignment = new ArrayList<>();

    @Column(name = "deleted")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean deleted;

    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String modifiedBy;

    public Long getUsedQuantity() {
        Long result = 0L;
        for (Assignment element : assignment) {
            if (element.getType() == AssignmentType.ACCEPTED || element.getType() == AssignmentType.DELIVERED || element.getType() == AssignmentType.RETURNING) {
                result += element.getQuantity();
            }
        }
        return result;
    }
}
