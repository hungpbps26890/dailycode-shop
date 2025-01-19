package com.dev.shop.services.user;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.requests.UserCreateRequest;
import com.dev.shop.domain.dtos.requests.UserUpdateRequest;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.domain.entities.Role;
import com.dev.shop.domain.entities.User;
import com.dev.shop.domain.enums.RoleEnum;
import com.dev.shop.exceptions.AlreadyExistsException;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.mappers.user.IUserMapper;
import com.dev.shop.repositories.CartRepository;
import com.dev.shop.repositories.RoleRepository;
import com.dev.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final IUserMapper userMapper;

    private final CartRepository cartRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AlreadyExistsException(request.getEmail() + ErrorMessage.ALREADY_EXISTS);

        User userToCreate = userMapper.toUser(request);

        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER.name())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROLE_NOT_FOUND));

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        userToCreate.setRoles(roles);

        User savedUser = userRepository.save(userToCreate);

        Cart cart = new Cart();
        cart.setUser(savedUser);

        Cart savedCart = cartRepository.save(cart);

        savedUser.setCart(savedCart);

        return userRepository.save(savedUser);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    @Override
    public User updateUser(Long id, UserUpdateRequest request) {
        User user = getUserById(id);

        return userRepository.save(userMapper.updateUser(user, request));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        userRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
                        }
                );
    }
}
