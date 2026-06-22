package net.furfurmc.gradle.deployer.deployers;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import net.furfurmc.gradle.deployer.common.CacheDocument;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.GitHubEntity;
import net.furfurmc.gradle.deployer.webclient.GitHubWebClient;

public class GitHubDeployer extends AbstractDeployer
{
    private GitHubWebClient webClient = new GitHubWebClient();
    
    public GitHubDeployer(File cacheDirectory)
    {
        super(GitHubEntity.class, cacheDirectory);
    }

    @Override
    public void deploy(AbstractEntity entity)
    {
        super.checkEntityClass(entity);
        try
        {
            // set deployEntity
            var deployEntity = (GitHubEntity) entity;
            var deployFile   = new File(deployEntity.destination, deployEntity.fileName);

            // make cache document
            var cacheDocument = new CacheDocument(super.getCacheDirectory(), deployEntity.name);

            // make deploy hash
            var deployHash  = webClient.getAssetHash(deployEntity.owner, deployEntity.repo, deployEntity.tag, deployEntity.asset);
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
            var webResponse = webClient.downloadAsset(deployEntity.owner, deployEntity.repo, deployEntity.tag, deployEntity.asset);
            try (InputStream inputStream = webResponse.getContentAsStream())
            {
                Files.copy(inputStream, deployFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy GitHubEntity.", exception);
        }
    }
}
