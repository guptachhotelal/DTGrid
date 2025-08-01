<%@ page language="java" contentType="text/html;  charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Home</title>
<%@include file="../common/include.jsp"%>
<script type="text/javascript" src="js/fetcher.js"></script>
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
					<th class="text-center">Email</th>
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