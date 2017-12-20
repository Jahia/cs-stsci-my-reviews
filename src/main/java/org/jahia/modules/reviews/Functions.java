package org.jahia.modules.reviews;/**
 * Created by fabriceaissah on 10/16/17.
 */

import org.apache.commons.lang.StringUtils;
import org.jahia.registries.ServicesRegistry;
import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRSessionFactory;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.content.JCRValueWrapper;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Functions {

    private static final Logger logger = LoggerFactory.getLogger(Functions.class);

    public static List<JCRNodeWrapper> getAssignableUsers(JCRNodeWrapper taskNode) throws RepositoryException {
        JahiaGroupManagerService groupManagerService = JahiaGroupManagerService.getInstance();
        Set<JCRNodeWrapper> users = new HashSet<>();

        JCRValueWrapper[] candidates = taskNode.getProperty("candidates").getValues();
        for (JCRValueWrapper candidate : candidates) {
            if(candidate.getString().contains("/groups/")){
                JCRGroupNode groupNode = groupManagerService.lookupGroupByPath(candidate.getString());
                if (groupNode != null) {
                    for (JCRUserNode user : groupNode.getRecursiveUserMembers()) {
                        users.add((JCRNodeWrapper)user);
                    }
                }
            }else{
                users.add((JCRNodeWrapper)ServicesRegistry.getInstance().getJahiaUserManagerService().lookupUserByPath(candidate.getString()));
            }
        }


        return new ArrayList(users);
    }

}