package com.aliashik.service;

import java.util.List;

public interface UserRoleService {
    List<String>getRolesByUsername(String username);
}
