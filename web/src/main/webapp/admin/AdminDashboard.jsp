<%@ page import="lk.jiat.ee.core.model.Admin" %>
<%@ page import="lk.jiat.ee.core.model.AuctionItem" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Admin admin = (Admin) session.getAttribute("adminSession");
    if (admin == null) {
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="../css/bootstrap.css">
    <link rel="stylesheet" href="../css/custom_styles.css">
</head>
<body>
<div class="container">
    <div class="row m-3 mb-5 align-items-center">
        <h3 class="text-start text-danger col text-capitalize">Welcome, <%= admin.getAdminUsername() %></h3>
        <h1 class="text-center col">Admin Dashboard</h1>
        <div class="text-end col">
            <form action="/BCDOnlineAuctions/AdminLogOutServlet" method="post" class="d-inline">
                <button type="submit" class="btn btn-danger">Logout</button>
            </form>
        </div>
    </div>

    <form action="/BCDOnlineAuctions/UpdateAuctionItemServlet" method="post">
    <table class="table table-striped table-bordered text-center">
        <thead class="table-dark">
        <tr>
            <th>Item Name</th>
            <th>Description</th>
            <th>Starting Bid</th>
            <th>Current Bid</th>
            <th>End Time</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="auction-items-body">
        </tbody>
    </table>
    </form>

    <hr class="my-5">

    <h4 class="text-center">Add New Auction Item</h4>
    <form action="/BCDOnlineAuctions/AddAuctionItemServlet" method="post" class="row g-3 mt-3">
        <div class="col-md-3">
            <input type="text" name="itemName" class="form-control" placeholder="Item Name" maxlength="100" required>
        </div>
        <div class="col-md-3">
            <input type="text" name="description" class="form-control" placeholder="Description" maxlength="1000" required>
        </div>
        <div class="col-md-2">
            <input type="number" name="startingBid" class="form-control" placeholder="Starting Bid" min="1000" required>
        </div>
        <div class="col-md-3">
            <input type="datetime-local" name="endTime" class="form-control" required>
        </div>
        <div class="col-md-1">
            <button type="submit" class="btn btn-primary">Add</button>
        </div>
    </form>


    <% String error = request.getParameter("error"); %>
    <% String success = request.getParameter("success"); %>

    <% if (error != null) { %>
    <div class="alert alert-danger text-center" role="alert" style="font-weight:bold;">
        <% if ("missingFields".equals(error)) { %>
        Fill out all the fields
        <% } else if ("nameTooLong".equals(error)) { %>
        Item name too long
        <% } else if ("descriptionTooLong".equals(error)) { %>
        Description too long.
        <% } else if ("invalidBid".equals(error)) { %>
        Invalid bid.
        <% } else if ("invalidBidFormat".equals(error)) { %>
        Invalid bid.
        <% } else if ("invalidStatus".equals(error)) { %>
        Select status
        <% } else if ("invalidStatusFormat".equals(error)) { %>
        Invalid status
        <% } else if ("invalidDateFormat".equals(error)) { %>
        Invalid Date
        <% } else if ("endDatePast".equals(error)) { %>
        Invalid date
        <% } else if ("itemNotFound".equals(error)) { %>
        Item missing
        <% } else if ("updateFailed".equals(error)) { %>
        Update failed.
        <% } else { %>
        An unknown error happened. WTF, right?
        <% } %>
    </div>
    <% } %>

    <% if ("itemUpdated".equals(success)) { %>
    <div class="alert alert-success text-center" role="alert" style="font-weight:bold;">
        Item Updated Successfully.
    </div>
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
