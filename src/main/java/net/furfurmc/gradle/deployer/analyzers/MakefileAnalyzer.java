package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.MakefileEntity;

public class MakefileAnalyzer extends AbstractAnalyzer
{
    public static final String CONTENT_PATH    = "content";
    public static final String ANALYZER_METHOD = "makefile";

    public MakefileAnalyzer()
    {
        super(MakefileAnalyzer.ANALYZER_METHOD);
    }

    @Override
    public AbstractEntity analyze(File destination, UnmodifiableConfig userdata)
    {
        var entity     = new MakefileEntity();
        entity.content = (String) super.getAndCheck(userdata, MakefileAnalyzer.CONTENT_PATH);
        return entity;
    }
}
