// HtmlUnitService.java : main
package net.furfurmc.gradle.deployer.service;

import org.gradle.api.provider.Property;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;

public abstract class HtmlUnitService implements BuildService<HtmlUnitService.Params>, AutoCloseable
{
    public static final String NAME = "HtmlUnitService";

    public interface Params extends BuildServiceParameters
    {
        Property<String> getUserAgent();
    }

    private WebClient webClient;
    
    public HtmlUnitService()
    {
        String userAgent = getParameters().getUserAgent().get();
        
        final var builder = new BrowserVersion.BrowserVersionBuilder(BrowserVersion.BEST_SUPPORTED);
        builder.setUserAgent(userAgent);
        BrowserVersion version = builder.build();

        this.webClient = new WebClient(version);
    }

    public WebClient getWebClient()
    {
        return this.webClient;
    }

    @Override
    public void close()
    {
        this.webClient.close();
    }
}
