package org.jahia.modules.reviews.actions;

import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.jahia.services.workflow.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class CommentWorkflowAction extends Action {

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource, JCRSessionWrapper session, Map<String, List<String>> parameters, URLResolver urlResolver) throws Exception {

        String          nodeId          =       getParameter(parameters, "node");            // NodeId of the workflow
        String          comment         =       getParameter(parameters,"comment");          // Comment of the workflow
        JCRNodeWrapper  task            =       session.getNodeByIdentifier(nodeId);                    // WorkflowTask to get the process ID, which is the processID

        if(task.getPrimaryNodeTypeName().equals("jnt:workflowTask")){
            String          userName        =       session.getUser().getUsername();                    // Username
            String          provider        =       "jBPM";                                             //Provider, need to add a comment
            String          taskId          =       task.getPropertyAsString("taskId");           //ProcessId, need to add a comment
            String          processID       =       null;
            List<WorkflowTask> tasks        =       WorkflowService.getInstance().getTasksForUser(session.getUser(),session.getLocale());

            processID = getProcessId(tasks,taskId);

            if (null != comment && !comment.isEmpty() && processID!= null) {
                WorkflowService.getInstance().addComment(processID,provider,comment,userName);
            }else {
                return ActionResult.INTERNAL_ERROR_JSON;
            }
            return ActionResult.OK_JSON;
        }else{
            return ActionResult.INTERNAL_ERROR_JSON;
        }

    }

    private String getProcessId(List<WorkflowTask> tasks, String taskId){
        for (WorkflowTask taskWorkflow : tasks){
            if(taskWorkflow.getId().equals(taskId)){
               return taskWorkflow.getProcessId();
            }
        }
        return null;
    }
}
