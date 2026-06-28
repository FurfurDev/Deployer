// GitHubRestClient.java : main
package net.furfurmc.gradle.deployer.restclient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.htmlunit.WebClient;
import org.htmlunit.WebResponse;
import com.electronwill.nightconfig.core.Config;

public class GitHubRestClient extends RestClientBase
{
    static private final String GITHUB_DOMAIN  = "api.github.com";
    static private final String API_KEY_HEADER = "Authorization";
    
    public GitHubRestClient(WebClient webClient)
    {
        this(webClient, null);
    }

    public GitHubRestClient(WebClient webClient, String apiKey)
    {
        super(webClient, GitHubRestClient.API_KEY_HEADER, apiKey);
    }

    public void setApiKey(String apiKey)
    {
        super.setApiKey(GitHubRestClient.API_KEY_HEADER, apiKey);;
    }

    public Config getReleaseByTag(String owner, String repo, String tag) throws IOException, URISyntaxException
    {
        var stringBuilder = new StringBuilder();
        stringBuilder
            .append("https://")
            .append(GitHubRestClient.GITHUB_DOMAIN)
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
        return this.getReleaseAssetByTag(owner, repo, tag, assetName).get("digest");
    }

    public String getAssetDownloadUrl(String owner, String repo, String tag, String assetName) throws IOException, URISyntaxException
    {
        return this.getReleaseAssetByTag(owner, repo, tag, assetName).get("url");
    }

    public WebResponse downloadAsset(String owner, String repo, String tag, String assetName) throws IOException, URISyntaxException
    {
        return super.getFile(new URI(this.getAssetDownloadUrl(owner, repo, tag, assetName)));
    }
}
