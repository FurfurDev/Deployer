package net.furfurmc.gradle.deployer.networks;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.htmlunit.HttpMethod;
import org.htmlunit.WebRequest;
import org.htmlunit.WebResponse;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.json.JsonFormat;

public class CurseforgeWebClient extends DeployerWebClient
{
    static private final String CURSEFORGE_DOMAIN = "api.curseforge.com";
    static private final String DEFAULT_API_KEY   = "$2a$10$wXpdRpnECM/C6YaxnT8dQ.DAeiO6fx/neuug4CFpaGT.MrMjf6Mtm";
    static private final String API_KEY_HEADER    = "x-api-key";
    
    private String apiKey;

    public CurseforgeWebClient()
    {
        this.apiKey = CurseforgeWebClient.DEFAULT_API_KEY;
        super.getWebClient().addRequestHeader(CurseforgeWebClient.API_KEY_HEADER, apiKey);
    }

    public CurseforgeWebClient(String apiKey)
    {
        this.apiKey = apiKey;
        super.getWebClient().addRequestHeader(CurseforgeWebClient.API_KEY_HEADER, apiKey);
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setApiKey(String apiKey)
    {
        super.getWebClient().removeRequestHeader(CurseforgeWebClient.API_KEY_HEADER);
        super.getWebClient().addRequestHeader(CurseforgeWebClient.API_KEY_HEADER, apiKey);
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

    public Config getModFile(String modId, String fileId) throws URISyntaxException, IOException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(CurseforgeWebClient.CURSEFORGE_DOMAIN)
            .append("/v1/mods/")
            .append(modId)
            .append("/files/")
            .append(fileId);
        return this.getRest(new URI(stringBuilder.toString()));
    }

    @SuppressWarnings("unchecked")
    public String getModFileHash(String modId, String fileId) throws URISyntaxException, IOException
    {
        var modFile = this.getModFile(modId, fileId);
        return ((ArrayList<Config>) modFile.get("data.hashes")).getFirst().get("value");
    }

    public String getModFileDownloadUrl(String modId, String fileId) throws URISyntaxException, IOException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(CurseforgeWebClient.CURSEFORGE_DOMAIN)
            .append("/v1/mods/")
            .append(modId)
            .append("/files/")
            .append(fileId)
            .append("/download-url");
        return this.getRest(new URI(stringBuilder.toString())).get("data");
    }

    public WebResponse downloadModFile(String modId, String fileId) throws URISyntaxException, IOException
    {
        var modFileDownloadUrl = this.getModFileDownloadUrl(modId, fileId);
        var webRequest         = new WebRequest(new URI(modFileDownloadUrl).toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/octet-stream");
        return super.getResponse(webRequest);
    }
}
