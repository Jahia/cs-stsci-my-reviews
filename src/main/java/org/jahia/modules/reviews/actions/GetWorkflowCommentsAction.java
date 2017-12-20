package org.jahia.modules.reviews.actions;

import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.decorator.JCRUserNode;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.jahia.services.workflow.Workflow;
import org.jahia.services.workflow.WorkflowComment;
import org.jahia.services.workflow.WorkflowService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GetWorkflowCommentsAction extends Action {

    private WorkflowService workflowService;

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource, JCRSessionWrapper session, Map<String, List<String>> parameters, URLResolver urlResolver) throws Exception {


        JSONObject resultAsJSON = new JSONObject();
        JSONArray jsonObject = new JSONArray();
        List<WorkflowComment> wfcomments = null;
        String nodeId = getParameter(parameters, "node");            // NodeId of the workflow
        JCRNodeWrapper task = session.getNodeByIdentifier(nodeId);                    // WorkflowTask to get the process ID, which is the processID
        if (task.getPrimaryNodeTypeName().equals("jnt:workflowTask")) {
            String provider = task.getPropertyAsString("provider");                                             //Provider, need to add a comment
            String taskID = task.getPropertyAsString("taskId");           //ProcessId, need to add a comment
            wfcomments= workflowService.getWorkflow(provider,workflowService.getWorkflowTask(taskID,provider,null).getProcessId(), Locale.ENGLISH).getComments();
            for (WorkflowComment wfcomment : wfcomments) {
                JSONObject jsoncomment = new JSONObject(wfcomment);
                jsonObject.put(jsoncomment);
            }
            resultAsJSON.put("wfcomments",jsonObject);
            return new ActionResult(HttpServletResponse.SC_OK, null, resultAsJSON);
        } else {
            return ActionResult.INTERNAL_ERROR_JSON;
        }

    }

    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public WorkflowService getWorkflowService() {
        return workflowService;
    }
}
