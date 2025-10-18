function createUser(e) {
    e.preventDefault();

    const data = {
        email: $('#email').val().trim(),
        fullName: $('#fullname').val().trim(),
        role: $('#role').val(),
        password: $('#password').val()
    };

    console.log("Submitting user data:", data);

    $.ajax({
        url: "/api/user/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response, textStatus, xhr) {
            const statusCode = xhr.status;
            console.log("Response code:", statusCode);

            if (statusCode === 201 || statusCode === 200) {
                $('#userCreateMsg').html(
                    `<span style="color:green;"><i class="fas fa-check-circle"></i> User account for <b>${data.fullName}</b> created successfully!</span>`
                );
                $('#createUserForm')[0].reset();
            } else {
                $('#userCreateMsg').html(
                    `<span style="color:orange;"><i class="fas fa-exclamation-circle"></i> Request completed with status code: ${statusCode}</span>`
                );
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            const statusCode = xhr.status;
            console.error("Error:", statusCode, errorThrown);

            let message;
            if (statusCode === 400) {
                message = "Invalid request data.";
            } else if (statusCode === 401) {
                message = "Unauthorized — please log in.";
            } else if (statusCode === 403) {
                message = "Forbidden — insufficient permissions.";
            } else if (statusCode === 404) {
                message = "Endpoint not found.";
            } else if (statusCode === 500) {
                message = "Server error — please try again later.";
            } else {
                message = `Unexpected error (${statusCode}): ${xhr.responseText || errorThrown}`;
            }

            $('#userCreateMsg').html(
                `<span style="color:red;"><i class="fas fa-times-circle"></i> ${message}</span>`
            );
        }
    });
}
