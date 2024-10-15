package com.secsystem.emr.admin;

import com.secsystem.emr.shared.pagination.PagedResponse;
import com.secsystem.emr.user.User;
import com.secsystem.emr.user.dto.response.UserResponseDto;

public interface AdminService {

    User userDetail(Integer id);
    PagedResponse<UserResponseDto> getAllUsersPaginated(String email, int page, int size);

}
