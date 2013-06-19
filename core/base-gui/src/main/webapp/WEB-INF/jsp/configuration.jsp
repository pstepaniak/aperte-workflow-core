﻿<%@ page import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="configuration">
	<div class="toggle-buttons">
	<spring:message code="configuration.process.table.header" />
	<fieldset data-role="controlgroup">
		<button id="process-table-hide-0" type="button" class="btn mobile-button" data-toggle="button" onClick="toggleColumn(0);" ><spring:message code="processes.button.hide.processname" /></button>
		<button id="process-table-hide-1" type="button" class="btn mobile-button" data-toggle="button" onClick="toggleColumn(1);" ><spring:message code="processes.button.hide.processcode" /></button>
		<button id="process-table-hide-2" type="button" class="btn mobile-button" data-toggle="button" onClick="toggleColumn(2);" ><spring:message code="processes.button.hide.creator" /></button>
		<button id="process-table-hide-3" type="button" class="btn mobile-button" data-toggle="button" onClick="toggleColumn(3);" ><spring:message code="processes.button.hide.assignee" /></button>
		<button id="process-table-hide-4" type="button" class="btn mobile-button" data-toggle="button" onClick="toggleColumn(4);" ><spring:message code="processes.button.hide.creationdate" /></button>
		<button id="process-table-hide-5" type="button" class="btn mobile-button" data-toggle="button" onClick="toggleColumn(4);" ><spring:message code="processes.button.hide.deadline" /></button>
		<div class="switch">
    <input type="checkbox" checked />
</div>
	</fieldset>
	</div>
</div>

<script type="text/javascript">

</script>