package com.projectmd5.service;

import com.projectmd5.model.entity.Role;
import com.projectmd5.model.entity.RoleName;

public interface IRoleService extends IGenericService<Role, Long>{
   Role findByRoleName(RoleName roleName);
}
