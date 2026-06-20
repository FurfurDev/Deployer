package net.furfurmc.gradle.deployer.tests.analyzers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.electronwill.nightconfig.core.Config;

import net.furfurmc.gradle.deployer.analyzers.MakefileAnalyzer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class MakefileAnalyzerTest
{
    private static final String CONTENT_TEXT = "Content-Text";

    private MakefileAnalyzer analyzer = new MakefileAnalyzer();

    @Test
    void analyzeTest()
    {
        var userdata = Config.inMemory();
        userdata.set(MakefileAnalyzer.CONTENT_PATH, CONTENT_TEXT);
        var entity = analyzer.analyze(FilesTestUtil.getCurrentDirectory(), userdata);
        assertNotNull(entity);
    }
}
