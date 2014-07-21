﻿<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<div class="process-panel" id="customqueue-panel-view" hidden="true">
	<table id="customQueueTable" class="process-table table table-striped" border="1">
		<thead>
			<tr>
				<th style="width:10%;"><spring:message code="processes.list.table.process.name" /></th>
				<th style="width:10%;"><spring:message code="processes.list.table.process.step" /></th>
				<th style="width:9%;"><spring:message code="processes.list.table.process.creator" /></th>
				<th style="width:9%;"><spring:message code="processes.list.table.process.creationdate" /></th>
				<th style="width:10%;"><spring:message code="processes.list.table.process.clientType" /></th>
				<th style="width:10%;"><spring:message code="processes.list.table.process.actions" /></th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>

</div>

<script type="text/javascript">
//<![CDATA[
  	$(document).ready(function()
	{
		var loadedCustom = loadCookies("queue");
		var parsedCustom = parseCookie(loadedCustom);
	
		var dataTable = new AperteDataTable("customQueueTable", 
			[
				 { "sName":"name", "bSortable": true,"bVisible":parsedCustom.name,"mData": function(object){return generateNameColumn(object);}},
				 { "sName":"step", "bSortable": true,"bVisible":parsedCustom.step, "mData": "step" },
				 { "sName":"creator", "bSortable": true,"bVisible":parsedCustom.creator,"mData": "creator" },
				 { "sName":"creationDate", "bSortable": true,"bVisible":parsedCustom.creationDate,"mData": function(object){return $.format.date(object.creationDate, 'dd-MM-yy, HH:mm:ss');}},
				 { "sName":"clientType","bVisible":true ,"bVisible":parsedCustom.clientType, "bSortable": true,"mData": function(object){ if(object.highlight) return '<font color="red">'+object.clientType+'<span class="glyphicon glyphicon-exclamation-sign" style="margin-left: 5px" /></font>'; else return object.clientType;}},
				 { "sName":"actions", "bSortable": false,"bVisible":parsedCustom.actions,"mData": function(object){return generateButtons(object)}}
			 ],
			 [[ 4, "desc" ]]
			);

        dataTable.addParameter('taskListViewName','LOTComplaintView');

		queueViewManager.addTableView('queue', dataTable, 'customqueue-panel-view');
			
		dataTable.enableMobileMode = function()
		{
			//this.toggleColumnButton("deadline", false);
			this.toggleColumnButton("creationDate", false);
		}
		
		dataTable.enableTabletMode = function()
		{
			this.toggleColumnButton("creator", false);
		}
		
		dataTable.disableMobileMode = function()
		{
			//this.toggleColumnButton("deadline", true);
			this.toggleColumnButton("creationDate", true);
		}
		
		dataTable.disableTabletMode = function()
		{
			this.toggleColumnButton("creator", true);
		}
	});
	
	function generateButtons(task)
	{
		var linkBody = '';
		if(task.queueName || (!task.assignee && task.userCanClaim))
		{
			linkBody += '<button id="link-'+task.queueName+'" class="btn btn-default btn-sm" type="button" data-toggle="tooltip" title="<spring:message code="activity.tasks.task.claim.details" />" onclick="claimTaskFromQueue(this, \''+task.queueName+'\','+task.processStateConfigurationId+','+task.taskId+'); "><spring:message code="activity.tasks.task.claim" /></a>';
		}
		
		return linkBody;
	}
	
	function claimTaskFromQueue(button, queueName, processStateConfigurationId, taskId)
	{
		$(button).prop('disabled', true);
		windowManager.showLoadingScreen();
		
		var bpmJson = $.post('<portlet:resourceURL id="claimTaskFromQueue"/>',
		{
			"queueName": queueName,
			"taskId": taskId,
			"userId": queueViewManager.currentOwnerLogin
		}, function(task)
		{
			clearAlerts();
			reloadQueues();
			loadProcessView(task.taskId);
		})
		.fail(function(request, status, error) 
		{	

		});
	}
//]]>
</script>