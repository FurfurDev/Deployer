package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.WebEntity;

public class WebAnalyzer extends AbstractAnalyzer
{
    public static final String URL_PATH        = "url";
    public static final String ANALYZER_METHOD = "web";

    public WebAnalyzer()
    {
        super(WebAnalyzer.ANALYZER_METHOD);
    }

    @Override
    public AbstractEntity analyze(File destination, UnmodifiableConfig userdata)
    {
        var entity = new WebEntity();
        entity.url = (String) super.getAndCheck(userdata, WebAnalyzer.URL_PATH);
        return entity;
    }
}
