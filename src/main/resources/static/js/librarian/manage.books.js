let bookCount = 0;
let editingRow = null; // Track the row being edited


function openModal() {
  document.getElementById("addBookModal").style.display = "block";
}


function closeModal() {
  document.getElementById("addBookModal").style.display = "none";
  editingRow = null; // Reset editing state when modal closes
  document.getElementById("addBookForm").reset();
}


function addBook(event) {
  event.preventDefault();

  const title = document.getElementById("modalBookTitle").value;
  const author = document.getElementById("modalBookAuthor").value;
  const edition = document.getElementById("modalBookEdition").value;
  const publisher = document.getElementById("modalBookPublisher").value;
  const year = document.getElementById("modalBookYear").value;

  if (year < 1000 || year > 2500 || isNaN(year)) {
    alert("Please enter a valid year between 1000 and 2500.");
    return false;
  }

  // If editing, update the existing row
  if (editingRow) {
    editingRow.cells[1].innerText = title;
    editingRow.cells[2].innerText = author;
    editingRow.cells[3].innerText = edition;
    editingRow.cells[4].innerText = publisher;
    editingRow.cells[5].innerText = year;

    // Update QR code
    const qrContainer = editingRow.cells[6];
    qrContainer.innerHTML = "";
    const qrDiv = document.createElement("div");
    qrDiv.style.width = "125px";
    qrDiv.style.height = "125px";
    qrContainer.appendChild(qrDiv);

    const qrContent = "Accession Number: " + editingRow.cells[0].innerText + " | Title: " + title + " | Author: " + author + " | Edition: " + edition + " | Publisher: " + publisher + " | Year: " + year;
    new QRCode(qrDiv, {
      text: qrContent,
      width: 110,
      height: 110,
      colorDark: "#000000",
      colorLight: "#ffffff",
      correctLevel: QRCode.CorrectLevel.H
    });

    const downloadBtn = document.createElement("button");
    downloadBtn.textContent = "Download QR";
    downloadBtn.className = "download-btn";
    downloadBtn.onclick = function () {
      downloadQRCode(qrDiv);
    };
    qrContainer.appendChild(downloadBtn);

    editingRow = null;
    closeModal();
    return false;
  }

  // If not editing, add a new row
  bookCount++;
  const formattedBookCount = bookCount.toString().padStart(4, '0');


  var table = document.getElementById("booksTable");
  var newRow = table.insertRow(-1);


  var cell0 = newRow.insertCell(0);
  var cell1 = newRow.insertCell(1);
  var cell2 = newRow.insertCell(2);
  var cell3 = newRow.insertCell(3);
  var cell4 = newRow.insertCell(4);
  var cell5 = newRow.insertCell(5);
  var cell6 = newRow.insertCell(6);
  var cell7 = newRow.insertCell(7); // Action cell


  cell0.innerHTML = formattedBookCount;
  cell1.innerHTML = title;
  cell2.innerHTML = author;
  cell3.innerHTML = edition;
  cell4.innerHTML = publisher;
  cell5.innerHTML = year;
  cell6.innerHTML = ""; // para sa QR


  var qrContainer = document.createElement("div");
  qrContainer.style.display = "flex";
  qrContainer.style.flexDirection = "column";
  qrContainer.style.alignItems = "center";


  var qrDiv = document.createElement("div");
  qrDiv.style.width = "125px";
  qrDiv.style.height = "125px";
  qrContainer.appendChild(qrDiv);


  var qrContent = "Accession Number: " + formattedBookCount + " | Title: " + title + " | Author: " + author + " | Edition: " + edition + " | Publisher: " + publisher + " | Year: " + year;
  new QRCode(qrDiv, {
    text: qrContent,
    width: 110,
    height: 110,
    colorDark: "#000000",
    colorLight: "#ffffff",
    correctLevel: QRCode.CorrectLevel.H
  });


  var downloadBtn = document.createElement("button");
  downloadBtn.textContent = "Download QR";
  downloadBtn.className = "download-btn";
  downloadBtn.onclick = function () {
    downloadQRCode(qrDiv);
  };
  qrContainer.appendChild(downloadBtn);

  cell6.appendChild(qrContainer);

  // Add Edit and Delete buttons
  cell7.innerHTML = `
    <button class="edit-btn" onclick="editRow(this)">Edit</button>
    <button class="delete-btn" onclick="deleteRow(this)">Delete</button>
  `;

  document.getElementById("addBookForm").reset();
  closeModal();
  return false;
}

// Edit row function (does NOT delete the row, just sets editingRow)
function editRow(btn) {
  const row = btn.closest("tr");
  // Get current values
  const title = row.cells[1].innerText;
  const author = row.cells[2].innerText;
  const edition = row.cells[3].innerText;
  const publisher = row.cells[4].innerText;
  const year = row.cells[5].innerText;

  // Fill modal with current values
  document.getElementById("modalBookTitle").value = title;
  document.getElementById("modalBookAuthor").value = author;
  document.getElementById("modalBookEdition").value = edition;
  document.getElementById("modalBookPublisher").value = publisher;
  document.getElementById("modalBookYear").value = year;

  editingRow = row; // Set the row being edited

  // Open modal for editing
  openModal();
}

function searchBooks() {

  var input = document.getElementById("searchInput").value.toLowerCase();
  var table = document.getElementById("booksTable");
  var rows = table.getElementsByTagName("tr");


  for (var i = 1; i < rows.length; i++) {
    var cells = rows[i].getElementsByTagName("td");
    var match = false;


    for (var j = 0; j < cells.length; j++) {
      if (cells[j]) {
        var cellText = cells[j].textContent || cells[j].innerText;
        if (cellText.toLowerCase().indexOf(input) > -1) {
          match = true;
          break;
        }
      }
    }


    rows[i].style.display = match ? "" : "none";
  }
}

function handleBulkImport(event) {
  const file = event.target.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = function (e) {
    const data = e.target.result;
    const workbook = XLSX.read(data, { type: "binary" });
    const sheetName = workbook.SheetNames[0];
    const sheet = workbook.Sheets[sheetName];
    const rows = XLSX.utils.sheet_to_json(sheet, { header: 1 });

    // Skip the header row and process the data
    for (let i = 1; i < rows.length; i++) {
      const row = rows[i];
      if (!row[0] && !row[1]) continue; // Skip empty rows

      // Map columns: [Title, Author, Edition, Publisher, Year]
      const title = row[0] || "";
      const author = row[1] || "";
      const edition = row[2] || "";
      const publisher = row[3] || "";
      const year = row[4] || "";

      // Validate year
      if (year < 1000 || year > 2500 || isNaN(year)) continue;

      // Add the book to the table
      addBookToTable(title, author, edition, publisher, year);
    }
    alert("Bulk import completed!");
  };

  reader.readAsBinaryString(file);
}

function addBookToTable(title, author, edition, publisher, year) {
  bookCount++;
  const formattedBookCount = bookCount.toString().padStart(4, "0");

  const table = document.getElementById("booksTable");
  const newRow = table.insertRow(-1);

  const cell0 = newRow.insertCell(0);
  const cell1 = newRow.insertCell(1);
  const cell2 = newRow.insertCell(2);
  const cell3 = newRow.insertCell(3);
  const cell4 = newRow.insertCell(4);
  const cell5 = newRow.insertCell(5);
  const cell6 = newRow.insertCell(6);
  const cell7 = newRow.insertCell(7); // Action cell

  cell0.innerHTML = formattedBookCount;
  cell1.innerHTML = title;
  cell2.innerHTML = author;
  cell3.innerHTML = edition;
  cell4.innerHTML = publisher;
  cell5.innerHTML = year;

  const qrContainer = document.createElement("div");
  qrContainer.style.display = "flex";
  qrContainer.style.flexDirection = "column";
  qrContainer.style.alignItems = "center";

  const qrDiv = document.createElement("div");
  qrDiv.style.width = "125px";
  qrDiv.style.height = "125px";
  qrContainer.appendChild(qrDiv);

  const qrContent = `Accession Number: ${formattedBookCount} | Title: ${title} | Author: ${author} | Edition: ${edition} | Publisher: ${publisher} | Year: ${year}`;
  new QRCode(qrDiv, {
    text: qrContent,
    width: 110,
    height: 110,
    colorDark: "#000000",
    colorLight: "#ffffff",
    correctLevel: QRCode.CorrectLevel.H,
  });

  const downloadBtn = document.createElement("button");
  downloadBtn.textContent = "Download QR";
  downloadBtn.className = "download-btn";
  downloadBtn.onclick = function () {
    downloadQRCode(qrDiv);
  };
  qrContainer.appendChild(downloadBtn);

  cell6.appendChild(qrContainer);

  // Add Edit and Delete buttons
  cell7.innerHTML = `
  <button class="edit-btn" onclick="editRow(this)">Edit</button>
  <button class="delete-btn" onclick="deleteRow(this)">Delete</button>
`;
}

function downloadQRCode(qrDiv) {
  const canvas = qrDiv.querySelector("canvas");
  if (canvas) {
    const imageSrc = canvas.toDataURL("image/png");
    const a = document.createElement("a");
    a.href = imageSrc;
    a.download = "qrcode.png";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  } else {
    alert("QR code not available for download.");
  }
}

// Delete row function
function deleteRow(btn) {
  if (confirm("Are you sure you want to delete this book?")) {
    const row = btn.closest("tr");
    row.remove();
  }
}