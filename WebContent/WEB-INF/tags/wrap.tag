<%@ tag %>
<%@ attribute name="title" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Billig lager løsning til restauratører, diskoteker mm.">
		<meta name="keywords" content="lager, diskotek, restauratør">
		<link href="${pageContext.request.contextPath}/favicon.ico" rel="shortcut icon" type="image/x-icon" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="print" href="${pageContext.request.contextPath}/css/print.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/style.css">
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.3.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.toaster.js"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<!-- /.table sort javascript -->
		<script src="${pageContext.request.contextPath}/js/sortable.js"></script>

		<title>Lager.io - ${title}</title>
	</head>
	<body>
	<c:import url="/views/nav.jsp" />
    <div class="container">
		<jsp:doBody/>
    </div><!-- /.container -->
    
    
    <script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-61705356-1', 'auto');
	  ga('send', 'pageview');
	</script>
    
    
	</body>
</html>