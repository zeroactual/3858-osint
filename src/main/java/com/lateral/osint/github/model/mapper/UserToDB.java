package com.lateral.osint.github.model.mapper;

import com.lateral.osint.github.model.db.User;
import com.lateral.osint.github.model.github.UserJson;

public class UserToDB {
    public static User map(UserJson from) {
        User user = new User();
        user.setName(from.getLogin());
        user.setRaw_location(from.getLocation());
        return user;
    }
}
