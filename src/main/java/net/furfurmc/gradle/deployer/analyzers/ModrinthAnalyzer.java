package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.ModrinthEntity;

public class ModrinthAnalyzer extends AbstractAnalyzer
{
    public static final String VERSION_ID_PATH   = "versionid";
    public static final String VERSION_FILE_PATH = "versionfile";
    public static final String ANALYZER_METHOD   = "modrinth";

    public ModrinthAnalyzer()
    {
        super(ModrinthAnalyzer.ANALYZER_METHOD);
    }

    @Override
    public AbstractEntity analyze(File destination, UnmodifiableConfig userdata)
    {
        var entity         = new ModrinthEntity();
        entity.versionId   = (String) super.getAndCheck(userdata, ModrinthAnalyzer.VERSION_ID_PATH);
        entity.versionFile = (String) super.getAndCheck(userdata, ModrinthAnalyzer.VERSION_FILE_PATH);
        return entity;
    }
}
