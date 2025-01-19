package com.dev.shop.security.user;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.entities.User;
import com.dev.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND));

        return ShopUserDetails.buildUserDetails(user);
    }
}
