package com.aliashik.service.impl;

import com.aliashik.service.UserRoleService;

import java.util.ArrayList;
import java.util.List;

public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public List<String> getRolesByUsername(String username) {
        return new ArrayList<String>(){{
            add("ROLE_USER");
            add("ROLE_CLIENT");
        }};
    }
}
