<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Register</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/custom_styles.css">
</head>
<body>

<div class="container mt-5 d-flex flex-column align-items-center justify-content-center">

    <h1 class="mb-4">User Register</h1>

    <form action="/BCDOnlineAuctions/UserRegisterServlet" method="post" class="w-50">
        <div class="mb-3">
            <label for="fullName" class="form-label">Full Name</label>
            <input type="text" name="fullName" id="fullName" class="form-control">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" name="email" id="email" class="form-control">
        </div>
        <div class="mb-3">
            <label for="number" class="form-label">Number</label>
            <input type="tel" name="number" id="number" class="form-control">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" name="password" id="password" class="form-control">
        </div>

        <% String error = request.getParameter("error"); %>
        <% if ("emptyFields".equals(error)) { %>
        <h2 style="color:red;">Please fill out all fields</h2>
        <% } else if ("nameTooLong".equals(error)) { %>
        <h2 style="color:red;">Name too long.</h2>
        <% } else if ("invalidEmail".equals(error)) { %>
        <h2 style="color:red;">Invalid user email.</h2>
        <% } else if ("shortPassword".equals(error)) { %>
        <h2 style="color:red;">Invalid Password.</h2>
        <% } else if ("invalidNumber".equals(error)) { %>
        <h2 style="color:red;">Invalid Number.</h2>
        <% } else if ("userExists".equals(error)) { %>
        <h2 style="color:red;">Email already in use.</h2>
        <% } %>

        <div class="d-flex justify-content-between">
            <a href="UserLogin.jsp" class="btn btn-secondary col">log In</a>
            <button type="submit" class="btn btn-danger col">Register</button>

        </div>
    </form>

</div>

<script src="../js/jquery.js"></script>
<script src="../js/custom_script.js"></script>

</body>
</html>
