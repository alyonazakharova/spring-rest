package main.web;

import main.entity.User;
import main.exception.InvalidCredentialsException;
import main.repository.UserRepository;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AuthRequest request) {
        try {
            String name = request.getUserName();
            Optional<User> optionalUser = userRepository.findUserByUsername(name);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (!pwdEncoder.matches(request.getPassword(), user.getPassword())) {
                    throw new InvalidCredentialsException("Invalid password");
                }
            } else {
                throw new UsernameNotFoundException("User not found");
            }

            String token = jwtTokenProvider.createToken(
                    name,
                    userRepository.findUserByUsername(name)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles()
            );

            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);

            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Invalid username or password");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        String username = user.getUsername();
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this username already exists");
        } else {
            String password = user.getPassword();
            password = pwdEncoder.encode(password);
            user.setPassword(password);
            return userRepository.save(user);
        }
    }
}
