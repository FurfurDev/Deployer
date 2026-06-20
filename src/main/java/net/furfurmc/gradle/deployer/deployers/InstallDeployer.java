package net.furfurmc.gradle.deployer.deployers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import net.furfurmc.gradle.deployer.common.CacheDocument;
import net.furfurmc.gradle.deployer.common.HashBuilder;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.InstallEntity;

public class InstallDeployer extends AbstractDeployer
{
    public InstallDeployer(File cacheDirectory)
    {
        super(InstallEntity.class, cacheDirectory);
    }

    @Override
    public void deploy(AbstractEntity entity)
    {
        super.checkEntityClass(entity);
        try
        {
            // set deployEntity
            var deployEntity = (InstallEntity) entity;
            var deployFile   = new File(deployEntity.destination, deployEntity.fileName);

            // make cache document
            var cacheDocument = new CacheDocument(super.getCacheDirectory(), deployEntity.name);

            // set custom vars
            var originFile = new File(deployEntity.origin);

            // make deploy hash
            var hashBuilder = new HashBuilder();
            var deployHash  = hashBuilder.makeFileHash(originFile);
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
            try (InputStream inputStream = new FileInputStream(originFile))
            {

                Files.copy(inputStream, deployFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy InstallEntity.", exception);
        }
    }
}
