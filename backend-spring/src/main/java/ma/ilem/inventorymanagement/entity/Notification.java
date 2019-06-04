package ma.ilem.inventorymanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.ilem.inventorymanagement.pojo.NotificationType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_message")
    private String message;

    @Enumerated
    @Column(name = "notification_type")
    private NotificationType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "deleted")
    private boolean deleted;
}
