package com.projectmd5.service;

import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.entity.User;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGenericService<User, Long> {
   Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir);
   Boolean existsByUsername(String username);
   Boolean existsByEmail(String email);
   Boolean existsByPhone(String phone);
   UserPageResponse getAll(Pageable pageable);
   UserPageResponse findByName(String name, Pageable pageable);
}
