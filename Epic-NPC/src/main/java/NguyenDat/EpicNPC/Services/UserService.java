package NguyenDat.EpicNPC.Services;

import NguyenDat.EpicNPC.Entities.Role;
import NguyenDat.EpicNPC.Entities.User;
import NguyenDat.EpicNPC.Repositories.RoleRepository;
import NguyenDat.EpicNPC.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Thêm repository cho vai trò

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> resetTokens = new ConcurrentHashMap<>();

    // Save user with encrypted password and role assignment
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); // Thiết lập thuộc tính enabled thành true

        // Gán quyền mặc định (ví dụ: USER) nếu không có quyền nào được gán
        if (user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.getRoles().add(defaultRole);
        }

        userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    // Create user and assign roles
    public User createUser(User user, boolean isAdmin) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Gán quyền USER hoặc ADMIN dựa trên giá trị isAdmin
        Set<Role> roles = new HashSet<>();
        if (isAdmin) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            roles.add(adminRole);
        } else {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("User role not found"));
            roles.add(userRole);
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    // Update user
    public User updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Update user's avatar
    public void updateAvatar(String username, String avatarPath) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setAvatar(avatarPath);
        userRepository.save(user);
    }

    // Get available avatars
    public List<String> getAvailableAvatars() {
        try {
            Path avatarPath = Path.of("src/main/resources/static/images/avatar");
            return Files.walk(avatarPath, 1)
                    .filter(path -> !path.equals(avatarPath))
                    .map(path -> "/images/avatar/" + path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Change user's password
    public User changePassword(Long id, String newPassword) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(existingUser);
        }
        return null;
    }

    // Find user by email
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Load user details by username for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Log thông tin người dùng và quyền của họ
        System.out.println("User " + user.getUsername() + " logged in with roles: " + user.getAuthorities());

        return user;
    }

    // Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElse(null);
    }
}
