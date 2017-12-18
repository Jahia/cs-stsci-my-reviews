package org.jahia.modules.reviews.actions;

import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.jahia.ajax.gwt.helper.DiffHelper;
import org.json.JSONObject;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CompareHighlightAction extends Action {
    private DiffHelper diff;

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource, JCRSessionWrapper jcrSessionWrapper, Map<String, List<String>> map, URLResolver urlResolver) throws Exception {
        JSONObject jsonObject = new JSONObject();
        String highlighted= diff.getHighlighted(req.getParameter("liveFrame"),req.getParameter("stagingFrame"));
        jsonObject.put("highlighted", highlighted);
        return new ActionResult(HttpServletResponse.SC_OK, null, jsonObject);
    }

    public void setDiff(DiffHelper diff) {
        this.diff = diff;
    }

    public DiffHelper getDiff() {
        return diff;
    }
}
