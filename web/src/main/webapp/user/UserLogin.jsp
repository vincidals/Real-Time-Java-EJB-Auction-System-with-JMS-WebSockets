<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/custom_styles.css">
</head>
<body>

<div class="container mt-5 d-flex flex-column align-items-center justify-content-center">

    <h1 class="mb-4">User Login</h1>

    <form action="/BCDOnlineAuctions/UserLoginServlet" method="post" class="w-50">
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" name="email" id="email" class="form-control">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" name="password" id="password" class="form-control">
        </div>

        <% String error = request.getParameter("error"); %>

        <% if ("emptyFields".equals(error)) { %>
        <h2 style="color:red;">Please fill out all fields.</h2>
        <% } else if ("invalidEmail".equals(error)) { %>
        <h2 style="color:red;">Invalid email.</h2>
        <% } else if ("shortPassword".equals(error)) { %>
        <h2 style="color:red;">Invalid Password</h2>
        <% } else if ("userNotFound".equals(error)) { %>
        <h2 style="color:red;">User doesnt exist, Register</h2>
        <% } else if ("wrongPassword".equals(error)) { %>
        <h2 style="color:red;">Incorrect Password</h2>
        <% } %>


        <div class="d-flex justify-content-between">
            <a href="UserRegister.jsp" class="btn btn-secondary col">Register</a>
            <button type="submit" class="btn btn-danger col">Login</button>

        </div>
    </form>

</div>

<script src="../js/jquery.js"></script>
<script src="../js/custom_script.js"></script>

</body>
</html>
