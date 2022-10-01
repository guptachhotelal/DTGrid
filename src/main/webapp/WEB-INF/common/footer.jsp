<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<br />
<div style="position: fixed; bottom: 0px; width: 100%; text-align: center; font-size: 10px;">
<jsp:useBean id="now" class="java.util.Date" />&copy; <fmt:formatDate pattern="yyyy" value="${now}" /> All rights reserved.
</div>