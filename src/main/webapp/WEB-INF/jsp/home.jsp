<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html title="${deploymentTime}" lang="en">
<head>
<meta charset="UTF-8">
<title>Home</title>
<%@include file="../common/include.jsp"%>
<script type="text/javascript" src="js/slist.js"></script>
<style type="text/css">
body {
	overflow-y: scroll;
	height: 100%;
}

.dataTables_filter {
	float: left;
}

.dataTables_filter input {
	min-width: 300px;
}

#tblList_processing {
	z-index: 1;
}

.dataTables_length select {
	margin-top: 10px;
}

.rightAlign {
	text-align: right;
}

.centerAlign {
	text-align: center;
}
</style>
</head>
<body>
	<div class="container-fluid" style="width: 80%; margin-top: 10px;">
		<table id="tblList"
			class="table table-striped table-bordered table-sm nowrap responsive"
			style="width: 100%;">
			<thead>
				<tr>
					<th class="centerAlign">Sr. No</th>
					<th class="centerAlign">Name</th>
					<th class="centerAlign">Date of Birth</th>
					<th class="centerAlign">Phone</th>
					<th class="centerAlign">City</th>
					<th class="centerAlign">Pincode</th>
					<th class="centerAlign">State</th>
					<th class="centerAlign">Create Date</th>
					<th class="centerAlign">Update Date</th>
				</tr>
			</thead>
		</table>
	</div>
	<%@include file="../common/footer.jsp"%>
</body>
</html>