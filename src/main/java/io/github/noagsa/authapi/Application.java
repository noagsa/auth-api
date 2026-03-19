package io.github.noagsa.authapi;

import io.github.noagsa.authapi.model.Role;
import io.github.noagsa.authapi.model.User;
import io.github.noagsa.authapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Value("${admin.mail}")
	private String adminMail;
	@Value("${admin.password}")
	private String adminPassword;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    public Application(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String encondedPassword = passwordEncoder.encode(adminPassword);
		userRepository.save(new User(1, adminMail, encondedPassword, Role.ADMIN));
	}
}
