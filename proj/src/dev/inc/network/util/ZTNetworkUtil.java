package dev.inc.network.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;


public class ZTNetworkUtil {

    @Getter private static final Gson gson = new GsonBuilder().create();
    @Getter private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:65.0) Gecko/20100101 Firefox/65.0";
    @Getter private static final String ztNetworkURL = "https://api.zerotier.com/api/v1/network/%s";
    @Getter private static final String ztMemberURL = "https://api.zerotier.com/api/v1/network/%s/member/%s";
}
