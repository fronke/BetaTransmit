package nl.stil4m.transmission.rpc;

import nl.stil4m.transmission.http.HostConfiguration;

import java.net.URI;

public class RpcConfiguration implements HostConfiguration {

    private URI host;
    private String authorization;

    public URI getHost() {
        return host;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setHost(URI host) {
        this.host = host;
    }

    public void setAuthorization(String authorization)  { this.authorization = authorization; }
}
