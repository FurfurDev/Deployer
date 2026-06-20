package net.furfurmc.gradle.deployer.deployers;

import java.io.File;
import java.nio.file.Files;
import net.furfurmc.gradle.deployer.common.CacheDocument;
import net.furfurmc.gradle.deployer.common.HashBuilder;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.MakefileEntity;

public class MakefileDeployer extends AbstractDeployer
{
    public MakefileDeployer(File cacheDirectory)
    {
        super(MakefileEntity.class, cacheDirectory);
    }

    @Override
    public void deploy(AbstractEntity entity)
    {
        super.checkEntityClass(entity);
        try
        {
            // set deployEntity
            var deployEntity = (MakefileEntity) entity;
            var deployFile   = new File(deployEntity.destination, deployEntity.fileName);

            // make cache document
            var cacheDocument = new CacheDocument(super.getCacheDirectory(), deployEntity.name);

            // make deploy hash
            var hashBuilder = new HashBuilder();
            var deployHash  = hashBuilder.makeStringHash(deployEntity.content);
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
            Files.writeString(deployFile.toPath(), deployEntity.content);
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy MakefileEntity.", exception);
        }
    }
}
