package net.furfurmc.gradle.deployer.webclient;

import java.io.IOException;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import net.furfurmc.gradle.deployer.instance.DeployerUserAgent;

public class DeployerWebClient extends WebClient
{
    private DeployerUserAgent userAgent;

    public DeployerWebClient()
    {
        super(DeployerWebClient.buildBrowserVersion(DeployerUserAgent.getInstance()));
        this.userAgent = DeployerUserAgent.getInstance();
    }

    public DeployerWebClient(DeployerUserAgent userAgent)
    {
        super(DeployerWebClient.buildBrowserVersion(userAgent));
        this.userAgent = userAgent;
    }

    public WebResponse getResponse(WebRequest webRequest) throws IOException
    {
        return super.loadWebResponse(webRequest);
    }

    public DeployerUserAgent getUserAgent()
    {
        return this.userAgent;
    }

    public void setUserAgent(DeployerUserAgent userAgent)
    {
        this.userAgent = userAgent;
        super.removeRequestHeader("User-Agent");
        super.addRequestHeader("User-Agent", userAgent.toString());
    }

    private static BrowserVersion buildBrowserVersion(DeployerUserAgent userAgent)
    {
        final var builder = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.BEST_SUPPORTED);
        builder.setUserAgent(userAgent.toString());
        return builder.build();
    }
}
