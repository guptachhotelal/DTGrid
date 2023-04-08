$(function() {
	var tbl = $('#tblList').DataTable({
		"pagingType": "full_numbers",
		"language": {
			"lengthMenu": "_MENU_",
			"paginate": { "previous": "&#10094;&#10094;", "first": "&#10094;", "next": "&#10095;&#10095;", "last": "&#10095;" },
			"processing": "Please wait..."
		},
		"dom": "<'row'<'col-sm-4'f><'col-sm-4'><'col-sm-4'B>><'row'<'col-sm-12'tr>><'row'<'col-sm-5'i><'col-sm-3'l><'col-sm-4'p>>",
		"scrollX": true,
		"start": 0,
		"length": 10,
		"stateSave": true,
		"responsive": true,
		"lengthMenu": [[10, 50, 200, 500], [10, 50, 200, 500]],
		"processing": true,
		"serverSide": true,
		"ajax": {
			"url": "v1/data",
			"type": "POST",
			"error": function(jqXHR, textStatus, errorThrown) {
				/*if(jqXHR.status!=200){
					var settings = $('#tblList').DataTable();
					console.log(settings);
				}*/
				/*alert(jqXHR.status +" " + jqXHR.statusText);*/
				$("#tblList_processing").hide();
			}
		},
		"columns": [
			{
				"targets": 0, "data": null, "orderable": false, "className": "text-end", render: function(data, type, row, meta) {
					return meta.row + meta.settings._iDisplayStart + 1;
				}
			},
			{ "targets": 1, "data": "name" },
			{
				"targets": 2, "data": "dob", "className": "text-end", render: function(data, type, row, meta) {
					return new Date(data).toISOString().substring(0, 10);
				}
			},
			{ "targets": 3, "data": "phone", "className": "text-end" },
			{ "targets": 4, "data": "email" },
			{ "targets": 5, "data": "city" },
			{ "targets": 6, "data": "pincode", "className": "text-end" },
			{ "targets": 7, "data": "state" },
			{
				"targets": 8, "data": "createDate", "className": "text-end", render: function(data, type, row, meta) {
					return new Date(data).toISOString().substring(0, 19);
				}
			},
			{
				"targets": 9, "data": "updateDate", "className": "text-end", render: function(data, type, row, meta) {
					return new Date(data).toISOString().substring(0, 19);
				}
			}
		],
		"order": [[1, "asc"]],
		"buttons": ["excelHtml5", "csvHtml5", "pdfHtml5", "print"],
		"fnServerParams": function(data) {
			data['order'].forEach(function(items, index) {
				data['order'][index]['column'] = data['columns'][items.column]['data'];
			});
		}
	});
	$('div.dataTables_filter input').attr('title', 'Press enter to filter');
	$('div.dataTables_filter input').focus();
	$('div.dataTables_filter input').unbind();
	$('div.dataTables_filter input').bind('keyup change', function(e) {
		if (e.keyCode === 13) {
			tbl.search($(this).val()).draw();
		}
	});
});