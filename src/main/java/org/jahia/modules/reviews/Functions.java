package org.jahia.modules.reviews;/**
 * Created by fabriceaissah on 10/16/17.
 */

import org.apache.commons.lang.StringUtils;
import org.jahia.registries.ServicesRegistry;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionFactory;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.decorator.JCRGroupNode;
import org.jahia.services.content.decorator.JCRUserNode;
import org.jahia.services.preferences.user.UserPreferencesHelper;
import org.jahia.services.usermanager.JahiaGroup;
import org.jahia.services.usermanager.JahiaGroupManagerService;
import org.jahia.services.usermanager.JahiaPrincipal;
import org.jahia.services.usermanager.JahiaUser;
import org.jahia.services.workflow.WorkflowDefinition;
import org.jahia.services.workflow.WorkflowService;
import org.jahia.services.workflow.WorkflowTask;
import org.jahia.services.workflow.jbpm.JBPM6WorkflowProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jahia.services.workflow.jbpm.JBPMTaskIdentityService;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.List;


public class Functions {

    private static final Logger logger = LoggerFactory.getLogger(Functions.class);

    public static List<JCRNodeWrapper> getAssignableUsers(JCRNodeWrapper taskNode) throws RepositoryException {
        JahiaGroupManagerService groupManagerService = JahiaGroupManagerService.getInstance();
        JCRSessionWrapper session = JCRSessionFactory.getInstance().getCurrentUserSession();
        JBPM6WorkflowProvider workflowProvider = JBPM6WorkflowProvider.getInstance();
        WorkflowTask task = workflowProvider.getWorkflowTask(taskNode.getPropertyAsString("taskId"),session.getLocale());
        List<JCRNodeWrapper> users = new ArrayList<JCRNodeWrapper>();
        WorkflowDefinition definition = task.getWorkflowDefinition();
        List <JahiaPrincipal> principals = new ArrayList<JahiaPrincipal>();

        try{
            principals = WorkflowService.getInstance().getAssignedRole(definition, "review", task.getProcessId(),session);
        }catch (RepositoryException e) {
            logger.error("Couldn't workflow for node "+task.getName(),e);
        }
        for (JahiaPrincipal principal : principals) {
            if (principal instanceof JahiaUser) {
                    users.add(((JCRNodeWrapper)ServicesRegistry.getInstance().getJahiaUserManagerService().lookupUser(((JahiaUser) principal).getUsername(), ((JahiaUser) principal).getRealm(), true)));
            } else if (principal instanceof JahiaGroup) {
                JCRGroupNode groupNode = groupManagerService.lookupGroupByPath(principal.getLocalPath());
                if (groupNode != null) {
                    for (JCRUserNode user : groupNode.getRecursiveUserMembers()) {
                            users.add((JCRNodeWrapper)user);
                    }
                }
            }
        }
        return users;
    }

}