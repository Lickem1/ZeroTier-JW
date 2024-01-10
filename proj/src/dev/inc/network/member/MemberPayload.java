package dev.inc.network.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberPayload {

    private final String name, description;
    private final ZTMemberConf config;
}
