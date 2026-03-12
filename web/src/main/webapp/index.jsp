<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.ee.core.model.AuctionItem" %>

<%
    List<AuctionItem> auctionItems = (List<AuctionItem>) request.getAttribute("activeAuctionItems");
    if (auctionItems == null) {
        auctionItems = new java.util.ArrayList<>();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Online Auctions</title>
    <link rel="stylesheet" href="css/bootstrap.css" />
    <link rel="stylesheet" href="css/custom_styles.css" />
</head>
<body>

<div class="container text-center mt-5">
    <h1>Online Auctions</h1>

    <div class="mt-5">
        <h2>Current Auctions</h2>
        <table class="table table-hover table-bordered text-center align-middle">
            <thead class="table-dark">
            <tr>
                <th>Item Name</th>
                <th>Current Bid (LKR)</th>
                <th>Time Remaining</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

    <div class="mt-4 d-flex justify-content-center gap-3">
        <a href="user/UserLogin.jsp" class="btn btn-dark col">User Login</a>
        <a href="admin/AdminLogin.jsp" class="btn btn-danger col">Admin Login</a>
    </div>
</div>

<script src="js/jquery.js"></script>
<script src="js/custom_script.js"></script>
</body>
</html>
