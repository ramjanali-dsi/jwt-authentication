package com.aliashik.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRoleService {
    private  Map<String, List<String>> userRoles;

    public UserRoleService() {
        this.userRoles = new HashMap<>();
        this.userRoles.put("user", new ArrayList<String>() {{add("ROLE_USER");}});
        this.userRoles.put("admin", new ArrayList<String>() {{add("ROLE_ADMIN");}});
        this.userRoles.put("client", new ArrayList<String>() {{add("ROLE_CLIENT");}});
    }
    public List<String> getRolesByUsername(String username) {
        return this.userRoles.get(username);
    }
}
