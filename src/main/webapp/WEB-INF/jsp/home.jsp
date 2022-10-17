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
.dataTables_length select {
	margin-top: 10px;
}
#tblList_paginate {
	margin-top: 10px;
}
</style>
</head>
<body>

<div class="container-fluid shadow rounded-2 my-3 col-11">
        <div class="row card card-body rounded-3">
          <table id="tblList" class="table table-striped nowrap responsive" style="width: 100%;">
			<thead>
				<tr>
					<th class="text-center">Sr. No</th>
					<th class="text-center">Name</th>
					<th class="text-center">Date of Birth</th>
					<th class="text-center">Phone</th>
					<th class="text-center">City</th>
					<th class="text-center">Pincode</th>
					<th class="text-center">State</th>
					<th class="text-center">Create Date</th>
					<th class="text-center">Update Date</th>
				</tr>
			</thead>
		</table>
    </div>
  </div> 
	<%@include file="../common/footer.jsp"%>
</body>
</html>