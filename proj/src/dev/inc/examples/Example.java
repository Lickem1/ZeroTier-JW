package dev.inc.examples;

import dev.inc.network.ZTNetwork;
import dev.inc.network.member.ZTMemberData;

import java.util.Collection;
import java.util.List;

public class Example {

    public static void main(String[] args) {

        String network_id = ""; // Your network ID
        String api_token = ""; // Your api token

        ZTNetwork network = new ZTNetwork(network_id, api_token);

        Collection<ZTMemberData> all = network.getZtMembers().values();
        List<ZTMemberData> active = network.getActiveMembers();

        String prefix;
        String localIP;

        System.out.println("All members:");
        for(ZTMemberData memberData : all) {
            if(memberData.isOnline() && memberData.assignmentManager().isAuthorized()) prefix = " • ";
            else if(!memberData.assignmentManager().isAuthorized()) prefix = " ? ";
            else prefix = " x ";

            if(memberData.assignmentManager().getIpAssignments().isEmpty()) localIP = "No IP Assigned";
            else localIP = memberData.assignmentManager().getIpAssignments().get(0);

            System.out.println(prefix + memberData.getName() + " | " + memberData.getPhysicalAddress() + " | " + localIP);
        }
        System.out.println();
        System.out.println("Online Members: " + active.size() + "/" + all.size());
        for(ZTMemberData data : active) {
            System.out.println(" • " + data.getName() + " | " + data.getPhysicalAddress() + " | " + data.assignmentManager().getIpAssignments().get(0));
        }
    }
}
