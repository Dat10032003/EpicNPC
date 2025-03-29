package NguyenDat.EpicNPC.Repositories;

import NguyenDat.EpicNPC.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id); // Tìm vai trò theo ID

    // Thêm phương thức tìm vai trò theo tên
    Optional<Role> findByName(String name);
}
