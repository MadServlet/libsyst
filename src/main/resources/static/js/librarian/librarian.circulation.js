function openQrScanner(inputId) {
  currentInputId = inputId;
  document.getElementById("qrScannerModal").style.display = "block";

  const html5QrCode = new Html5Qrcode("reader");
  html5QrCode.start(
    { facingMode: "environment" },
    {
      fps: 10,
      qrbox: { width: 250, height: 250 },
    },
    (decodedText) => {

      document.getElementById(currentInputId).value = decodedText;
      alert(`Scanned: ${decodedText}`);
      html5QrCode.stop();
      closeQrScanner();
    },
    (errorMessage) => {

      console.error(`QR Code scan error: ${errorMessage}`);
    }
  ).catch((err) => {
    console.error(`Unable to start scanning: ${err}`);
  });
}

function closeQrScanner() {
  document.getElementById("qrScannerModal").style.display = "none";
  const reader = document.getElementById("reader");
  if (reader) {
    reader.innerHTML = "";
  }
}