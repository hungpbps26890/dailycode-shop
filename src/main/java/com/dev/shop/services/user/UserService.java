package com.dev.shop.services.user;

import com.dev.shop.constants.ErrorMessage;
import com.dev.shop.domain.dtos.requests.UserCreateRequest;
import com.dev.shop.domain.dtos.requests.UserUpdateRequest;
import com.dev.shop.domain.entities.Cart;
import com.dev.shop.domain.entities.User;
import com.dev.shop.exceptions.AlreadyExistsException;
import com.dev.shop.exceptions.ResourceNotFoundException;
import com.dev.shop.mappers.user.IUserMapper;
import com.dev.shop.repositories.CartRepository;
import com.dev.shop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final IUserMapper userMapper;

    private final CartRepository cartRepository;

    @Override
    public User createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AlreadyExistsException(request.getEmail() + ErrorMessage.ALREADY_EXISTS);

        User userToCreate = userMapper.toUser(request);

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
