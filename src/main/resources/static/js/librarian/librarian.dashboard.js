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

});