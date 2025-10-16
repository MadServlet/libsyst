function showPassword() {
	const passwordField = document.getElementById('password');
	const eyeIcon = document.querySelector('.password-wrapper i');
	if (passwordField.type === "password") {
		passwordField.type = "text";
		eyeIcon.classList.remove('fa-eye');
		eyeIcon.classList.add('fa-eye-slash');
	} else {
		passwordField.type = "password";
		eyeIcon.classList.remove('fa-eye-slash');
		eyeIcon.classList.add('fa-eye');
	}
}

document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById('username').value;
    const pw = document.getElementById('password').value;

    try {
        const response = await fetch("/api/auth", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                "username": name,
                "password": pw
            })
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem("token", data.token);
            window.location.href = "/web/index";
        } else {
            alert("❌ " + data.error);
        }

    } catch (err) {
        alert("❌ Network error");
    }
});