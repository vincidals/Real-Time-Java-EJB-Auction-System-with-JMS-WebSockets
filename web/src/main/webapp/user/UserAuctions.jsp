<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="lk.jiat.ee.core.model.AuctionItem" %>
<%@ page import="lk.jiat.ee.core.model.User" %>

<%
    User user = (User) session.getAttribute("userSession");
    if (user == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
    List<AuctionItem> auctionItems = (List<AuctionItem>) request.getAttribute("activeAuctionItems");
    if (auctionItems == null) {
        auctionItems = new java.util.ArrayList<>();
    }%>
<html>
<head>
    <title>Live Auctions</title>
    <link rel="stylesheet" href="../css/bootstrap.css">
    <link rel="stylesheet" href="../css/custom_styles.css">
</head>
<body>

<div class="container">
    <div class="row bg-body-secondary p-3 mb-5 align-items-center">
        <h3 class="text-start col">Welcome, <%= user.getFullName() %></h3>
        <div class="text-end col">
            <form action="/BCDOnlineAuctions/UserLogOutServlet" method="post" class="d-inline">
                <button type="submit" class="btn btn-danger">
                    Logout
                </button>
            </form>
        </div>
    </div>

    <h2 class="text-center mb-4">Current Auctions</h2>

    <table class="table table-hover table-bordered text-center align-middle">
        <thead class="table-dark">
        <tr>
            <th>Item Name</th>
            <th>Current Bid (LKR)</th>
            <th>Time Remaining</th>
            <th>Your Bid</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <% String error = request.getParameter("error"); %>
    <% if ("invalidBidAmount".equals(error)) { %>
    <h2 style="color:red;">Please enter greater than 1000 of the current bid</h2>
    <% } else if ("invalidBid".equals(error)) { %>
    <h2 style="color:red;">Error placing bid</h2>
    <% } %>
</div>
<script>
    const socket = new WebSocket("ws://" + window.location.host + "/BCDOnlineAuctions/bidNotifications");

    socket.onmessage = function(event) {
        const message = event.data;
        alert("New Bid Notification\n\n" + message);
    };

    socket.onerror = function(error) {
        console.error("WebSocket error:", error);
    };
</script>
<script src="../js/jquery.js"></script>
<script src="../js/custom_script.js"></script>
</body>
</html>
