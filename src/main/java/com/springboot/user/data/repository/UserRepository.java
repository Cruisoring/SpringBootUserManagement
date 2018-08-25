package com.springboot.user.data.repository;

import com.springboot.user.data.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * {@link UserModel} {@link JpaRepository }
 */
public interface UserRepository extends JpaRepository<UserModel, Long> {

    List<UserModel> findUserByEmailOrUserName(String email, String userName);

    UserModel findUserByEmail(String email);

    UserModel removeById(Long id);
}
