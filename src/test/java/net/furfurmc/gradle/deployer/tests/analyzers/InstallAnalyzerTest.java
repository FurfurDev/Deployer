package net.furfurmc.gradle.deployer.tests.analyzers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.InstallAnalyzer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class InstallAnalyzerTest
{
    public static final String ORIGIN_TEXT = "Origin-Text";

    private InstallAnalyzer analyzer = new InstallAnalyzer();

    @Test
    void analyzeTest()
    {
        var userdata = Config.inMemory();
        userdata.set(InstallAnalyzer.ORIGIN_PATH, ORIGIN_TEXT);
        var entity = analyzer.analyze(FilesTestUtil.getCurrentDirectory(), userdata);
        assertNotNull(entity);
    }
}
