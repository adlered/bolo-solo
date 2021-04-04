package org.b3log.solo.util;

import org.b3log.latke.ioc.Inject;
import org.b3log.solo.repository.PluginRepository;
import org.b3log.solo.service.PluginQueryService;

/**
 * Get Bolo Plugins status.
 */
public class PluginUtil {

    @Inject
    PluginRepository pluginRepository;

    public boolean b3logPluginEnabled() {
        try {
            return pluginRepository.get("B3log支持插件_0.0.1").optString("status").equals("ENABLED");
        } catch (Exception e) {
            return false;
        }
    }
}
