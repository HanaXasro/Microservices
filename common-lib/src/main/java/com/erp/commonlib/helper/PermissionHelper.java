package com.erp.commonlib.helper;

import com.erp.commonlib.security.Permissions;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class PermissionHelper {


    public static String perm(String entity, String action) {
        return entity + ":" + action;
    }

    public static List<String> getAllPermissions() {
        return Arrays.stream(Permissions.class.getDeclaredFields())
                .filter(field -> field.getType().equals(String.class))
                .filter(field -> Modifier.isPublic(field.getModifiers()))
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field -> {
                    try {
                        return (String) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
