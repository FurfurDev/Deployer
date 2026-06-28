// CurseforgeRestClient.java : main
package net.furfurmc.gradle.deployer.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.htmlunit.WebClient;
import org.htmlunit.WebResponse;
import com.electronwill.nightconfig.core.Config;

public class CurseforgeRestClient extends RestClientBase
{
    static private final String CURSEFORGE_DOMAIN = "api.curseforge.com";
    static private final String DEFAULT_API_KEY   = "$2a$10$wXpdRpnECM/C6YaxnT8dQ.DAeiO6fx/neuug4CFpaGT.MrMjf6Mtm";
    static private final String API_KEY_HEADER    = "x-api-key";

    public CurseforgeRestClient(WebClient webClient)
    {
        this(webClient, CurseforgeRestClient.DEFAULT_API_KEY);
    }

    public CurseforgeRestClient(WebClient webClient, String apiKey)
    {
        super(webClient, CurseforgeRestClient.API_KEY_HEADER, apiKey);
    }

    public void setApiKey(String apiKey)
    {
        super.setApiKey(CurseforgeRestClient.API_KEY_HEADER, apiKey);;
    }

    public Config getModFile(String modId, String fileId) throws IOException, URISyntaxException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(CurseforgeRestClient.CURSEFORGE_DOMAIN)
            .append("/v1/mods/")
            .append(modId)
            .append("/files/")
            .append(fileId);
        return super.getRest(new URI(stringBuilder.toString()));
    }

    @SuppressWarnings("unchecked")
    public String getModFileHash(String modId, String fileId) throws IOException, URISyntaxException
    {
        return ((ArrayList<Config>) this.getModFile(modId, fileId).get("data.hashes")).getFirst().get("value");
    }

    public String getModFileDownloadUrl(String modId, String fileId) throws IOException, URISyntaxException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(CurseforgeRestClient.CURSEFORGE_DOMAIN)
            .append("/v1/mods/")
            .append(modId)
            .append("/files/")
            .append(fileId)
            .append("/download-url");
        return this.getRest(new URI(stringBuilder.toString())).get("data");
    }

    public WebResponse downloadModFile(String modId, String fileId) throws IOException, URISyntaxException
    {
        return super.getFile(new URI(this.getModFileDownloadUrl(modId, fileId)));
    }
}
