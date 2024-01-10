package dev.inc.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ZTNetworkConf {

    private final String name;
    private final long lastModified;
    private final List<Route> routes;

    @AllArgsConstructor
    public static class Route {
        private final String target;

        public String getSubnetRange() {
            return target;
        }
    }
}
