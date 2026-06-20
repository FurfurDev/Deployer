package net.furfurmc.gradle.deployer.tests.analyzers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.electronwill.nightconfig.core.Config;

import net.furfurmc.gradle.deployer.analyzers.ModrinthAnalyzer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class ModrinthAnalyzerTest
{
    private static final String VERSION_ID_TEXT   = "VersionId-Text";
    private static final String VERSION_FILE_TEXT = "VersionFile-Text";

    private ModrinthAnalyzer analyzer = new ModrinthAnalyzer();

    @Test
    void analyzeTest()
    {
        var userdata = Config.inMemory();
        userdata.set(ModrinthAnalyzer.VERSION_ID_PATH,   VERSION_ID_TEXT);
        userdata.set(ModrinthAnalyzer.VERSION_FILE_PATH, VERSION_FILE_TEXT);
        var entity = analyzer.analyze(FilesTestUtil.getCurrentDirectory(), userdata);
        assertNotNull(entity);
    }
}
