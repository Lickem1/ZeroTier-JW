package dev.inc.network.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchType {

    ID("This search type uses the clients ZT address to find a user."),
    NAME("This search type uses the clients display name and returns the Member Object (You set the username for them in the ZT members configuration panel)."),
    IP("This search type uses both external and ZT IP addresses to find a user.");

    private final String description;
}
