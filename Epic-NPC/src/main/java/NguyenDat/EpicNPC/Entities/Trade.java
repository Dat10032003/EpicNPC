package NguyenDat.EpicNPC.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "middleman_id")
    private User middleman;

    @Column
    private Double value;

    @Column(length = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    private TradeType type;

    @Enumerated(EnumType.STRING)
    private TradeStatus status;

    @Column(columnDefinition = "TEXT")
    private String itemDescription; // Mô tả sản phẩm hoặc tài khoản

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        status = TradeStatus.CREATED;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
