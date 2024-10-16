package com.secsystem.emr.admin;

import com.secsystem.emr.shared.pagination.GenericFilter;
import com.secsystem.emr.shared.pagination.GenericService;
import com.secsystem.emr.shared.pagination.PagedResponse;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.dto.response.UserResponseDto;
import com.secsystem.emr.utils.constants.responsehandler.ResponseHandler;
import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/admin")
public class AdminController {
    private final AdminService adminService;
    private final GenericService<User> genericService;


    public AdminController(AdminService adminService, GenericService<User> genericService) {
        this.adminService = adminService;
        this.genericService = genericService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSingleUser(@PathVariable Integer id) {
        User userDetail = adminService.userDetail(id);
        return ResponseHandler.responseBuilder("user profile retrieved", HttpStatus.OK, userDetail);
    }

    @GetMapping("/users/paginated")
    public ResponseEntity<?> getAllUsersPaginated(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PagedResponse<UserResponseDto> pagedResponse = adminService.getAllUsersPaginated(email, page, size);
        return ResponseHandler.responseBuilder("Users retrieved", HttpStatus.OK, pagedResponse);
    }



}