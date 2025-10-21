// Function to generate Excel report
function generateExcel() {
    const table = document.getElementById("historyTable");
    const workbook = XLSX.utils.table_to_book(table, { sheet: "Borrow/Return History" });
    XLSX.writeFile(workbook, "Borrow_Return_History.xlsx");
}

// Function to generate PDF report
function generatePDF() {
    const table = document.getElementById("historyTable");
    const rows = Array.from(table.rows).map(row => Array.from(row.cells).map(cell => cell.textContent));
    const doc = new jsPDF();

    doc.text("Borrow/Return History", 10, 10);
    doc.autoTable({
        head: [rows[0]],
        body: rows.slice(1),
    });

    doc.save("Borrow_Return_History.pdf");
}