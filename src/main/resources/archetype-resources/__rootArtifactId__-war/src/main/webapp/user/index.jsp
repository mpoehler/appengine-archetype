#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>user</title>

    <jsp:include page="/header-css.jsp"></jsp:include>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12">
            <h1>user</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            Go to <a href="/admin/">Admin Section</a><br/>
            Go to <a href="/">Homepage</a><br/>
        </div>
        <div class="col-md-6">
            <sec:authorize access="isAuthenticated()">
                User (<sec:authentication property="principal.email" />) is authenticated
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                AND THE USER IS ADMIN
            </sec:authorize>
            <br/>
            <sec:authorize access="isAuthenticated()"><a href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">Logout</a></sec:authorize>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <ul id="list">
            </ul>

            Note: Delete is only allowed for administrators. The Error is expected ;-)
        </div>
    </div>
</div>

<jsp:include page="/footer-js.jsp"></jsp:include>

<script type="text/javascript">
    ${symbol_dollar}( document ).ready(function() {
        listAllPersons();
    });
</script>

</body>
</html>