package com.example.home.configuration
//
//import com.example.home.service.HomeUserDetailsService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher
//
//
//@Configuration
//@EnableWebSecurity
//class HomeSecurityConfiguration(
//    @Autowired
//    private val userDetailsService: HomeUserDetailsService,
//    @Autowired
//    private val passwordEncoder: PasswordEncoder
//) {
//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
//        .authorizeHttpRequests {
//            it
//                .requestMatchers("/register/**").permitAll()
//                .requestMatchers("/").hasRole("USER")
//                .requestMatchers("/users").hasRole("ADMIN")
//        }
//        .formLogin {
//            it.loginPage("/signin")
//                .loginProcessingUrl("/signin")
//                .defaultSuccessUrl("/")
//                .permitAll()
//        }
//        .logout {
//            it.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
//                .permitAll()
//        }
//        .build()
//
//
//
//
//    @Autowired
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth
//            .userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder)
//    }
//}