package com.lateral.osint.github.model.mapper;

import com.lateral.osint.github.model.db.Org;
import com.lateral.osint.github.model.github.OrgJson;

public class OrgToDB {
    public static Org map(OrgJson from) {
        Org org = new Org();
        org.setName(from.getLogin());
        org.setRaw_location(from.getLocation());
        return org;
    }
}
