package com.projectmd5.service;

import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.entity.User;
import org.springframework.data.domain.Pageable;

public interface IUserService extends IGenericService<User, Long> {
   Pageable getPageable(int pageNo, int pageSize, String sortBy, String sortDir);
   UserPageResponse getAll(Pageable pageable);
   UserPageResponse findByNameWithPaging(String name, Pageable pageable);
}
