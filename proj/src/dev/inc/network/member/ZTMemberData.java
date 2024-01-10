package dev.inc.network.member;

import dev.inc.network.ZTNetwork;
import dev.inc.network.util.ZTNetworkUtil;
import lombok.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

@Getter
public class ZTMemberData {

    private final String networkId, name, physicalAddress, nodeId;
    @Setter private String description;
    private boolean dummyMember;
    private final long lastSeen;
    @Getter(AccessLevel.NONE) private final ZTMemberConf config;

    public ZTMemberData(String networkId, String name, String physicalAddress, String description, String nodeId, long lastSeen, ZTMemberConf config) {
        this.networkId = networkId;
        this.name = name;
        this.physicalAddress = physicalAddress;
        this.description = description;
        this.nodeId = nodeId;
        this.lastSeen = lastSeen;
        this.config = config;
    }
    public ZTMemberData() {
        this.networkId = "404";
        this.name = "404";
        this.physicalAddress = "404";
        this.description = "404";
        this.nodeId = "404";
        this.lastSeen = 404;
        this.config = new ZTMemberConf(new ArrayList<>(), false);
        this.dummyMember = true;
    }


    public boolean isOnline() {
        long diff = lastSeen - System.currentTimeMillis();
        long diffMinutes = diff / (60 * 1000) % 60;

        return (diffMinutes >= 0);
    }

    // Post data to ZeroTier
    public boolean post(ZTNetwork network) {
        if(this.dummyMember) return false;

        try {
            MemberPayload payload = new MemberPayload(this.name, this.description, config);
            String parsed_payload = ZTNetworkUtil.getGson().toJson(payload);
            String url = String.format(ZTNetworkUtil.getZtMemberURL(), this.networkId, this.nodeId);

            Jsoup.connect(url)
                    .ignoreContentType(true).userAgent(ZTNetworkUtil.getUserAgent())
                    .header("Authorization", "token " + network.getToken())
                    .requestBody(parsed_payload)
                    .method(Connection.Method.POST)
                    .execute();

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    public ZTMemberConf assignmentManager() {
        return this.config;
    }

}
