//let approvedRequests = []; // Will store approved requests from database
//
//// Simulate fetching approved requests (replace with actual API call)
//function fetchApprovedRequests() {
//  // Dummy data - replace with actual API call
//  approvedRequests = [
//    { id: 1, studentName: 'Juan Dela Cruz', bookTitle: 'Introduction to Programming' },
//    { id: 2, studentName: 'Maria Santos', bookTitle: 'Database Management' }
//  ];
//  populateRequestDropdown();
//}
//
//function populateRequestDropdown() {
//  const select = document.getElementById('requestSelect');
//  select.innerHTML = '<option value="">Select a request...</option>';
//
//  approvedRequests.forEach(request => {
//    const option = document.createElement('option');
//    option.value = request.id;
//    option.textContent = `${request.studentName} - ${request.bookTitle}`;
//    select.appendChild(option);
//  });
//}

//function openQrScanner(inputId) {
//  currentInputId = inputId;
//  document.getElementById("qrScannerModal").style.display = "block";
//
//  const reader = document.getElementById("reader");
//  reader.innerHTML = "<div class='scan-message'>Please Scan the Book</div>";

//  setTimeout(() => {
//    const success = Math.random() > 0.3;
//    const isBorrow = inputId === 'issueBookTitle';
//    const statusId = isBorrow ? 'scanStatus' : 'returnScanStatus';
//    const timestampId = isBorrow ? 'scanTimestamp' : 'returnScanTimestamp';
//    const dateTimeId = isBorrow ? 'borrowDateTime' : 'returnDateTime';
//
//    if (success) {
//      const sampleBookId = "BOOK-" + Math.floor(Math.random() * 1000);
//      document.getElementById(currentInputId).value = sampleBookId;
//
//      // Get current date and time
//      const now = new Date();
//      document.getElementById(dateTimeId).value = now.toISOString();
//
//      // Update scan status and timestamp
//      document.getElementById(statusId).innerHTML = "Scan Successfully";
//      document.getElementById(timestampId).innerHTML = now.toLocaleString();
//
//      reader.innerHTML = "<div class='scan-message success'>Scan Successful</div>";
//      setTimeout(() => {
//        closeQrScanner();
//      }, 1500);
//    } else {
//      reader.innerHTML = "<div class='scan-message error'>Scan Failed. Please try again.</div>";
//
//      // Clear scan status and timestamp if failed
//      document.getElementById(statusId).innerHTML = "";
//      document.getElementById(timestampId).innerHTML = "";
//
//      setTimeout(() => {
//        reader.innerHTML = "<div class='scan-message'>Please scan the Book</div>";
//      }, 3000);
//    }
//  }, 3000);
//}

//function closeQrScanner() {
//  document.getElementById("qrScannerModal").style.display = "none";
//  const reader = document.getElementById("reader");
//  if (reader) {
//    reader.innerHTML = "";
//  }
//}
//
//// Call this when the page loads
//document.addEventListener('DOMContentLoaded', fetchApprovedRequests);

$(function() {
  const $modal = $('#qrScannerModal');
  const $input = $('#scanned_id');
  const $close = $('#closeScannerModal');

  let sourceType = null; // store which source opened it

  function openModal(source) {
    sourceType = source; // keep track of the origin
    $modal.show();
    $input.focus();

    // 🔹 Keep focus logic
    $(document).on('mousedown.keepFocus', function(e) {
      if (
        $modal.is(':visible') &&
        !$input.is(e.target) &&
        !$close.is(e.target) &&
        !$input.has(e.target).length
      ) {
        setTimeout(() => {
          if ($modal.is(':visible')) $input.focus();
        }, 0);
      }
    });

    $input.on('blur.keepFocus', function() {
      setTimeout(() => {
        if ($modal.is(':visible')) $input.focus();
      }, 0);
    });

    // 🔹 Debounced input handler (0.5s after last key)
    let typingTimer;
    $input.on('input.keepFocus', function() {
      clearTimeout(typingTimer);
      typingTimer = setTimeout(() => {
        const scannedValue = $(this).val().trim();
        if (scannedValue) {
          console.log("scan val:", scannedValue);
          console.log("source:", sourceType);



          // Example AJAX logic:
//          $.ajax({
//            url: "/api/process-scan",
//            method: "POST",
//            data: {
//              code: scannedValue,
//              source: sourceType
//            },
//            success: function(response) {
//              console.log("Server response:", response);
//            },
//            error: function(err) {
//              console.error("Error:", err);
//            }
//          });
//          alert("scan code: "+scannedValue + " | source: "+sourceType)

          // Reset input for next scan
          $(this).val('');
          closeModal();
        }
      }, 500);
    });
  }

  function closeModal() {
    $modal.hide();
    $(document).off('.keepFocus');
    $input.off('.keepFocus');
    sourceType = null;
  }

  $close.on('click', closeModal);

  $('#return_btn, #borrow_btn').on('click', function() {
    const source = $(this).data('source'); // e.g., "return" or "borrow"
    openModal(source);
  });
});