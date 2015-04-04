<%@ tag %>
<%@ attribute name="title" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/favicon.ico" rel="shortcut icon" type="image/x-icon" />
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
		<script type="text/javascript" src="//code.jquery.com/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
		
		<!-- /.table sort javascript -->
		<script src="${pageContext.request.contextPath}/js/sortable.js"></script>

		<title>LAGERSTYRING - ${title}</title>
	</head>
	<body>
	<c:import url="/views/nav.jsp" />
    <div class="container">
		<jsp:doBody/>
    </div><!-- /.container -->
	</body>
</html>