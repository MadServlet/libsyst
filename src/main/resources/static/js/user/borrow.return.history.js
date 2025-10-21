function addHistoryEntry(bookTitle, author, status, dateTime) {
    const table = document.getElementById('historyTable');
    const newRow = document.createElement('tr');

    newRow.innerHTML = `
        <td>${bookTitle}</td>
        <td>${author}</td>
        <td>${status}</td>
        <td>${dateTime}</td>
    `;

    table.appendChild(newRow);

    // Display notification
    const notificationArea = document.getElementById('notificationArea');
    const notificationMessage = document.getElementById('notificationMessage');

    notificationMessage.textContent = `Successfully ${status.toLowerCase()} "${bookTitle}" on ${dateTime}.`;
    notificationArea.style.display = "block";

    // Hide notification after 5 seconds
    setTimeout(() => {
        notificationArea.style.display = "none";
    }, 5000);
}

// Example: Simulate real-time updates
setTimeout(() => addHistoryEntry('Ibong Adarna', 'Anonymous', 'Borrowed', '2023-10-15 09:30AM'), 3000);
setTimeout(() => addHistoryEntry('Mga Kwento ni Lola Basyang', 'Severino Reyes', 'Returned', '2023-10-16 11:45AM'), 6000);