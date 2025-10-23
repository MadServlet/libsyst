class LoanTable {
  constructor(apiUrl, tableSelector, paginationContainer, pageSize = 10) {
    this.apiUrl = apiUrl;
    this.$table = $(tableSelector);
    this.$tbody = this.$table.find("tbody");
    this.$paginationContainer = $(paginationContainer);
    this.pageSize = pageSize;
    this.currentPage = 0;
    this.searchItem = '';

    this.init();
  }

  init() {
    this.loadPage(0);
  }

  loadPageSearch(search) {
    this.searchItem = search;
    this.loadPage(0);
  }

  loadPage(page) {

    $.getJSON(this.apiUrl, { search: this.searchItem, page: page, size: this.pageSize })
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

          row.append($("<td>").text(item.title));
          row.append($("<td>").text(item.author));
          row.append($("<td>").text(item.publisher));
          row.append($("<td>").text(item.year));
          row.append($("<td>").text(item.status));

          let actionButton;

          if (item.status === "AVAILABLE") {
            actionButton = $('<button>')
              .addClass('borrow-btn')
              .text('Borrow')
              .attr('data-copy-id', item.copyId);
          } else {
            actionButton = $('<button>')
              .addClass('borrow-btn unavailable')
              .attr('disabled', true)
              .text('Not Available')
              .attr('data-copy-id', item.copyId);
          }

          row.append($("<td>").append(actionButton));

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
    "/api/books/find/bulk/available",
    "#booksTable",
    "#paginationControls",
    10
  );

  $("#searchInput").on("keypress", (e) => {
    if (e.which === 13) { // 13 = Enter key
      e.preventDefault(); // prevent form submission if inside a form
      const search = $("#searchInput").val().trim();
      loanTable.loadPageSearch(search);
    }
  });
});