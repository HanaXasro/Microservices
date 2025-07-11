package com.erp.commonlib.security;

import com.erp.commonlib.helper.PermissionHelper;

public final class Permissions {

    // User permissions
    public static final String UserView = PermissionHelper.perm("User", "View");
    public static final String UserList = PermissionHelper.perm("User", "List");
    public static final String UserEdit = PermissionHelper.perm("User", "Edit");
    public static final String UserDelete = PermissionHelper.perm("User", "Delete");

    // Role permissions
    public static final String RoleView = PermissionHelper.perm("Role", "View");
    public static final String RoleList = PermissionHelper.perm("Role", "List");
    public static final String RoleEdit = PermissionHelper.perm("Role", "Edit");
    public static final String RoleDelete = PermissionHelper.perm("Role", "Delete");
}
