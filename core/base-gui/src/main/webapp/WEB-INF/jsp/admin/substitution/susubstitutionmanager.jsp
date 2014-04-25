<!-- Aperte Workflow Substitution Manager -->
<!-- @author: mpawlak@bluesoft.net.pl -->

<%@ page
	import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="../../utils/globals.jsp"%>
<%@include file="../../utils/apertedatatable.jsp"%>

<form id="NewSubmission">
	<!-- Modal -->
	<div class="modal fade" id="NewSubstitutionModal" tabindex="-1"
		role="dialog" aria-labelledby="categoryModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="categoryModalLabel">
						<spring:message code="admin.substitution.modal.add.title" />
					</h4>
				</div>
				<div class="modal-body">

					<div class="form-horizontal" role="form">
						<div class="form-group">
							<label name="tooltip"
								title="<spring:message code='substituting.user.label.tooltip' />"
								for="UserLogin" class="col-sm-2 control-label"><spring:message
									code="substituting.user.label" /></label>
							<div class="col-sm-10">
								<input type="text" name="UserLogin" id="UserLogin"
									class="form-control"
									data-placeholder="<spring:message code='substituting.user.input.placeholder' />" />
							</div>
						</div>
						<div class="form-group">
							<label name="tooltip"
								title="<spring:message code='substitute.user.label.tooltip' />"
								for="UserSubstituteLogin"
								class="col-sm-2 control-label required"><spring:message
									code="substitute.user.label" /></label>
							<div class="col-sm-10">
								<input type="text" name="UserSubstituteLogin"
									id="UserSubstituteLogin" class="form-control"
									data-placeholder="<spring:message code='substituting.user.input.placeholder' />" />
							</div>
						</div>
						<div class="form-group">
							<label name="tooltip"
								title="<spring:message code='substituting.date.from.tooltip' />"
								for="SubstitutingDateFrom"
								class="col-sm-2 control-label required"><spring:message
									code="substituting.date.from.label" /></label>
							<div class="col-sm-10 input-append date"
								id="SubstitutingDateFromPicker" data-date-format="yyyy-mm-dd">
								<input name="SubstitutingDateFrom" id="SubstitutingDateFrom"
									class="span2 form-control" size="16" type="text"> <span
									class="add-on"><i class="icon-th"></i></span>
							</div>
						</div>
						<div class="form-group">
							<label name="tooltip"
								title="<spring:message code='substituting.date.to.tooltip' />"
								for="SubstitutingDateTo" class="col-sm-2 control-label required"><spring:message
									code="substituting.date.to.label" /></label>
							<div class="col-sm-10 input-append date"
								id="SubstitutingDateToPicker" data-date-format="yyyy-mm-dd">
								<input name="SubstitutingDateTo" id="SubstitutingDateTo"
									class="span2 form-control" size="16" type="text"> <span
									class="add-on"><i class="icon-th"></i></span>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
					<button type="submit" class="btn btn-primary">Wybierz</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</form>

<div class="process-queue-name apw_highlight">
	Aperte Workflow Substitution Manager
	<div class="btn-group  pull-right">
		<button class="btn btn-info" id="substitution-add-button"
			data-toggle="modal" data-target="#NewSubstitutionModal"
			data-original-title="" title="">
			<span class="glyphicon glyphicon-plus"></span>
			<spring:message code="admin.substitution.action.add" />
		</button>
	</div>
</div>

<div class="process-tasks-view" id="task-view-processes">
	<table id="substitutionTable" class="process-table table table-striped"
		border="1">
		<thead>
			<th style="width: 20%;"><spring:message
					code="admin.substitution.table.substituted" /></th>
			<th style="width: 20%;"><spring:message
					code="admin.substitution.table.substituting" /></th>
			<th style="width: 20%;"><spring:message
					code="admin.substitution.table.dateFrom" /></th>
			<th style="width: 20%;"><spring:message
					code="admin.substitution.table.dateTo" /></th>
			<th style="width: 20%;"><spring:message
					code="admin.substitution.table.action" /></th>
		</thead>
		<tbody></tbody>
	</table>
</div>
<button class="btn btn-danger" type="button" id="RemoveSubstitutions"
	data-original-title="" title="">
	<span class="glyphicon glyphicon-trash"></span>Remove
</button>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#RemoveSubstitutions")
								.click(
										function() {
											selections = $("#substitutionTable input");
											selections
													.each(function(i, val) {
														if (val.checked)
															$
																	.ajax(
																			{
																				url : dispatcherPortlet,
																				type : "POST",
																				data : {
																					controller : 'substitutionController',
																					action : 'deleteSubtitution',
																					substitutionId : val.name
																				}
																			})
																	.done(
																			function(
																					resp) {
																				dataTable
																						.reloadTable(dispatcherPortlet);
																			})
													})
										});

						$("#SubstitutingDateFromPicker").datepicker().on(
								'changeDate',
								function(ev) {
									$("#SubstitutingDateFromPicker")
											.datepicker("hide")
								});
						$("#SubstitutingDateToPicker").datepicker().on(
								'changeDate',
								function(ev) {
									$("#SubstitutingDateToPicker").datepicker(
											"hide")
								});

						var dataTable = new AperteDataTable(
								"substitutionTable",
								[
										{
											"sName" : "userLogin",
											"bSortable" : true,
											"mData" : "userLogin"
										},
										{
											"sName" : "userSubstituteLogin",
											"bSortable" : true,
											"mData" : "userSubstituteLogin"
										},
										{
											"sName" : "dateFrom",
											"bSortable" : true,
											"mData" : function(object) {
												return $.format.date(
														object.dateFrom,
														'dd-MM-yyyy, HH:mm:ss');
											}
										},
										{
											"sName" : "dateTo",
											"bSortable" : true,
											"mData" : function(object) {
												return $.format.date(
														object.dateTo,
														'dd-MM-yyyy, HH:mm:ss');
											}
										},
										{
											"sName" : "action",
											"bSortable" : true,
											"mData" : function(o) {
												return '<input name="'+o.id+'" type="checkbox" >';
											}
										} ], [ [ 3, "desc" ] ]);

						dataTable.addParameter("controller",
								"substitutionController");
						dataTable.addParameter("action", "loadSubstitutions");
						dataTable.reloadTable(dispatcherPortlet);

						$("#NewSubmission").submit(function(e) {
							e.preventDefault();
							var postData = $(this).serializeObject();
							postData.controller = 'substitutionController'
							postData.action = 'addNewSubstitution'
							var formUrl = dispatcherPortlet
							$.ajax({
								url : dispatcherPortlet,
								type : "POST",
								data : postData,
								success : function(data, textStatus, jqXHR) {
								}
							}).done(function(resp) {
								$("#NewSubstitutionModal").modal("hide")
								dataTable.reloadTable(dispatcherPortlet)
							});
						});
					});
</script>