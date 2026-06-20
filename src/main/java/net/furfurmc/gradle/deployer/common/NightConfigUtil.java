package net.furfurmc.gradle.deployer.common;

import java.util.List;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class NightConfigUtil
{
    @SuppressWarnings("unchecked")
    public static List<UnmodifiableConfig> getNamespace(UnmodifiableConfig uConfig)
    {
        return (List<UnmodifiableConfig>) NightConfigUtil.getField(DeployerPlugin.CONFIG_NAMESPACE, uConfig);
    }

    public static UnmodifiableConfig getSettings(UnmodifiableConfig uConfig)
    {
        return NightConfigUtil.getNamespace(uConfig).get(0);
    }

    public static UnmodifiableConfig getUserdata(UnmodifiableConfig uConfig)
    {
        return (UnmodifiableConfig) NightConfigUtil.getField(DeployerPlugin.CONFIG_USERDATA, getSettings(uConfig));
    }

    public static String getMethod(UnmodifiableConfig uConfig)
    {
        return (String) NightConfigUtil.getField(DeployerPlugin.CONFIG_METHOD, getSettings(uConfig));
    }

    public static String getName(UnmodifiableConfig uConfig)
    {
        return (String) NightConfigUtil.getField(DeployerPlugin.CONFIG_NAME, getSettings(uConfig));
    }

    public static String getFileName(UnmodifiableConfig uConfig)
    {
        return (String) NightConfigUtil.getField(DeployerPlugin.CONFIG_FILENAME, getSettings(uConfig));
    }

    @SuppressWarnings("unchecked")
    public static List<Config> getNamespace(Config config)
    {
        return (List<Config>) NightConfigUtil.getField(DeployerPlugin.CONFIG_NAMESPACE, config);
    }

    public static Config getSettings(Config config)
    {
        return NightConfigUtil.getNamespace(config).get(0);
    }

    public static Config getUserdata(Config uConfig)
    {
        return (Config) NightConfigUtil.getField(DeployerPlugin.CONFIG_USERDATA, getSettings(uConfig));
    }

    public static void setNamespace(Config config, List<Config> namespace)
    {
        NightConfigUtil.setField(DeployerPlugin.CONFIG_NAMESPACE, config, namespace);
    }

    public static void setSettings(Config config, Config settings)
    {
        NightConfigUtil.getNamespace(config).set(0, settings);
    }

    public static void setMethod(Config config, String method)
    {
        NightConfigUtil.setField(DeployerPlugin.CONFIG_METHOD, getSettings(config), method);
    }

    public static void setName(Config config, String name)
    {
        NightConfigUtil.setField(DeployerPlugin.CONFIG_NAME, getSettings(config), name);
    }

    public static void setFileName(Config config, String fileName)
    {
        NightConfigUtil.setField(DeployerPlugin.CONFIG_FILENAME, getSettings(config), fileName);
    }

    private static Object getField(String path, UnmodifiableConfig uConfig)
    {
        if (!uConfig.contains(path)) throw new RuntimeException("Not found " + path + ".");
        return uConfig.get(path);
    }

    private static void setField(String path, Config config, Object object)
    {
        config.set(path, object);
    }
}
