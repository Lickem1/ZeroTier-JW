package dev.inc.network.member;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ZTMemberConf {

    private final List<String> ipAssignments;
    @Setter private boolean authorized;

    public ZTMemberConf(List<String> ipAssignments, boolean authorized) {
        this.ipAssignments = ipAssignments;
        this.authorized = authorized;
    }
}
