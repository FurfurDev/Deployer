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

public class GitHubWebClient extends DeployerWebClient
{
    static private final String GITHUB_DOMAIN  = "api.github.com";
    static private final String API_KEY_HEADER = "Authorization";
    
    private String apiKey;

    public GitHubWebClient()
    {
        this.apiKey = null;
    }

    public GitHubWebClient(String apiKey)
    {
        this.apiKey = apiKey;
        super.getWebClient().addRequestHeader(GitHubWebClient.API_KEY_HEADER, apiKey);
    }

    public String getApiKey()
    {
        return this.apiKey;
    }

    public void setApiKey(String apiKey)
    {
        if (this.apiKey != null) super.getWebClient().removeRequestHeader(GitHubWebClient.API_KEY_HEADER);
        super.getWebClient().addRequestHeader(GitHubWebClient.API_KEY_HEADER, apiKey);
        this.apiKey = apiKey;
    }

    public Config getRest(URI url) throws IOException
    {
        WebRequest webRequest = new WebRequest(url.toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/vnd.github+json");
        var webResponse = super.getResponse(webRequest);
        var jsonFormat  = JsonFormat.fancyInstance();
        var jsonParser  = jsonFormat.createParser();
        return jsonParser.parse(webResponse.getContentAsStream());
    }

    public Config getReleaseByTag(String owner, String repo, String tag) throws IOException, URISyntaxException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(GitHubWebClient.GITHUB_DOMAIN)
            .append("/repos/")
            .append(owner)
            .append("/")
            .append(repo)
            .append("/releases/tags/")
            .append(tag);
        return this.getRest(new URI(stringBuilder.toString()));
    }

    @SuppressWarnings("unchecked")
    public Config getReleaseAssetByTag(String owner, String repo, String tag, String assetName) throws IOException, URISyntaxException
    {
        var release = this.getReleaseByTag(owner, repo, tag);
        for (Config asset : (ArrayList<Config>) release.get("assets"))
        {
            if (assetName.equals((String) asset.get("name"))) return asset;
        }
        return null;
    }

    public String getAssetHash(String owner, String repo, String tag, String assetName) throws IOException, URISyntaxException
    {
        var asset = this.getReleaseAssetByTag(owner, repo, tag, assetName);
        return asset.get("digest");
    }

    public String getAssetDownloadUrl(String owner, String repo, String tag, String assetName) throws IOException, URISyntaxException
    {
        var asset = this.getReleaseAssetByTag(owner, repo, tag, assetName);
        return asset.get("url");
    }

    public WebResponse downloadAsset(String owner, String repo, String tag, String assetName) throws IOException, URISyntaxException
    {
        var assetDownloadUrl = this.getAssetDownloadUrl(owner, repo, tag, assetName);
        var webRequest       = new WebRequest(new URI(assetDownloadUrl).toURL(), HttpMethod.GET);
        webRequest.setAdditionalHeader("Accept", "application/octet-stream");
        return super.getResponse(webRequest);
    }
}
