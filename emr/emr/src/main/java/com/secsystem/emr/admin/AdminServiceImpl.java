package com.secsystem.emr.admin;

import com.secsystem.emr.exceptions.EntityNotFoundException;
import com.secsystem.emr.shared.pagination.GenericFilter;
import com.secsystem.emr.shared.pagination.GenericService;
import com.secsystem.emr.shared.pagination.PagedResponse;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.UserRepository;
import com.secsystem.emr.user.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminServiceImpl implements AdminService{


    private final UserRepository userRepository;
    private final GenericService<User> genericService;

    public AdminServiceImpl(UserRepository userRepository, GenericService<User> genericService) {
        this.userRepository = userRepository;
        this.genericService = genericService;
    }

    @Override
    public User userDetail(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public PagedResponse<UserResponseDto> getAllUsersPaginated(String email, int page, int size) {
        // Create the filter for email
        GenericFilter filter = new GenericFilter();
        if (email != null) {
            filter.addFilter("email", email);
        }

        Pageable pageable = PageRequest.of(page, size);

        // Fetch filtered data
        Page<User> userPage = genericService.getFilteredData(filter, pageable, User.class);

        // Map User entities to UserResponseDto
        List<UserResponseDto> userResponseDtos = userPage.getContent().stream().map(user ->
                new UserResponseDto(
                        user.getFirstName() + " " + user.getLastName(), // Concatenating first and last name
                        user.getEmail(),
                        user.getRole().getName().toString()
                )
        ).collect(Collectors.toList());

        // Create and return a paged response
        return new PagedResponse<>(userPage, userResponseDtos);
    }
}
