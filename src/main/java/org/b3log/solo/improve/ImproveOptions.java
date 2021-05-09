package org.b3log.solo.improve;

import org.b3log.latke.ioc.BeanManager;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.OptionQueryService;
import org.json.JSONException;
import org.json.JSONObject;

public class ImproveOptions {

    /**
     * 查询用户是否加入了用户体验改进计划
     * @return String
     * 空白：还没设置过
     * false/true：用户已设置
     */
    public static String doJoinHelpImprovePlan() {
        final BeanManager beanManager = BeanManager.getInstance();
        final OptionQueryService optionQueryService = beanManager.getReference(OptionQueryService.class);
        final JSONObject preference = optionQueryService.getPreference();

        try {
            return preference.getString(Option.ID_C_HELP_IMPROVE_PLAN);
        } catch (JSONException e) {
            return "";
        }
    }
}
