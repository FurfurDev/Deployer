package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.entities.GitHubEntity;

public class GitHubAnalyzer extends AbstractAnalyzer
{
    public static final String OWNER_PATH      = "owner";
    public static final String REPO_PATH       = "repo";
    public static final String TAG_PATH        = "tag";
    public static final String ASSET_PATH      = "asset";
    public static final String ANALYZER_METHOD = "github";

    public GitHubAnalyzer()
    {
        super(GitHubAnalyzer.ANALYZER_METHOD);
    }

    @Override
    public AbstractEntity analyze(File destination, UnmodifiableConfig userdata)
    {
        var entity   = new GitHubEntity();
        entity.owner = (String) super.getAndCheck(userdata, GitHubAnalyzer.OWNER_PATH);
        entity.repo  = (String) super.getAndCheck(userdata, GitHubAnalyzer.REPO_PATH);
        entity.tag   = (String) super.getAndCheck(userdata, GitHubAnalyzer.TAG_PATH);
        entity.asset = (String) super.getAndCheck(userdata, GitHubAnalyzer.ASSET_PATH);
        return entity;
    }
}
