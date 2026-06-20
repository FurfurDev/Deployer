package net.furfurmc.gradle.deployer.tests.analyzers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.electronwill.nightconfig.core.Config;

import net.furfurmc.gradle.deployer.analyzers.CurseforgeAnalyzer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class CurseforgeAnalyzerTest
{
    private static final String MODID_TEXT  = "ModId-Text";
    private static final String FILEID_TEXT = "FileId-Text";

    private CurseforgeAnalyzer analyzer = new CurseforgeAnalyzer();

    @Test
    void analyzeTest()
    {
        var userdata = Config.inMemory();
        userdata.set(CurseforgeAnalyzer.MODID_PATH,  MODID_TEXT);
        userdata.set(CurseforgeAnalyzer.FILEID_PATH, FILEID_TEXT);
        var entity = analyzer.analyze(FilesTestUtil.getCurrentDirectory(), userdata);
        assertNotNull(entity);
    }
}
