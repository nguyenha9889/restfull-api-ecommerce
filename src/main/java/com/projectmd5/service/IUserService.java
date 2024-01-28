package com.projectmd5.service;

import com.projectmd5.model.dto.user.UserPageResponse;
import com.projectmd5.model.dto.user.UserResponse;
import com.projectmd5.model.entity.User;

public interface IUserService extends IGenericService<User, Long> {
   UserPageResponse getPageUser(int pageNo, int pageSize, String sortBy, String sortDir);
   UserPageResponse searchWithPaging(String query, int pageNo, int pageSize, String sortBy, String sortDir);
   UserResponse lockOrEnableUser(Long userId, Boolean status);
   UserResponse addRoleUser(Long userId, Long roleId);
   void deleteRoleUser(Long userId, Long roleId);
}
