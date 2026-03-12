function formatTimeRemaining(endTime) {
    const now = new Date().getTime();
    const end = new Date(endTime).getTime();
    let diff = Math.max(end - now, 0);

    const days = Math.floor(diff / (1000 * 60 * 60 * 24));
    const hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
    const minutes = Math.floor((diff / (1000 * 60)) % 60);
    const seconds = Math.floor((diff / 1000) % 60);

    return `${days} days ${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
}

function formatDate(timestamp) {
    const d = new Date(parseInt(timestamp));
    return d.toISOString().slice(0, 16); // yyyy-MM-ddTHH:mm
}

$(document).ready(function () {


    if (window.location.pathname.endsWith('/') || window.location.pathname.endsWith('/index.jsp')) {
        function updateAuctionTable() {
            $.getJSON('/BCDOnlineAuctions/LoadAuctionsServlet')
                .done(function (data) {
                    const $tbody = $('tbody');
                    $tbody.empty();

                    if (data.length === 0) {
                        $tbody.html(`
                            <tr>
                                <td colspan="3">No auctions currently available</td>
                            </tr>`);
                        return;
                    }

                    $.each(data, function (_, item) {
                        const $tr = $(`
                            <tr>
                                <td>${item.title}</td>
                                <td>${item.currentBid}</td>
                                <td>${formatTimeRemaining(item.endDate)}</td>
                            </tr>
                        `);
                        $tbody.append($tr);
                    });
                })
                .fail(function (xhr, status, error) {
                    console.error('Failed to load auctions:', error);
                });
        }

        updateAuctionTable();
        setInterval(updateAuctionTable, 1000);
    }


    if (window.location.pathname.endsWith('/AdminDashboard.jsp')) {
        function updateAdminAuctionTable() {
            $.getJSON('/BCDOnlineAuctions/AdminDashboard')
                .done(function (data) {
                    const $tbody = $('tbody');
                    $tbody.empty();

                    if (data.length === 0) {
                        $tbody.html(`
                            <tr>
                                <td colspan="3">No auctions added yet.</td>
                            </tr>`);
                        return;
                    }
                    $.each(data, function (_, item) {
                        const $row = $(`
                        <tr>
                            <td>
                            <input type="text" name="itemName" class="form-control" value="${item.title}" maxlength="100" required></td>
                            <td><input type="text" name="description" class="form-control" value="${item.description}" maxlength="1000" required></td>
                            <td><input type="number" name="startingBid" class="form-control" value="${item.startingBid}" min="0" required></td>
                            <td>
                                <input type="number" class="form-control" value="${item.currentBid}" readonly>
                                <input type="hidden" name="currentBid" value="${item.currentBid}">
                            </td>
                            <td><input type="datetime-local" name="endTime" class="form-control" value="${formatDate(item.endDate)}" required></td>
                            <td>
                                <select name="status" class="form-control" required>
                                    <option value="1" ${item.status === 1 ? 'selected' : ''}>Active</option>
                                    <option value="0" ${item.status === 0 ? 'selected' : ''}>Inactive</option>
                                </select>
                            </td>
                            <td>
                                    <input type="hidden" name="auctionId" value="${item.id}">
                                    <button type="submit" class="btn btn-success">Update</button>
                            </td>
                        </tr>
                    `);

                        $tbody.append($row);
                    });
                })
                .fail(function (xhr, status, error) {
                    console.error('Failed to load admin auctions:', error);
                });
        }

        updateAdminAuctionTable();
    }


    if (window.location.pathname.endsWith('/UserAuctions.jsp')) {
        function updateUserAuctionTable() {
            $.getJSON('/BCDOnlineAuctions/LoadAuctionsServlet')
                .done(function (data) {
                    const $tbody = $('tbody');
                    $tbody.empty();

                    if (data.length === 0) {
                        $tbody.html(`
                            <tr>
                                <td colspan="3">No auctions currently available</td>
                            </tr>`);
                        return;
                    }

                    $.each(data, function (_, item) {
                        const $row = $(`
                            <tr>
                                <td>${item.title}</td>
                                <td class="current-bid" data-id="${item.id}">${item.currentBid}</td>
                                <td class="time-remaining" data-end="${item.endDate}" data-id="${item.id}">${formatTimeRemaining(item.endDate)}</td>
                                <td>
                                    <form action="/BCDOnlineAuctions/PlaceBidServlet" method="post" class="d-flex gap-2">
                                        <input type="number" name="userBid" class="form-control" min="${item.currentBid + 1000}" required>
                                        <input type="hidden" name="auctionId" value="${item.id}">
                                        <button type="submit" class="btn btn-primary">Place Bid</button>
                                    </form>
                                </td>
                            </tr>
                        `);
                        $tbody.append($row);
                    });

                })
                .fail(function (xhr, status, error) {
                    console.error('Failed to load user auctions:', error);
                });
        }

        updateUserAuctionTable();
        startLiveAuctionUpdates();

    }
    function startLiveAuctionUpdates() {
        setInterval(() => {
            $.getJSON('/BCDOnlineAuctions/LoadAuctionsServlet')
                .done(function (data) {
                    data.forEach(item => {
                        const $bidCell = $(`.current-bid[data-id="${item.id}"]`);
                        const $timeCell = $(`.time-remaining[data-id="${item.id}"]`);

                        // 💸 Update the bid
                        if ($bidCell.length) {
                            $bidCell.text(item.currentBid);
                        }

                        // ⏰ Update the time
                        if ($timeCell.length) {
                            const formattedTime = formatTimeRemaining(item.endDate);
                            $timeCell.text(formattedTime);
                        }
                    });
                })
                .fail(function (xhr, status, error) {
                    console.error('Live update failed:', error);
                });
        }, 1000);
    }


});
