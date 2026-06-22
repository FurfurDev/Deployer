package net.furfurmc.gradle.deployer.webclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.htmlunit.HttpMethod;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.json.JsonFormat;

public class ModrinthWebClient extends DeployerWebClient
{
    static private final String MODRINTH_DOMAIN = "api.modrinth.com";
    static private final String API_KEY_HEADER  = "Authorization";
    
    private String apiKey;

    public ModrinthWebClient()
    {
        this.apiKey = null;
    }

    public ModrinthWebClient(String apiKey)
    {
        this.apiKey = apiKey;
        super.addRequestHeader(ModrinthWebClient.API_KEY_HEADER, apiKey);
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setApiKey(String apiKey)
    {
        if (this.apiKey != null) super.removeRequestHeader(ModrinthWebClient.API_KEY_HEADER);
        super.addRequestHeader(ModrinthWebClient.API_KEY_HEADER, apiKey);
        this.apiKey = apiKey;
    }

    public Config getRest(URI url) throws IOException
    {
        WebRequest webRequest = new WebRequest(url.toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/json");
        var webResponse = super.getResponse(webRequest);
        var jsonFormat  = JsonFormat.fancyInstance();
        var jsonParser  = jsonFormat.createParser();
        return jsonParser.parse(webResponse.getContentAsStream());
    }

    public Config getVersion(String versionId) throws IOException, URISyntaxException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(ModrinthWebClient.MODRINTH_DOMAIN)
            .append("/v2/version/")
            .append(versionId);
        return this.getRest(new URI(stringBuilder.toString()));
    }

    @SuppressWarnings("unchecked")
    public Config getVersionFile(String versionId, String fileName) throws IOException, URISyntaxException
    {
        var version = this.getVersion(versionId);
        for (Config file : (ArrayList<Config>) version.get("files"))
        {
            if (fileName.equals((String) file.get("filename"))) return file;
        }
        return null;
    }

    public String getVersionFileHash(String versionId, String fileName) throws IOException, URISyntaxException
    {
        var file = this.getVersionFile(versionId, fileName);
        return (String) file.get("hashes.sha512");
    }

    public String getVersionFileDownloadUrl(String versionId, String fileName) throws IOException, URISyntaxException
    {
        var file = this.getVersionFile(versionId, fileName);
        return (String) file.get("url");
    }

    public WebResponse downloadVersionFile(String versionId, String fileName) throws IOException, URISyntaxException
    {
        var versionFileDownloadUrl = this.getVersionFileDownloadUrl(versionId, fileName);
        var webRequest             = new WebRequest(new URI(versionFileDownloadUrl).toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/octet-stream");
        return super.getResponse(webRequest);
    }
}
