package com.example.lotte.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // A constants class should not be instantiated
public class UserSortConstants {

    // Use Set when using JPQL:
    public static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "username"
    );
    public static final String DEFAULT_SORT_FIELD = "username";
    public static final String DEFAULT_SORT_DIRECTION = "desc";
    public static final int MIN_PAGE = 0;
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 100;
}
