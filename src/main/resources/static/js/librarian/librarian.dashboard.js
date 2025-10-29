$(document).ready(function () {
    const API_BASE_URL = '/api/books';
    $.ajax({
        url: `${API_BASE_URL}/count`,
        method: 'GET',
        success: function (response) {
            $("#bookCount").text(response.totalCount.toLocaleString('en-US'));
        },
        error: function (error) {
            console.error(`Error fetching, data: ${error}`, error);
        }
    });

     var modal = new bootstrap.Modal($('#requestModal')[0]);

    $('#requestNotifications tr').each(function() {
        $(this).find('button').click(() => {
            var date = $(this).children().eq(0).text();
            var name = $(this).children().eq(1).text();
            var title = $(this).children().eq(2).text();

            $('#modalDate').text(date);
            $('#modalName').text(name);
            $('#modalTitle').text(title);

            modal.show();
        });
    });

});