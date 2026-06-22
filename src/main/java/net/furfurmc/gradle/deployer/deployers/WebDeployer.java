package net.furfurmc.gradle.deployer.deployers;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.htmlunit.HttpMethod;
import org.htmlunit.WebRequest;
import net.furfurmc.gradle.deployer.common.CacheDocument;
import net.furfurmc.gradle.deployer.common.HashBuilder;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.WebEntity;
import net.furfurmc.gradle.deployer.webclient.DeployerWebClient;

public class WebDeployer extends AbstractDeployer
{
    private DeployerWebClient webClient = new DeployerWebClient();

    public WebDeployer(File cacheDirectory)
    {
        super(WebEntity.class, cacheDirectory);
    }

    @Override
    public void deploy(AbstractEntity entity)
    {
        super.checkEntityClass(entity);
        try
        {
            // set deployEntity
            var deployEntity = (WebEntity) entity;
            var deployFile   = new File(deployEntity.destination, deployEntity.fileName);

            // make cache document
            var cacheDocument = new CacheDocument(super.getCacheDirectory(), deployEntity.name);

            // set custom vars
            var downloadUrl = new URI(deployEntity.url);

            // make deploy hash
            var hashBuilder = new HashBuilder();
            var deployHash  = hashBuilder.makeUrlHash(downloadUrl);
            if (cacheDocument.contains("hash"))
            {
                var cacheHash = cacheDocument.getField("hash");
                if (deployHash.equals(cacheHash)) return;
            }

            // update cache file
            cacheDocument.setField("hash", deployHash);

            // createDirectories
            deployEntity.destination.mkdirs();

            // deploy
            var webRequest = new WebRequest(downloadUrl.toURL(), HttpMethod.GET);
            webRequest.setAdditionalHeader("Accept", "application/octet-stream");
            try (InputStream inputStream = webClient.getResponse(webRequest).getContentAsStream())
            {
                Files.copy(inputStream, deployFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy UrlMineEntity.", exception);
        }
    }
}
