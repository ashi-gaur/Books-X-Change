// package com.angularspringbootecommerce.backend.services;

// import com.angularspringbootecommerce.backend.dtos.UserLoginDto;
// import com.angularspringbootecommerce.backend.models.User;
// import com.angularspringbootecommerce.backend.models.UserRole;
// import jakarta.transaction.Transactional;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.springframework.security.core.AuthenticationException;
// import com.angularspringbootecommerce.backend.repository.UserRepository;
// import com.angularspringbootecommerce.backend.repository.UserRoleRepository;

// import java.util.HashSet;
// import java.util.Set;

// @Service
// @Transactional
// public class AuthenticationService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private UserRoleRepository userRoleRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Autowired
//     private AuthenticationManager authenticationManager;

//     @Autowired
//     private TokenService tokenService;

//     // public User register(String email, String password) {
//     //     String encodedPassword = passwordEncoder.encode(password);

//     //     UserRole userRole = userRoleRepository.findByAuthority("USER").get();
//     //     Set<UserRole> authorities = new HashSet<>();

//     //     authorities.add(userRole);

//     //     return userRepository.save(new User(email, encodedPassword, authorities));
//     // }
//     public User register(String email, String password) {
//         String encodedPassword = passwordEncoder.encode(password);
    
//         UserRole userRole = userRoleRepository.findByAuthority("USER")
//                 .orElseThrow(() -> new RuntimeException("Role with authority 'USER' not found"));
    
//         Set<UserRole> authorities = new HashSet<>();
//         authorities.add(userRole);
    
//         return userRepository.save(new User(email, encodedPassword, authorities));
//     }
    

//     public UserLoginDto login(String email, String password) {
//         try {
//             Authentication auth = authenticationManager.authenticate(
//                     new UsernamePasswordAuthenticationToken(email, password)
//             );

//             String token = tokenService.generateJwt(auth);

//             User user = userRepository.findByEmail(email).orElse(null);
//             if (user != null) {
//                 return new UserLoginDto(user.getId(), user, token);
//             } else {
//                 return new UserLoginDto(null, null, "");
//             }

//         } catch (AuthenticationException e) {
//             return new UserLoginDto(null, null, "");
//         }
//     }
// }
package com.angularspringbootecommerce.backend.services;

import com.angularspringbootecommerce.backend.dtos.UserLoginDto;
import com.angularspringbootecommerce.backend.models.User;
import com.angularspringbootecommerce.backend.models.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.angularspringbootecommerce.backend.repository.UserRepository;
import com.angularspringbootecommerce.backend.repository.UserRoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User register(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        UserRole userRole = userRoleRepository.findByAuthority("USER")
                .orElseGet(() -> createDefaultUserRole()); // Create if not found

        Set<UserRole> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new User(email, encodedPassword, authorities));
    }

    private UserRole createDefaultUserRole() {
        return userRoleRepository.save(new UserRole("USER"));
    }

    public UserLoginDto login(String email, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                return new UserLoginDto(user.getId(), user, token);
            } else {
                return new UserLoginDto(null, null, "");
            }

        } catch (AuthenticationException e) {
            return new UserLoginDto(null, null, "");
        }
    }
}
