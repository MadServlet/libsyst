var user_role = 'NONE';
function generateExcel() {

  $.getJSON("/api/lending/report/history/bulk")
    .done((response) => {
      const simplified = response.map(item => ({
        "User Name": item.user.fullName,
        "Email": item.user.email,
        "Book Title": item.book.title,
        "Author": item.book.author,
        "Borrowed Date": item.loanDate ? formatDate(item.loanDate) : "",
        "Returned Date": item.returnDate ? formatDate(item.returnDate) : "—"
      }));

      const worksheet = XLSX.utils.json_to_sheet(simplified);

      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "History");

      XLSX.writeFile(workbook, "Borrow_Return_History.csv", { bookType: "csv" });
    })
    .fail(() => {
      alert("Generation failed")
    });
}

// Function to generate PDF report
function generatePDF() {

  $.getJSON("/api/lending/report/history/bulk")
    .done((response) => {

      const { jsPDF } = window.jspdf;
      const doc = new jsPDF({
        orientation: 'landscape'
      });

      const headers = ["User Name", "Email", "Book Title", "Author", "Borrowed Date", "Returned Date"];
      doc.setFontSize(14);
      doc.text("Borrow History", 14, 15);

      const rows = response.map(r => [
        r.user.fullName,
        r.user.email,
        r.book.title,
        r.book.author,
        r.loanDate ? formatDate(r.loanDate) : "",
        r.returnDate ? formatDate(r.returnDate) : "—"
      ]);

      doc.autoTable({
        head: [headers],
        body: rows,
        startY: 25,
        styles: { fontSize: 10, cellPadding: 3 }
      });

      doc.save("borrow_history.pdf");
    })
    .fail(() => {
      alert("Generation failed")
    });
}

function formatDate(dateStr) {
  if (!dateStr) return "";
  const d = new Date(dateStr);
  const year = d.getFullYear();
  const month = String(d.getMonth() + 1).padStart(2, "0");
  const day = String(d.getDate()).padStart(2, "0");
  let hours = d.getHours();
  const minutes = String(d.getMinutes()).padStart(2, "0");
  const ampm = hours >= 12 ? "PM" : "AM";
  hours = hours % 12 || 12;
  return `${year}-${month}-${day} ${hours}:${minutes}${ampm}`;
}

class LoanTable {
  constructor(apiUrl, tableSelector, paginationContainer, pageSize = 10) {
    this.apiUrl = apiUrl;
    this.$table = $(tableSelector);
    this.$tbody = this.$table.find("tbody");
    this.$paginationContainer = $(paginationContainer);
    this.pageSize = pageSize;
    this.currentPage = 0;

    this.init();
  }

  init() {
    this.loadPage(0);
  }

  loadPage(page) {

    $.getJSON(this.apiUrl, { page: page, size: this.pageSize })
        .done((response) => {
          this.renderTable(response.content);
          this.renderPagination(response.page);
        })
        .fail(() => {
          this.$tbody.html(`<tr><td colspan="6">Error loading data</td></tr>`);
        });
  }

  renderTable(data) {
    this.$tbody.empty();
    if (!data.length) {
      this.$tbody.append(`<tr><td colspan="6">No records found</td></tr>`);
      return;
    }

    data.forEach(item => {
      const row = $("<tr>");

      if (user_role === "ADMIN" || user_role === "LIBRARIAN") {
        row.append($("<td>").text(item.user.fullName));
        row.append($("<td>").text(item.user.email));
      }

      row.append($("<td>").text(item.book.title));
      row.append($("<td>").text(item.book.author));
      row.append($("<td>").text(formatDate(item.loanDate)));
      row.append($("<td>").text(item.returnDate ? formatDate(item.returnDate) : "—"));
      this.$tbody.append(row);
    });
  }

  renderPagination(page) {
    const totalPages = page.totalPages;
    const currentPage = page.number;
    this.currentPage = currentPage;

    const $pagination = $('<ul class="pagination"></ul>');

    // Previous button
    $pagination.append(`
      <li class="page-item ${currentPage === 0 ? "disabled" : ""}">
        <a class="page-link" href="#" data-page="${currentPage - 1}">Previous</a>
      </li>
    `);

    const maxVisible = 5;
    let startPage = Math.max(0, currentPage - 2);
    let endPage = Math.min(totalPages - 1, startPage + maxVisible - 1);

    // Adjust range near the end
    if (endPage - startPage < maxVisible - 1) {
      startPage = Math.max(0, endPage - maxVisible + 1);
    }

    // Show first page + ellipsis if needed
    if (startPage > 0) {
      $pagination.append(`
        <li class="page-item"><a class="page-link" href="#" data-page="0">1</a></li>
      `);
      if (startPage > 1) {
        $pagination.append(`
          <li class="page-item disabled"><a class="page-link" href="#">…</a></li>
        `);
      }
    }

    // Numbered buttons
    for (let i = startPage; i <= endPage; i++) {
      $pagination.append(`
        <li class="page-item ${i === currentPage ? "active" : ""}">
          <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
        </li>
      `);
    }

    // Show ellipsis + last page if needed
    if (endPage < totalPages - 1) {
      if (endPage < totalPages - 2) {
        $pagination.append(`
          <li class="page-item disabled"><a class="page-link" href="#">…</a></li>
        `);
      }
      $pagination.append(`
        <li class="page-item"><a class="page-link" href="#" data-page="${totalPages - 1}">${totalPages}</a></li>
      `);
    }

    // Next button
    $pagination.append(`
      <li class="page-item ${currentPage >= totalPages - 1 ? "disabled" : ""}">
        <a class="page-link" href="#" data-page="${currentPage + 1}">Next</a>
      </li>
    `);

    // Replace container contents
    this.$paginationContainer.html($pagination);

    // Bind click events
    this.$paginationContainer.find(".page-link").click((e) => {
      e.preventDefault();
      const page = parseInt($(e.target).data("page"));
      if (!isNaN(page) && page >= 0 && page < totalPages) {
        this.loadPage(page);
      }
    });
  }
}

$(document).ready(function () {
  const loanTable = new LoanTable(
    "/api/lending/report/history",
    "#historyTable",
    "#paginationControls",
    10
  );

  user_role = $("#userType").data('role');
});
