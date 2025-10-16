////a service layer for user operations
//
//package org.example.asd;
//
//import betterpedia.repository.UserRepository;
//import betterpedia.model.User;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
////repositories for access
//@Service
//public class UserService {
//    private final UserRepository users;
//    private final RoleRepository roles;
//
//    public UserService(UserRepository users, RoleRepository roles) {
//        this.users = users;
//        this.roles = roles;
//    }
////fetch a user, or throw if not found
//    public User getById(Long id) {
//        return users.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
//    }
////updating users email, email must not already exist
//    @Transactional
//    public User_Esha updateProfile(Long userId, String email) {
//        User_Esha u = getById(userId);
//        if (!u.getEmail().equalsIgnoreCase(email) && users.existsByEmail(email)) {
//            throw new IllegalArgumentException("Email already in use");
//        }
//        u.setEmail(email);
//        return users.save(u);
//    }
////chnaging users password
//    @Transactional
//    public void changePassword(Long userId, String newPassword) {
//        User_Esha u = getById(userId);
//        // hash later when enabling security
//        u.setPassword(newPassword);
//        users.save(u);
//    }
//
//    public List<User_Esha> listAll() {
//        return users.findAll();
//    }
////creatinga new user, enabling roles
//    @Transactional
//    public User_Esha createUser(String email, String password, Collection<String> roleNames, boolean enabled) {
//        if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already in use");
//        User_Esha u = new User_Esha();
//        u.setEmail(email);
//        u.setPassword(password); // hash later
//        u.setEnabled(enabled);
//        u.setRoles(resolveRoles(roleNames));
//        return users.save(u);
//    }
////enable or disable a user
//    @Transactional
//    public User_Esha setEnabled(Long userId, boolean enabled) {
//        User_Esha u = getById(userId);
//        u.setEnabled(enabled);
//        return users.save(u);
//    }
////updating a users roles
//    @Transactional
//    public User_Esha updateRoles(Long userId, Collection<String> roleNames) {
//        User_Esha u = getById(userId);
//        u.setRoles(resolveRoles(roleNames));
//        return users.save(u);
//    }
////delete a user by ID
//    @Transactional
//    public void deleteUser(Long userId) {
//        users.deleteById(userId);
//    }
////toggle the enable/disables
//    @Transactional
//    public boolean toggleEnabled(Long userId) {
//        User_Esha u = getById(userId);
//        boolean newVal = !u.isEnabled(); // <-- fixed
//        u.setEnabled(newVal);
//        users.save(u);
//        return newVal;
//    }
//
//    private Set<Role> resolveRoles(Collection<String> roleNames) {
//        Set<Role> out = new HashSet<>();
//        if (roleNames == null) return out;
//        for (String raw : roleNames) {
//            String name = normalizeRole(raw);
//            Role r = roles.findByName(name).orElseGet(() -> {
//                Role nr = new Role();
//                nr.setName(name);
//                return roles.save(nr);
//            });
//            out.add(r);
//        }
//        return out;
//    }
//
//    private String normalizeRole(String s) {
//        if (s == null) return "ROLE_USER";
//        String n = s.trim().toUpperCase(Locale.ROOT);
//        if (!n.startsWith("ROLE_")) n = "ROLE_" + n;
//        return n;
//    }
//}
