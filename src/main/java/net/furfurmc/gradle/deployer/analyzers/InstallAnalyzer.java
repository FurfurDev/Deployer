package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.InstallEntity;

public class InstallAnalyzer extends AbstractAnalyzer
{
    public static final String ORIGIN_PATH      = "origin";
    public static final String ANALYZER_METHOD  = "install";

    public InstallAnalyzer()
    {
        super(InstallAnalyzer.ANALYZER_METHOD);
    }

    @Override
    public AbstractEntity analyze(File destination, UnmodifiableConfig userdata)
    {
        var entity    = new InstallEntity();
        entity.origin = new File(destination, (String) super.getAndCheck(userdata, InstallAnalyzer.ORIGIN_PATH)).toString();
        return entity;
    }
}
