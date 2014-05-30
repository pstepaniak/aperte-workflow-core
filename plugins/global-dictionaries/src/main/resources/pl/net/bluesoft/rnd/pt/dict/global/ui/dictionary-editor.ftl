<#import "/spring.ftl" as spring />
<#assign portlet=JspTaglibs["http://java.sun.com/portlet_2_0"] />

<script type="text/javascript">
	var dispatcherPortlet = '<@portlet.resourceURL id="dispatcher"/>';

</script>

<#include "apertedatatable.ftl"/>

<script>
    var g_dictionary_items = {};
    g_dictionary_items["complaint_type"] = [
        ["P", "Reklamacja Pasażerska", "", [
            ["Reklamacja Pasażerska", "", "", "", [
                ["case_signature", "HMRP", "Sygnatura sprawy"],
                ["sap_code", "P", "Kod dla SAP"],
                ["box_id", "P", "Identyfikator skrzynki"],
                ["queue_id", "new_passenger_complaints", "Identyfikator kolejki"]
            ]]
        ]],
        ["B", "Reklamacja Bagażowa", "", [
            ["Reklamacja Bagażowa", "", "", "", [
                ["case_signature", "HMRB", "Sygnatura sprawy"],
                ["sap_code", "B", "Kod dla SAP"],
                ["box_id", "B", "Identyfikator skrzynki"],
                ["queue_id", "new_luggage_complaints", "Identyfikator kolejki"]
            ]]
        ]]
    ];
    g_dictionary_items["statuses"] = [
        ["NEW", "Status zarejestrowana", "", [
            ["Zarejestrowana", "", "", "", [
                ["type", "OPEN", "Sprawa otwarta"]
            ]]
        ]],
        ["OPEN", "Status otwarta", "", [
            ["Otwarta", "", "", "", [
                ["type", "OPEN", "Sprawa otwarta"]
            ]]
        ]],
        ["CLOSED", "Status zamknięta", "", [
            ["Zamknięta", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["CLOSED_NUMBER", "Status zamknięta z numerem", "", [
            ["Zamknięta z numerem", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"],
                ["subdict", "statuses_closed", "Podsłownik dla zamkniętych z numerem"],
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]],
        ["REJECTED", "Status odrzucona", "", [
            ["Odrzucona", "", "", "", [
                ["type", "CLOSED", "Sprawa otwarta"]
            ]]
        ]]

    ];

</script>

<style type="text/css">
    #fixedElement.stick {
        position: fixed;
        top: 0;
        z-index: 1;
        background-color: white;
    }

    table {
        border:none;
        border-collapse: collapse;
    }

    table tr {
        border-top: 1px solid #ddd;
        border-bottom: 1px solid #ddd;
    }

    table tr:first-child {
        border-top: none;
        border-bottom: none;
    }

    table tr:last-child {
        border-bottom: none;
    }

</style>


<div class="apw main-view col-md-10 col-md-offset-1">
    <div class="panel panel-default">
        <div class="panel-heading">
            <@spring.message "dictionary.editor.heading"/>
            <div class="pull-right">
                <div class="col-md-4">
                    <label for="dictName"><@spring.message "dictionary.editor.dictionaryName"/></label>
                </div>
                <div class="col-md-4">
                    <select id="dictName" type="text" placeholder="Taryfa" style="width:250px">
                        <option value="complaint_type">Kod sekcji</option>
                        <option value="statuses">Statusy</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <div id="itemsList">
                <div class="row">
                    <div class="col-md-8">
                        <button type="button" id="addNew" class="btn btn-primary btn-xs"><@spring.message "dictionary.editor.dictionaryItems.button.addNew"/></button>
                    </div>
                </div>
                <br>

                <div class="panel-body">
                    <table cellpadding="0" cellspacing="0" border="0" id="itemsTable"
                           class="table table-hover table-bordered" style="width:100%;">
                        <thead>
                            <th style="font-size: 11px!important; width:110px"><@spring.message "dictionary.editor.dictionaryItems.table.key"/></th>
                            <th style="font-size: 11px!important;width:50px"><@spring.message "dictionary.editor.dictionaryItems.table.description"/></th>
                            <th style="font-size: 11px!important;width:210px"><@spring.message "dictionary.editor.dictionaryItems.table.actions"/></th>
                        </thead>
                        <tbody style="font-size: 12px!important;vertical-align:middle;">

                        </tbody>
                    </table>
                </div>
            </div>
            <div id="itemsEdit" hidden>
                <div class="row">
                    <div class="col-md-2">
                        <button type="button" id="saveButton" class="btn btn-success btn-xs">Zapisz</button>
                        <button type="button" id="backButton" class="btn btn-warning btn-xs">Anuluj</button>
                    </div>
                </div>
                <br>

                <div class="row">
                    <div class="form-group col-md-4">
                        <label for="description" class="col-sm-2 control-label">Klucz</label>

                        <div class="col-sm-10">
                            <input class="form-control" id="itemKey" placeholder="Klucz"></textarea>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="description" class="col-sm-2 control-label">Opis</label>

                        <div class="col-sm-10">
                            <input class="form-control" id="itemDesc" placeholder="Opis"></textarea>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2 pull-right">
                        <button type="button" id="addNewValue" class="btn btn-primary btn-xs">Dodaj wartość</button>
                    </div>
                </div>
                <div class="row">
                    <div class="panel-body">
                        <table cellpadding="0" cellspacing="0" border="0" id="valuesTable"
                               class="table table-hover table-bordered" style="width:100%;">
                            <thead>
                            <tr>
                                <!-- <th class="id" style="font-size: 11px!important;"></th> -->
                                <th class="CASE_NUMBER" style="font-size: 11px!important; width:110px">Wartość</th>
                                <th class="TYPE" style="font-size: 11px!important;width:50px">Data od</th>
                                <th class="NAME" style="font-size: 11px!important;width:50px">Data do</th>
                                <th class="NAME" style="font-size: 11px!important;width:410px">Rozszerzenia</th>
                                <th class="NAME" style="font-size: 11px!important;width:80px">Akcje</th>
                            </tr>

                            </thead>
                            <tbody style="font-size: 12px!important;vertical-align:middle;">

                            </tbody>
                        </table>


                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
//<![CDATA[


    var itemsTable;
    var valuesTable;
    var currentDict = "complaint_type";
    var currentItem;
    var currentItemClone;
    var currentItemIndex;

    $(document).ready(function () {
        $('[name="tooltip"]').tooltip();

        /*valuesTable = $('#valuesTable').dataTable({
            "bPaginate": true,
            "bLengthChange": true,
            "bFilter": false,
            "bSort": true,
            "bInfo": false,
            "bAutoWidth": false,
            "iDisplayLength": 5,
            "aLengthMenu": [[25, 50, 100, -1],[25, 50, 100, "All"]],
            "oLanguage": {
                "sEmptyTable": " "
            },
            "aoColumnDefs":[
                {
                    "aTargets":[0],
                    "sClass":"center",
					"mData": null,
                    "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                            var valueControl = $('<input style="width:200px" type="text" class="form-control" id="actionDate" placeholder="Wartość">');
							valueControl.val(oData[0]);
							$(valueControl).on('change',function(){
								currentItemClone[3][iRow][0] = $( this ).val() ;
							});
                            $(nTd).prepend(valueControl);
                    }
                },
                {
                    "aTargets":[1],
                    "sClass":"center",
					"mData": null,
                    "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                            var dataControl = $('<div class="input-group date" style="width:150px"><input type="text" class="form-control datepicker" id="actionDate" placeholder="Data od"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span></div>');
							$(dataControl).datepicker({
									"format": "yyyy-mm-dd",
									"language": "pl",
									autoclose: true
							});
							$(dataControl).datepicker('update', currentItemClone[3][iRow][1]);
							$(dataControl).on('changeDate',function(e){
								var dateString = $.format.date(e.dates[0], "yyyy-MM-dd");
								currentItemClone[3][iRow][1] = dateString;

							});
                            $(nTd).prepend(dataControl);
                    }
                },
                {
                    "aTargets":[2],
                    "sClass":"center",
					"mData": null,
                    "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
                            var dataControl = $('<div class="input-group date" style="width:150px"><input type="text" class="form-control datepicker" id="actionDate" placeholder="Data do"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span></div>');
							$(dataControl).datepicker({
									"format": "yyyy-mm-dd",
									"language": "pl",
									autoclose: true
							});
							$(dataControl).datepicker('update', currentItemClone[3][iRow][2]);
							$(dataControl).on('changeDate',function(e){
								var dateString = $.format.date(e.dates[0], "yyyy-MM-dd");
								currentItemClone[3][iRow][2] = dateString;

							});
                            $(nTd).prepend(dataControl);
                    }
                },
				{
                    "aTargets":[3],
                    "sClass":"center",
					"mData": null,
					"fnCreatedCell" : function(nTd, sData, oData, iRow, iCol)
					{
						$(nTd).empty();
						var row = $('<div class="col-md-12" ></div>');
						$.each(oData[4], function(index)
						{
							var innerRow = $('<div class="row"></div>');
							var lineKey = $('<div class="form-group col-md-5"><label for="description" class="control-label">Klucz</label>' +
											'<div><input type="text" class="form-control" value="'+this[0]+'" placeholder="Klucz"></input></div></div>');

							$(lineKey).find('input').on('change',function(){
								currentItemClone[3][iRow][4][index][0] = $( this ).val() ;
							});


							var lineValue = $('<div class="form-group col-md-5"><label for="description" class="control-label">Wartość</label>' +
											'<div><input type="text" class="form-control" value="'+this[1]+'" placeholder="Wartość"></input></div></div>');

							$(lineValue).find('input').on('change',function(){
								currentItemClone[3][iRow][4][index][1] = $( this ).val() ;
							});

							var lineButtonDiv = $('<div class="form-group col-md-2"></div>');

							var removeButton = $('<span name="tooltip" title="Usuń" class="glyphicon glyphicon-trash" style="font-size: 16px!important;cursor:pointer;margin-top:25px"></span>');

							removeButton.on('click',function(){
                                removeExtension(iRow, index);
                            });

							$(lineButtonDiv).append(removeButton);

							$(innerRow).append(lineKey);
							$(innerRow).append(lineValue);
							$(innerRow).append(lineButtonDiv);

							$(row).append(innerRow);
						});
						$(nTd).append(row);


						var addExtensionButton = $('<button type="button" class="btn btn-info btn-xs" style="margin: 10px;">Dodaj rozszerzenie</button>');
                            addExtensionButton.button();
                            addExtensionButton.on('click',function(){
                                addNewExtension(iRow);
                            });
						$(nTd).append("&nbsp;");
                        $(nTd).append(addExtensionButton);
                    }
                },
				{
                    "aTargets":[4],
                    "sClass":"center",
					"mData": null,
                    "fnCreatedCell" : function(nTd, sData, oData, iRow, iCol){
							var removeButton = $('<button type="button" class="btn btn-danger btn-xs">Usuń</button>');
                            removeButton.button();
                            removeButton.on('click',function(){
                                removeValue(iRow);
                            });
							$(nTd).prepend("&nbsp;");
                            $(nTd).prepend(removeButton);
                        }
                }
            ],
            "fnRowCallback" : function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {

            }
        });*/

        itemsTable = new AperteDataTable("itemsTable",
            [
                 { "sName":"key", "bSortable": true ,"mData": "key" },
                 { "sName":"description", "bSortable": true ,"mData": "description" },
                 { "sName":"actions", "bSortable": false , "mData": function(o) { return ""; }, "fnCreatedCell": function(nTd, sData, oData, iRow, iCol) { return generateActionsColumn(nTd, sData, oData, iRow, iCol) }
                 }
            ],
            [[ 0, "asc" ]]
        );

        function generateActionsColumn(nTd, sData, oData, iRow, iCol) {
            var editButton = $('<button type="button" class="btn btn-primary btn-xs"><@spring.message "dictionary.editor.dictionaryItems.button.edit"/></button>');
            editButton.button();
            editButton.on('click',function(){
                edit(oData.id);
            });
            $(nTd).empty();
            $(nTd).prepend(editButton);

            var removeButton = $('<button type="button" class="btn btn-danger btn-xs"><@spring.message "dictionary.editor.dictionaryItems.button.delete"/></button>');
            removeButton.button();
            removeButton.on('click',function(){
                remove(oData.id);
            });
            $(nTd).prepend("&nbsp;");
            $(nTd).prepend(removeButton);
        }

        itemsTable.addParameter("controller", "dictionaryeditorcontroller");
        itemsTable.addParameter("action", "getDictionaryItems");
        itemsTable.reloadTable(dispatcherPortlet);

        $('#dictName').select2();
		$('#dictName').on('change', function(){
			currentDict = $('#dictName').val();
			refreshTable();
		});
		$("#backButton").on('click',function()
		{
			$("#itemsList").show();
			$("#itemsEdit").hide();

			refreshTable();
		});

		$("#addNew").on('click',function()
		{
			addNew();
		});

		$("#addNewValue").on('click',function()
		{
			addNewValue();
		});

		$("#saveButton").on('click',function()
		{
			currentItemClone[0] = $("#itemKey").val();
			currentItemClone[1] = $("#itemDesc").val();

			$("#itemsList").show();
			$("#itemsEdit").hide();

			//currentItem = currentItemClone.slice();
			currentItem = $.extend(true, [], currentItemClone);
			g_dictionary_items[currentDict][currentItemIndex] = currentItem;
			//currentItem.shift().shift();

			valuesTable.fnClearTable();

			refreshTable();
		});

    });

	function addNewExtension(iRow)
	{
		var newExtension = ["", "", ""];
		var extensions = currentItemClone[3][iRow][4];

		currentItemClone[3][iRow][4][extensions.length] = newExtension;

		valuesTable.fnClearTable();
		valuesTable.fnAddData(currentItemClone[3]);
	}

	function removeExtension(iRow, index)
	{
		currentItemClone[3][iRow][4].splice(index, 1);

		valuesTable.fnClearTable();
		valuesTable.fnAddData(currentItemClone[3]);
	}

	function removeValue(iRow)
	{
		currentItemClone[3].splice(iRow, 1);

		valuesTable.fnClearTable();
		valuesTable.fnAddData(currentItemClone[3]);
	}

	function addNew()
	{
		currentItemIndex = g_dictionary_items[currentDict].length;
		currentItemClone = ["", "", "", [["", "", "", "", []]]];

		$("#itemsList").hide();
		$("#itemsEdit").show();
		$("#itemKey").val(currentItemClone[0]);
		$("#itemDesc").val(currentItemClone[1]);

		valuesTable.fnClearTable();
		valuesTable.fnAddData(currentItemClone[3]);

	}

	function addNewValue()
	{
		var values = currentItemClone[3];
		currentItemClone[3][values.length] = ["", "", "","", []];

		valuesTable.fnClearTable();
		valuesTable.fnAddData(currentItemClone[3]);
	}

	function edit(iRow)
		{
			currentItemIndex = iRow;
			currentItem = g_dictionary_items[currentDict][currentItemIndex];
			//currentItemClone = currentItem.slice();
			currentItemClone = $.extend(true, [], currentItem);
			//currentItemClone.shift().shift();

			$("#itemsList").hide();
			$("#itemsEdit").show();
			$("#itemKey").val(currentItemClone[0]);
			$("#itemDesc").val(currentItemClone[1]);

			valuesTable.fnClearTable();
			valuesTable.fnAddData(currentItemClone[3]);
		}

	function remove(iRow) {
        // todo
        refreshTable();
    }

	function refreshTable()
	{
		// itemsTable.fnClearTable();
		// itemsTable.fnAddData(g_dictionary_items[currentDict]);
		itemsTable.reloadTable(dispatcherPortlet);
	}



    function isOdd(n)
    {
       return isNumber(n) && (Math.abs(n) % 2 == 1);
    }

    function isNumber(n)
    {
       return n === parseFloat(n);
    }

//]]>

</script>

