package net.furfurmc.gradle.deployer.tests.analyzers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.WebAnalyzer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class UrlAnalyzerTest
{
    private static final String URL_TEXT = "Url-Text";

    private WebAnalyzer analyzer = new WebAnalyzer();

    @Test
    void analyzeTest()
    {
        var userdata = Config.inMemory();
        userdata.set(WebAnalyzer.URL_PATH, URL_TEXT);
        var entity = analyzer.analyze(FilesTestUtil.getCurrentDirectory(), userdata);
        assertNotNull(entity);
    }
}
