package net.furfurmc.gradle.deployer.networks;

import java.io.IOException;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;

public class DeployerWebClient
{
    private DeployerUserAgent userAgent;
    private WebClient         webClient;

    public DeployerWebClient()
    {
        this.userAgent = DeployerUserAgent.getInstance();
        this.webClient = this.makeWebClient();
    }

    public DeployerWebClient(DeployerUserAgent userAgent)
    {
        this.userAgent = userAgent;
        this.webClient = this.makeWebClient();
    }

    private WebClient makeWebClient()
    {
        final var builder = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.BEST_SUPPORTED);
        builder.setUserAgent(this.userAgent.getUserAgent());
        final var browserVersion = builder.build();
        return new WebClient(browserVersion);
    }

    public WebResponse getResponse(WebRequest webRequest) throws IOException
    {
        return this.webClient.loadWebResponse(webRequest);
    }

    public DeployerUserAgent getUserAgent()
    {
        return this.userAgent;
    }

    public WebClient getWebClient()
    {
        return this.webClient;
    }

    public void setUserAgent(DeployerUserAgent userAgent)
    {
        this.userAgent = userAgent;
        this.webClient.removeRequestHeader("User-Agent");
        this.webClient.addRequestHeader("User-Agent", userAgent.getUserAgent());
    }

    public void setWebClient(WebClient webClient)
    {
        this.webClient = webClient;
    }
}
