# ZeroTier-JW
A Java api that helps you manage your ZeroTier network (https://www.zerotier.com/). The library requires an API Access Token and lets you query the status of ZeroTier and create/update/delete the network alongside its members.
This project was inspired by my sudden interest in ZeroTier framework as well as the out-of-date wrapper made by edouardswiac and mcondraelli (https://github.com/edouardswiac/zerotier-api-java)


## Requirements
- Java 8+
- A ZeroTier API access token (get it from https://my.zerotier.com/ > Account)


## Installation
The project is not yet published to maven central. SOON

The api uses:
  • [Jsoup](http://square.github.io/okhttp/) for HTML parsing.
  • [Gson](https://github.com/google/gson) for JSON.


## Features
The [`ZTService`](src/main/java/com/github/edouardswiac/zerotier/ZTService.java) interface describes the service and its available methods. The provided implementation, [`ZTServiceImpl`](src/main/java/com/github/edouardswiac/zerotier/ZTServiceImpl.java), makes HTTP requests to ZeroTier's Central REST API.

What you can achieve with ZeroTier-JW:
- ***status*** query the status of ZeroTier, as well as the status of members
- ***member*** create a member, update a member, and delete a member.

If you need more fields and want to contribute to the project, please submit a pull request with your changes along some integration testing.

## Examples
```java
String network_id = "My network ID"; // Your network ID
String api_token = "My API Token"; // Your api token

ZTNetwork network = new ZTNetwork(network_id, api_token);

// Grab a list of all the active users
List<ZTMemberData> active = network.getActiveMembers();

System.out.println(String.format("There are currently %s users online on this network.", active.size())));
for(ZTMemberData data : active) {
    System.out.println(" • " + data.getName() + " | " + data.getPhysicalAddress() + " | " + data.assignmentManager().getIpAssignments().get(0));
}
```
Also refer to [`Example`](proj/src/dev/inc/examples/Example.java)
