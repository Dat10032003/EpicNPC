package NguyenDat.EpicNPC.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import NguyenDat.EpicNPC.Entities.Trade;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String content;
    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;



    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
