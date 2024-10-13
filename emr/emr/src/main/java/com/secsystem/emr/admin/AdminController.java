package com.secsystem.emr.admin;

import com.secsystem.emr.user.User;
import com.secsystem.emr.utils.constants.responsehandler.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSingleUser(@PathVariable Integer id) {
        User userDetail = adminService.userDetail(id);
        return ResponseHandler.responseBuilder("user profile retrieved", HttpStatus.OK, userDetail);
    }
}