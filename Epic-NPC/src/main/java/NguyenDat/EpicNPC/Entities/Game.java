package NguyenDat.EpicNPC.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "added_date", updatable = false)
    private LocalDate addedDate;

    @PrePersist
    protected void onCreate() {
        this.addedDate = LocalDate.now();
    }
}
