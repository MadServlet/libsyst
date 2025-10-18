$(document).ready(function () {
    // API constants
    const API_BASE_URL = '/api/user';
    const PAGE_SIZE = 10;

    // Cache selectors for modal elements
    const $modal = $('#custom-modal');
    const $editUserForm = $('#editUserForm');
    const $editUserId = $('#edit-user-id');
    const $editFullName = $('#edit-full-name');
    const $editEmail = $('#edit-email');
    const $editPassword = $('#edit-password');
    var $searchParam = $('#searchUsers').val()

    // Function to show the modal
    function showModal() {
        $modal.removeClass('hidden');
    }

    // Function to hide the modal and clear the form
    function hideModal() {
        $modal.addClass('hidden');
        $editUserForm[0].reset();
    }

    // Function to populate and show the modal
    function populateAndShowModal($row) {
        const userId = $row.data('user-id');
        const fullName = $row.find('td').eq(0).text().trim();
        const email = $row.find('td').eq(1).text().trim();

        $editUserId.val(userId);
        $editFullName.val(fullName);
        $editEmail.val(email);
        $editPassword.val("");

        showModal();
    }

    // New function to fetch and render user data for a specific page
    function fetchUsers(searchText, userType, pageNumber) {
        $searchParam = searchText;
        const requestData = {
            "email": null,
            "fullName": null,
            "role": userType.toUpperCase()
        };

        if(searchText.includes("@")) {
            requestData['email'] = searchText;
            console.log("its an email")
        }else if (searchText.length > 0 ){
            requestData['fullName'] = searchText;
            console.log("its a name")
        }

        $.ajax({
            url: `${API_BASE_URL}/find/bulk?page=${pageNumber}&size=${PAGE_SIZE}`,
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify(requestData),
            success: function (response) {
                const tableBody = $(`#${userType}sTable tbody`);
                tableBody.empty();

                // Render new rows from API response
                if (response.content && response.content.length > 0) {
                    response.content.forEach(user => {
                        const newRow = `<tr data-user-id="${user.id}">
                                            <td>${user.fullName}</td>
                                            <td>${user.email}</td>
                                            <td class="action-buttons">
                                                <button class="edit-btn"><i class="fas fa-edit"></i> Edit</button>
                                                <button class="delete-btn"><i class="fas fa-trash"></i> Delete</button>
                                            </td>
                                        </tr>`;
                        tableBody.append(newRow);
                    });
                } else {
                    tableBody.append('<tr><td colspan="3">No users found.</td></tr>');
                }

                renderPagination(userType, response.page.totalPages, pageNumber);
            },
            error: function (error) {
                console.error(`Error fetching ${userType} data:`, error);
                $(`#${userType}sTable tbody`).empty().append('<tr><td colspan="3">Failed to load data.</td></tr>');
            }
        });
    }

    // New function to render pagination controls
    function renderPagination(userType, totalPages, currentPage) {
        const $paginationContainer = $(`#${userType}s-pagination-container`);

        // Safety check: ensure the container element exists
        if ($paginationContainer.length === 0) {
            console.error(`Pagination container #${userType}s-pagination-container not found.`);
            return;
        }

        $paginationContainer.empty();
        const $ul = $('<ul class="pagination"></ul>');

        // Add 'Previous' button
        $ul.append(`
            <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}"><i class="fas fa-angle-left"></i> Previous</a>
            </li>
        `);

        // Logic to determine the pages to display in the sliding window
        const pagesToShow = 5; // Number of page buttons to show at once (e.g., 5)
        let startPage = Math.max(0, currentPage - Math.floor(pagesToShow / 2));
        let endPage = Math.min(totalPages - 1, startPage + pagesToShow - 1);

        if (endPage - startPage < pagesToShow - 1) {
            startPage = Math.max(0, endPage - pagesToShow + 1);
        }

        // Add first page button if it's not in the window
        if (startPage > 0) {
            $ul.append(`
                <li class="page-item"><a class="page-link" href="#" data-page="0">1</a></li>
            `);
            if (startPage > 1) {
                $ul.append(`<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`);
            }
        }

        // Add page number buttons within the sliding window
        for (let i = startPage; i <= endPage; i++) {
            $ul.append(`
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
            `);
        }

        // Add last page button if it's not in the window
        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                $ul.append(`<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`);
            }
            $ul.append(`
                <li class="page-item"><a class="page-link" href="#" data-page="${totalPages - 1}">${totalPages}</a></li>
            `);
        }

        // Add 'Next' button
        $ul.append(`
            <li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}">Next <i class="fas fa-angle-right"></i></a>
            </li>
        `);

        $paginationContainer.append($ul);
    }

    // Initial data load for both tables
     fetchUsers('', 'student', 0); // Load first page of students
     fetchUsers('', 'teacher', 0); // Load first page of teachers

    // Event listener for pagination links using event delegation
    $('.users-container').on('click', '.pagination a.page-link', function (event) {
        event.preventDefault();
        const $link = $(this);

        const pageNumber = $link.data('page');
        const userType = $link.closest('.table-section').find('h2 i').hasClass('fa-user-graduate') ? 'student' : 'teacher';

        if (!$link.closest('li').hasClass('disabled')) {
            fetchUsers($searchParam, userType, pageNumber);
        }
    });

    // Event listener for all edit buttons
    $('.users-container').on('click', '.edit-btn', function (event) {
        event.preventDefault();
        const $row = $(this).closest('tr');
        populateAndShowModal($row);
    });

    // Event listener for modal close buttons and overlay
    $modal.on('click', '.close-modal-btn, .modal-container', function (event) {
        if ($(event.target).is('.close-modal-btn, .modal-container')) {
            hideModal();
        }
    });

    // Event listener for Escape key to close modal
    $(document).on('keydown', function (event) {
        if (event.key === 'Escape' && !$modal.hasClass('hidden')) {
            hideModal();
        }
    });

    // Handle form submission inside the modal
    $editUserForm.on('submit', function (event) {
        event.preventDefault();
        const formData = {
            id: $editUserId.val(),
            fullName: $editFullName.val(),
            email: $editEmail.val(),
            password: $editPassword.val()
        };

        $.ajax({
            url: `${API_BASE_URL}/update`,
            method: 'PATCH',
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {

                const userType = $(`tr[data-user-id="${formData.id}"]`).closest('.table-section').find('h2 i').hasClass('fa-user-graduate') ? 'student' : 'teacher';
                const currentPage = parseInt($(`#${userType}s-pagination-container .page-item.active a`).data('page'));

                fetchUsers($searchParam, userType, currentPage);
                hideModal();

                alert("success");
            },
            error: function (error) {
                console.error(`Error updating, data: ${error}`, error);
                alert("failed");
            }
        });
    });

    // Delete user
    $('.users-container').on('click', '.delete-btn', function (event) {
        event.preventDefault();
        const $row = $(this).closest('tr');
        const id = $row.data('user-id');
        const name = $row.find('td').eq(0).text();
        const userType = $row.closest('.table-section').find('h2').text().trim();

        if (confirm(`Are you sure you want to delete ${userType}: ${name}?`)) {
            // Your delete logic here
            // On success, refresh the table
            // fetchUsers(userType, currentPage);
            $row.remove();
            console.log(`Deleted ${userType} with ID: ${id}`);
        }
    });

    // Search functionality
    $('#searchUsers').on('keyup', function () {
        if (event.key === 'Enter') {
            const searchText = $(this).val().toLowerCase();
            fetchUsers(searchText, 'student', 0); // Load first page of students
            fetchUsers(searchText, 'teacher', 0); // Load first page of teachers
        }
    });

});