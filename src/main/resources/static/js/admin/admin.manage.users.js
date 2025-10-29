$(document).ready(function () {
    // API constants
    const API_BASE_URL = '/api/user';
    const PAGE_SIZE = 10;

    const $modalEl = $('#custom-modal');
    const $editUserForm = $('#editUserForm');
    const $editUserId = $('#edit-user-id');
    const $editFullName = $('#edit-full-name');
    const $editEmail = $('#edit-email');
    const $editPassword = $('#edit-password');
    let $searchParam = $('#searchUsers').val() || '';

    const bootstrapModal = $modalEl.length ? new bootstrap.Modal($modalEl[0]) : null;

    $modalEl.on('hidden.bs.modal', function () {
        if ($editUserForm && $editUserForm[0]) $editUserForm[0].reset();
    });

    function populateAndShowModal($row) {
        const userId = $row.data('user-id');
        const fullName = $row.find('td').eq(0).text().trim();
        const email = $row.find('td').eq(1).text().trim();

        $editUserId.val(userId);
        $editFullName.val(fullName);
        $editEmail.val(email);
        $editPassword.val("");

        if (bootstrapModal) bootstrapModal.show();
    }

    function fetchUsers(searchText, userType, pageNumber) {
        $searchParam = searchText;
        const requestData = {
            "email": null,
            "fullName": null,
            "role": userType.toUpperCase()
        };

        if (searchText.includes("@")) {
            requestData.email = searchText;
        } else if (searchText.length > 0) {
            requestData.fullName = searchText;
        }

        $.ajax({
            url: `${API_BASE_URL}/find/bulk?page=${pageNumber}&size=${PAGE_SIZE}`,
            method: 'POST',
            contentType: "application/json",
            data: JSON.stringify(requestData),
            success: function (response) {
                const tableBody = $(`#${userType}sTable tbody`);
                tableBody.empty();

                if (response.content && response.content.length > 0) {
                    response.content.forEach(user => {
                        const newRow = `<tr data-user-id="${user.id}">
                                            <td>${user.fullName}</td>
                                            <td>${user.email}</td>
                                            <td class="text-center action-buttons">
                                                <button class="btn btn-primary shadow me-2" data-bs-toggle="modal" data-bs-target="#custom-modal"><i class="fas fa-edit me-1"></i> Edit</button>
                                                <button class="btn btn-danger shadow"><i class="fas fa-trash me-1"></i> Delete</button>
                                            </td>
                                        </tr>`;
                        tableBody.append(newRow);
                    });
                } else {
                    tableBody.append('<tr><td colspan="3">No users found.</td></tr>');
                }

                renderPagination(userType, (response.page && response.page.totalPages) || 0, pageNumber);
            },
            error: function (error) {
                console.error(`Error fetching ${userType} data:`, error);
                $(`#${userType}sTable tbody`).empty().append('<tr><td colspan="3">Failed to load data.</td></tr>');
            }
        });
    }

    // Render pagination controls
    function renderPagination(userType, totalPages, currentPage) {
        const $paginationContainer = $(`#${userType}s-pagination-container`);
        if ($paginationContainer.length === 0) return;

        $paginationContainer.empty();
        const $ul = $('<ul class="pagination justify-content-end mb-0"></ul>');

        $ul.append(`
            <li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage - 1}"><i class="fas fa-angle-left"></i> Previous</a>
            </li>
        `);

        const pagesToShow = 5;
        let startPage = Math.max(0, currentPage - Math.floor(pagesToShow / 2));
        let endPage = Math.min(totalPages - 1, startPage + pagesToShow - 1);
        if (endPage - startPage < pagesToShow - 1) startPage = Math.max(0, endPage - pagesToShow + 1);

        if (startPage > 0) {
            $ul.append(`<li class="page-item"><a class="page-link" href="#" data-page="0">1</a></li>`);
            if (startPage > 1) $ul.append(`<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`);
        }

        for (let i = startPage; i <= endPage; i++) {
            $ul.append(`
                <li class="page-item ${i === currentPage ? 'active' : ''}">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
            `);
        }

        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) $ul.append(`<li class="page-item disabled"><a class="page-link" href="#">...</a></li>`);
            $ul.append(`<li class="page-item"><a class="page-link" href="#" data-page="${totalPages - 1}">${totalPages}</a></li>`);
        }

        $ul.append(`
            <li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
                <a class="page-link" href="#" data-page="${currentPage + 1}">Next <i class="fas fa-angle-right"></i></a>
            </li>
        `);

        $paginationContainer.append($ul);
    }

    // Initial load
    fetchUsers('', 'student', 0);
    fetchUsers('', 'teacher', 0);

    // Pagination click (delegated)
    $('.users-container').on('click', '.pagination a.page-link', function (event) {
        event.preventDefault();
        const $link = $(this);
        if ($link.closest('li').hasClass('disabled')) return;

        const pageNumber = parseInt($link.data('page'), 10) || 0;
        const userType = $link.closest('.table-section').find('h2').data('user-type') || ($link.closest('.table-section').find('h2 i').hasClass('fa-user-graduate') ? 'student' : 'teacher');

        fetchUsers($searchParam || '', userType, pageNumber);
    });

    // Edit button (delegated) - any button that opens the custom modal
    $('.users-container').on('click', 'button[data-bs-target="#custom-modal"]', function (event) {
        event.preventDefault();
        const $row = $(this).closest('tr');
        populateAndShowModal($row);
    });

    // Form submit (update user)
    $editUserForm.on('submit', function (event) {
        event.preventDefault();
        const formData = {
            id: $editUserId.val(),
            fullName: $editFullName.val(),
            email: $editEmail.val(),
            password: $editPassword.val()
        };

        $.ajax({
            url: `${API_BASE_URL}/updat`,
            method: 'PATCH',
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                const $row = $(`tr[data-user-id="${formData.id}"]`);
                const userType = $row.closest('.table-section').find('h2').data('user-type') || ($row.closest('.table-section').find('h2 i').hasClass('fa-user-graduate') ? 'student' : 'teacher');
                const $activePageLink = $(`#${userType}s-pagination-container .page-item.active a`);
                const currentPage = parseInt($activePageLink.data('page'), 10) || 0;

                fetchUsers($searchParam || '', userType, currentPage);
                if (bootstrapModal) bootstrapModal.hide();
                showResultModal("Account Updated", true)
            },
            error: function (error) {
                console.error(`Error updating:`, error);
                showResultModal("Update Failed, Please contact your local Administrator.", false)
            }
        });
    });

    // Delete user (delegated)
    $('.users-container').on('click', '.btn-danger', function (event) {
        event.preventDefault();
        const $row = $(this).closest('tr');
        const id = $row.data('user-id');
        const name = $row.find('td').eq(0).text();
        const userType = $row.closest('.table-section').find('h2').data('user-type');
        const $activePageLink = $(`#${userType}s-pagination-container .page-item.active a`);
        const currentPage = parseInt($activePageLink.data('page'), 10) || 0;

        if (confirm(`Are you sure you want to delete ${userType}: ${name}?`)) {
            $.ajax({
                url: `${API_BASE_URL}/delete?id=${id}`,
                method: 'DELETE',
                success: function () {
                    fetchUsers($searchParam || '', userType, currentPage);
                    alert("success");
                },
                error: function (error) {
                    console.error(`Error deleting:`, error);
                    alert("failed");
                }
            });
        }
    });

    // Search on Enter
    $('#searchUsers').on('keyup', function (event) {
        if (event.key === 'Enter') {
            const searchText = $(this).val().toLowerCase();
            $searchParam = searchText;
            fetchUsers(searchText, 'student', 0);
            fetchUsers(searchText, 'teacher', 0);
        }
    });

    function showResultModal(message, isSuccess) {
        let $resultModal = $('#result-modal');
        if ($resultModal.length === 0) {
            const modalHtml = `
                <div class="modal fade" id="result-modal" tabindex="-1" aria-hidden="true">
                  <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="resultModalLabel"></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body" id="resultModalBody"></div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                      </div>
                    </div>
                  </div>
                </div>`;
            $('body').append(modalHtml);
            $resultModal = $('#result-modal');
        }

        $('#resultModalLabel').text(isSuccess ? 'Success' : 'Error');
        $('#resultModalBody').text(message);

        const resultModalEl = document.getElementById('result-modal');
        const resultModalInstance = new bootstrap.Modal(resultModalEl);
        resultModalInstance.show();
    }
});