# cs-stsci-my-reviews

 This is a custom integration for STSCI

The objective is to provide reviewers with a more intuitive mechanism for processing workflow.  This will be accomplished by creating a new 'My Reviews' application that resides in the user’s dashboard based on the existing My Tasks module (https://github.com/Jahia/tasks).

Changes to existing My Tasks functionality:
 
 - The 'Assign to Me' button changes to a drop-down list of reviewers able to review the item.  Any reviewer may assign a task to him/herself or another reviewer with permissions to review the item.
 
 - Any reviewer with permissions to review a task can un-assign that task and assign it to another reviewer.

 - The "Preview" button will be replace by a "Compare" that will provide the same features as the Compare Engine in the Edit mode. This includes the highlighting of text differences.
 
 - The ability to dynamically add and view comments will be added to the interface. 
 
 - When a workflow task has been accepted or rejected, it will remain in the list of workflow items, showing its state of Accepted or Rejected.  It will stay in the list for 2 weeks (configurable) before disappearing.
 
 - The columns in the table of tasks, Owner will be relabelled Assignee and Creator will be relabelled Editor


Please note that in order to be able to reassign tasks without running into JBPM error, we have to fork the My Tasks module in order to fix a Drools rule (https://github.com/faissah/tasks)
