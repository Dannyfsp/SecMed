package com.secsystem.emr.admin;

import com.secsystem.emr.exceptions.EntityNotFoundException;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService{


    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User userDetail(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
