$(document).ready(function () {
    // API constants
    const API_BASE_URL = '/api/user/find/bulk';
    const PAGE_SIZE = 5;

    // Cache selectors for modal elements
    const $modal = $('#custom-modal');
    const $editUserForm = $('#editUserForm');
    const $editUserId = $('#edit-user-id');
    const $editFullName = $('#edit-full-name');
    const $editEmail = $('#edit-email');
    const $editPassword = $('#edit-password');

    // Function to show the modal
    function showModal() {
        $modal.removeClass('hidden');
    }

    // Function to hide the modal and clear the form
    function hideModal() {
        $modal.addClass('hidden');
        $editUserForm.reset();
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
            url: `${API_BASE_URL}?page=${pageNumber}&size=${PAGE_SIZE}`,
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

                // Generate and render the pagination links
                renderPagination(userType, response.totalPages, response.currentPage);
            },
            error: function (error) {
                console.error(`Error fetching ${userType} data:`, error);
                $(`#${userType}sTable tbody`).empty().append('<tr><td colspan="3">Failed to load data.</td></tr>');
            }
        });
    }

    // New function to render pagination controls
    function renderPagination(userType, totalPages, currentPage) {
        const $paginationContainer = $(`#${userType}-pagination-container`);
        $paginationContainer.empty();

        const $ul = $('<ul class="pagination"></ul>');

        // Add 'Previous' button
        $ul.append(`
            <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}"><i class="fas fa-angle-left"></i> Previous</a>
            </li>
        `);

        // Add page number buttons
        for (let i = 0; i < totalPages; i++) {
            $ul.append(`
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
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
            fetchUsers(userType, pageNumber);
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

        console.log('Saving user data:', formData);

        // You would perform your AJAX call to update the user here
        // Then, after a successful update, refresh the current page of the table
        const userType = $(`tr[data-user-id="${formData.id}"]`).closest('.table-section').find('h2 i').hasClass('fa-user-graduate') ? 'student' : 'teacher';
        const currentPage = parseInt($(`#${userType}-pagination-container .page-item.active a`).data('page'));

        // This is a placeholder call; replace with your actual API call.
        // fetchUsers(userType, currentPage);
        hideModal();
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