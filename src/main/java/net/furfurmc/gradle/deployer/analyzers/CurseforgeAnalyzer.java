package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.CurseforgeEntity;

public class CurseforgeAnalyzer extends AbstractAnalyzer
{
    public static final String MODID_PATH      = "modid";
    public static final String FILEID_PATH     = "fileid";
    public static final String ANALYZER_METHOD = "curseforge";

    public CurseforgeAnalyzer()
    {
        super(CurseforgeAnalyzer.ANALYZER_METHOD);
    }

    @Override
    public AbstractEntity analyze(File destination, UnmodifiableConfig userdata)
    {
        var entity    = new CurseforgeEntity();
        entity.modId  = (String) super.getAndCheck(userdata, CurseforgeAnalyzer.MODID_PATH);
        entity.fileId = (String) super.getAndCheck(userdata, CurseforgeAnalyzer.FILEID_PATH);
        return entity;
    }
}
