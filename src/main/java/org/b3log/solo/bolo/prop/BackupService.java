package org.b3log.solo.bolo.prop;

import org.b3log.latke.ioc.Inject;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.model.Option;
import org.b3log.solo.service.ExportService;
import org.b3log.solo.service.OptionQueryService;
import org.b3log.solo.util.Solos;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * <h3>bolo-solo</h3>
 * <p>备份服务</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2020-01-05 13:39
 **/
@RequestProcessor
public class BackupService {
    /**
     * Option query service.
     */
    @Inject
    private OptionQueryService optionQueryService;

    /**
     * Export service.
     */
    @Inject
    private ExportService exportService;

    @RequestProcessing(value = "/prop/backup/hacpai/do/upload", method = {HttpMethod.GET})
    public void uploadBackupToHacpai(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        final JSONObject preference = optionQueryService.getPreference();
        if (!preference.optBoolean(Option.ID_C_SYNC_GITHUB)) {
            context.sendError(405);

            return;
        }

        try {
            exportService.exportHacPai();

            context.renderJSON().renderCode(200);
            context.renderJSON().renderMsg("Exported backup to HacPai manual successfully.");

            return ;
        } catch (final Exception e) {
            context.sendError(500);

            return ;
        }
    }
}
