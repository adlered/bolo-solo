package org.b3log.solo.improve;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionQueryService;
import org.json.JSONObject;

public class ImproveOptions {

    /**
     * 查询用户是否加入了用户体验改进计划
     * @return boolean 是否加入
     */
    public static boolean joinHelpImprovePlan() {
        final BeanManager beanManager = BeanManager.getInstance();
        final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
        final JSONObject preference = optionQueryService.getPreference();
        final String helpImprovePlan = preference.getString(Option.ID_C_HELP_IMPROVE_PLAN);

        return helpImprovePlan.equals("true");
    }
}
