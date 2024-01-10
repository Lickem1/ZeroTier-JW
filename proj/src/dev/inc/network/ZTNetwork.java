package dev.inc.network;

import com.google.gson.Gson;
import dev.inc.network.util.SearchType;
import dev.inc.network.util.ZTNetworkUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import dev.inc.network.member.ZTMemberData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class ZTNetwork {

    private final String token;
    private final String id;
    private String description;
    private ZTNetworkConf config;
    private int onlineMemberCount, authorizedMemberCount, totalMemberCount;
    private HashMap<String, ZTMemberData> ztMembers;

    public ZTNetwork(String id, String token) {
        this.id = id;
        this.token = token;
        updateLocal();
    }

    private void assign(ZTNetwork assign) {
        this.description = assign.description;
        this.config = assign.config;
        this.onlineMemberCount = assign.onlineMemberCount;
        this.authorizedMemberCount = assign.authorizedMemberCount;
        this.totalMemberCount = assign.totalMemberCount;
        this.ztMembers = new HashMap<>();
    }

    @SneakyThrows
    public void updateLocal() {
        Gson gson = ZTNetworkUtil.getGson();
        String networkUrl = String.format(ZTNetworkUtil.getZtNetworkURL(), this.id);
        String memberUrl = networkUrl + "/member";
        String userAgent = ZTNetworkUtil.getUserAgent();

        Document network_doc = Jsoup.connect(networkUrl).ignoreContentType(true).userAgent(userAgent)
                .header("Authorization", "token " + this.token).get();

        Document member_doc = Jsoup.connect(memberUrl).ignoreContentType(true).userAgent(userAgent)
                .header("Authorization", "token " + this.token).get();

        String networkJson = network_doc.text();
        String memberJson = member_doc.text();

        assign(gson.fromJson(networkJson, ZTNetwork.class));
        ZTMemberData[] members = gson.fromJson(memberJson, ZTMemberData[].class);

        for(ZTMemberData m : members) {
            this.ztMembers.put(m.getNodeId(), m);
        }
    }

    public ZTMemberData searchMember(String query, SearchType type) {
        ZTMemberData[] found = {dummyMember()};
        switch (type) {
            case ID: return ztMembers.get(query.toLowerCase());

            case NAME:
                ztMembers.values().forEach(m -> {
                    if(m.getName().equalsIgnoreCase(query)) found[0] = m;
                });
                return found[0];

            case IP:
                // searches external and zt ip
                ztMembers.values().forEach(m -> {
                    if(m.getPhysicalAddress().equalsIgnoreCase(query)) found[0] = m;
                    else if(m.assignmentManager().getIpAssignments().contains(query)) found[0] = m;
                });
                return found[0];

            default: return found[0];
        }
    }

    public List<ZTMemberData> getActiveMembers() {
        List<ZTMemberData> activeMemberlist = new ArrayList<>();
        ztMembers.values().forEach(member -> {

            long diff = member.getLastSeen() - System.currentTimeMillis();
            long diffMinutes = diff / (60 * 1000) % 60;

            if(!member.assignmentManager().isAuthorized()) return;
            if (diffMinutes >= 0) activeMemberlist.add(member);

        });
        return activeMemberlist;
    }

    private ZTMemberData dummyMember() {
        return new ZTMemberData();
    }
}
