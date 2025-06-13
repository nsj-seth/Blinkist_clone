package com.example.library.security.config;

import com.example.library.security.jwt.AuthTokenFilter;
import com.example.library.security.jwt.JwtAuthEntryPoint;
import com.example.library.security.jwt.JwtUtils;
import com.example.library.security.user.BookUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.context.annotation.Configuration
public class BookConfig {
    private final BookUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;

    private static final List<String> SECURED_URLS =
            List.of("/api/v1/library/**"
            ,"api/v1/saved/**");

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter(JwtUtils jwtUtils, BookUserDetailsService userDetailsService) {
        return new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtils jwtUtils) throws Exception{
        http.csrf(AbstractHttpConfigurer:: disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[] :: new)).authenticated()
                        .anyRequest().permitAll());
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(jwtUtils, userDetailsService ), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }























//    @Bean
//    @Transactional
//    CommandLineRunner commandLineRunner(
//            BookRepository bookRepository,
//            BookCollectionRepository collectionRepository) {
//        return args -> {
//            // First save all collections
//            BookCollection collection1 = collectionRepository.save(new BookCollection("Volvo"));
//            BookCollection collection2 = collectionRepository.save(new BookCollection("Mazda"));
//            BookCollection collection3 = collectionRepository.save(new BookCollection("Ford"));
//            BookCollection collection4 = collectionRepository.save(new BookCollection("BMW"));
//            BookCollection collection5 = collectionRepository.save(new BookCollection("Henny"));
//
//            // Create sets of collections
//            Set<BookCollection> collections1 = new HashSet<>();
//            collections1.add(collection1);
//            collections1.add(collection2);
//            collections1.add(collection3);
//            collections1.add(collection4);
//
//            Set<BookCollection> collections2 = new HashSet<>();
//            collections2.add(collection5);
//
//            // Create and save books with the collections
//            Book book1 = new Book("Book 1", "Author 1", 22, "w", "hello", 2025, collections1);
//            Book book2 = new Book("Book 2", "Author 2", 45, "e", "hi", 2025, collections2);
//
//            // Save the books
//            bookRepository.save(book1);
//            bookRepository.save(book2);
//        };
//    }
}