#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="homeApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>homepage</title>

    <jsp:include page="/header-css.jsp"></jsp:include>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#!">Home</a></li>
                <li><a href="/user">Login</a></li>
                <li><a href="#!about">About</a></li>
                <li><a href="#!imprint">Imprint</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<!-- Begin page content -->
<div class="container-fluid" ng-view>
</div>

<!-- footer -->
<div class="footer">
    <div class="container">
        <p class="text-muted">
            Place footer content here
            <sec:authorize access="isAuthenticated()">
                User (<sec:authentication property="principal.email" />) is authenticated
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                AND THE USER IS ADMIN
            </sec:authorize>
            <br/>
            <sec:authorize access="isAuthenticated()"><a href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">Logout</a></sec:authorize>
        </p>
    </div>
</div>

<jsp:include page="/footer-js.jsp"></jsp:include>

</body>
</html>