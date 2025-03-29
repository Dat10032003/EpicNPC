package NguyenDat.EpicNPC.Repositories;

import NguyenDat.EpicNPC.Entities.Notification;
import NguyenDat.EpicNPC.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadFalse(User user);
}
