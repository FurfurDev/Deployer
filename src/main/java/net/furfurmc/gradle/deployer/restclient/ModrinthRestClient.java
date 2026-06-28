// ModrinthRestClient.java : main
package net.furfurmc.gradle.deployer.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.htmlunit.WebClient;
import org.htmlunit.WebResponse;
import com.electronwill.nightconfig.core.Config;

public class ModrinthRestClient extends RestClientBase
{
    static private final String MODRINTH_DOMAIN = "api.modrinth.com";
    static private final String API_KEY_HEADER  = "Authorization";
    
    public ModrinthRestClient(WebClient webClient)
    {
        this(webClient, null);
    }

    public ModrinthRestClient(WebClient webClient, String apiKey)
    {
        super(webClient, ModrinthRestClient.API_KEY_HEADER, apiKey);
    }

    public void setApiKey(String apiKey)
    {
        super.setApiKey(ModrinthRestClient.API_KEY_HEADER, apiKey);;
    }

    public Config getVersion(String versionId) throws IOException, URISyntaxException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(ModrinthRestClient.MODRINTH_DOMAIN)
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
        return (String) this.getVersionFile(versionId, fileName).get("hashes.sha512");
    }

    public String getVersionFileDownloadUrl(String versionId, String fileName) throws IOException, URISyntaxException
    {
        return (String) this.getVersionFile(versionId, fileName).get("url");
    }

    public WebResponse downloadVersionFile(String versionId, String fileName) throws IOException, URISyntaxException
    {
        return super.getFile(new URI(this.getVersionFileDownloadUrl(versionId, fileName)));
    }
}
