<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/custom_styles.css">
</head>
<body>

<div class="container mt-5 d-flex flex-column align-items-center justify-content-center">

    <h1 class="mb-4">Admin Login</h1>

    <form action="/BCDOnlineAuctions/AdminLoginServlet" method="post" class="w-50">
        <div class="mb-3">
            <label for="admin_username" class="form-label">Admin Username</label>
            <input type="text" name="admin_username" id="admin_username" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="admin_password" class="form-label">Admin Password</label>
            <input type="password" name="admin_password" id="admin_password" class="form-control" required>
        </div>

        <% String error = request.getParameter("error"); %>

        <% if ("emptyFields".equals(error)) { %>
        <h2 style="color:red;">Please fill out all fields.</h2>
        <% } else if ("invalidUsername".equals(error)) { %>
        <h2 style="color:red;">Invalid username.</h2>
        <% } else if ("shortPassword".equals(error)) { %>
        <h2 style="color:red;">Invalid Password</h2>
        <% } else if ("userNotFound".equals(error)) { %>
        <h2 style="color:red;">Admin doesnt exist, try again.</h2>
        <% } else if ("wrongPassword".equals(error)) { %>
        <h2 style="color:red;">Incorrect Password</h2>
        <% } %>

        <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-danger col">Login</button>

        </div>
    </form>

</div>

<script src="../js/jquery.js"></script>
<script src="../js/custom_script.js"></script>

</body>
</html>
