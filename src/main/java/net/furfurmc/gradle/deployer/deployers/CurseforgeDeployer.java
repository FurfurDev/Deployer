package net.furfurmc.gradle.deployer.deployers;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import net.furfurmc.gradle.deployer.common.CacheDocument;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.CurseforgeEntity;
import net.furfurmc.gradle.deployer.networks.CurseforgeWebClient;

public class CurseforgeDeployer extends AbstractDeployer
{
    private CurseforgeWebClient webClient = new CurseforgeWebClient();

    public CurseforgeDeployer(File cacheDirectory)
    {
        super(CurseforgeEntity.class, cacheDirectory);
    }

    @Override
    public void deploy(AbstractEntity entity)
    {
        super.checkEntityClass(entity);
        try
        {
            // set deployEntity
            var deployEntity = (CurseforgeEntity) entity;
            var deployFile   = new File(deployEntity.destination, deployEntity.fileName);

            // make cache document
            var cacheDocument = new CacheDocument(super.getCacheDirectory(), deployEntity.name);

            // make deploy hash
            var deployHash  = webClient.getModFileHash(deployEntity.modId, deployEntity.fileId);
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
            var webResponse = webClient.downloadModFile(deployEntity.modId, deployEntity.fileId);
            try (InputStream inputStream = webResponse.getContentAsStream())
            {
                Files.copy(inputStream, deployFile.toPath());
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy CurseforgeEntity.", exception);
        }
    }
}
