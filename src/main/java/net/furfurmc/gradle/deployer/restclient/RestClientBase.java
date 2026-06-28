// RestClientBase.java : main
package net.furfurmc.gradle.deployer.restclient;

import java.io.IOException;
import java.net.URI;
import org.htmlunit.HttpMethod;
import org.htmlunit.WebClient;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.json.JsonFormat;

public abstract class RestClientBase
{
    private WebClient webClient;
    private String    apiKeyHeader;
    private String    apiKey;

    public RestClientBase(WebClient webClient)
    {
        this(webClient, null, null);
    }

    public RestClientBase(WebClient webClient, String apiKeyHeader, String apiKey)
    {
        this.webClient = webClient;
        this.setApiKey(apiKeyHeader, apiKey);
    }

    public String getApiKeyHeader()
    {
        return this.apiKeyHeader;
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setApiKey(String apiKeyHeader, String apiKey)
    {
        this.apiKeyHeader = apiKeyHeader;
        this.apiKey       = apiKey;
    }

    public Config getRest(URI url) throws IOException
    {
        WebRequest webRequest = new WebRequest(url.toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/json");
        this.setRequestApiKey(webRequest);
        var webResponse = this.webClient.loadWebResponse(webRequest);
        var jsonFormat  = JsonFormat.fancyInstance();
        var jsonParser  = jsonFormat.createParser();
        return jsonParser.parse(webResponse.getContentAsStream());
    }

    public WebResponse getFile(URI url) throws IOException
    {
        WebRequest webRequest = new WebRequest(url.toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/octet-stream");
        this.setRequestApiKey(webRequest);
        return this.webClient.loadWebResponse(webRequest);
    }

    private void setRequestApiKey(WebRequest webRequest)
    {
        if (this.apiKey != null) webRequest.setAdditionalHeader(this.apiKeyHeader, this.apiKey);
    }
}
