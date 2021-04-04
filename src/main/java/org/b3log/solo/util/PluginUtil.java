package org.b3log.solo.util;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.solo.repository.PluginRepository;

/**
 * Get Bolo Plugins status.
 */
public class PluginUtil {

    final static BeanManager beanManager = BeanManager.getInstance();
    final static PluginRepository pluginRepository = beanManager.getReference(PluginRepository.class);

    public static boolean b3logPluginEnabled() {
        try {
            return pluginRepository.get("B3log支持插件_0.0.1").optString("status").equals("ENABLED");
        } catch (Exception e) {
            return false;
        }
    }
}
