package com.proj.itstaym.libsyst;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;

public class EmailSendingTest {

    public static void main(String[] args) {
        Email email = new Email();

        email.setFrom("Libsyst", "librarian@test-r6ke4n17rymgon12.mlsender.net");
        email.addRecipient("Test User", "tarayme761@gmail.com");

        email.setSubject("Libsyst Test Email");

        email.setHtml("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Borrow/Return History</title>\n" +
                "    <style>body {\n" +
                "  font-family: Arial, sans-serif;\n" +
                "  background: #f7f7f7;\n" +
                "  margin: 0;\n" +
                "  padding-bottom: 50px;\n" +
                "}\n" +
                "\n" +
                "header {\n" +
                "  background: #4CAF50;\n" +
                "  color: #fff;\n" +
                "  padding: 5px 20px;\n" +
                "  display: flex;\n" +
                "  align-items: center;\n" +
                "  justify-content: space-between;\n" +
                "  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;\n" +
                "}\n" +
                "\n" +
                ".title-logo {\n" +
                "  display: flex;\n" +
                "  align-items: center;\n" +
                "}\n" +
                "\n" +
                ".title-logo .logo {\n" +
                "  margin-right: 15px;\n" +
                "  width: 80px;\n" +
                "  height: auto;\n" +
                "}\n" +
                "\n" +
                ".title-logo h1 {\n" +
                "  margin: 0;\n" +
                "}\n" +
                "\n" +
                "nav {\n" +
                "  display: flex;\n" +
                "  gap: 10px;\n" +
                "}\n" +
                "\n" +
                "nav a {\n" +
                "  color: #fff;\n" +
                "  text-decoration: none;\n" +
                "  border-radius: 5px;\n" +
                "  padding: 10px 15px;\n" +
                "\n" +
                "}\n" +
                "\n" +
                "nav a:hover {\n" +
                "  background: #45a049;\n" +
                "  box-shadow: rgba(0, 0, 0, 0.05) 0px 6px 24px 0px, rgba(0, 0, 0, 0.08) 0px 0px 0px 1px, rgba(149, 157, 165, 0.2) 0px 8px 24px;\n" +
                "}\n" +
                "\n" +
                ".container {\n" +
                "  padding: 20px;\n" +
                "}\n" +
                "\n" +
                "#logoutBtn {\n" +
                "  background-color: #ffffff;\n" +
                "  background-position: 0 0;\n" +
                "  border: 1px solid #ff4d4d;\n" +
                "  color: #ff4d4d;\n" +
                "  font-size: 20px;\n" +
                "  cursor: pointer;\n" +
                "  padding: 10px;\n" +
                "  border-radius: 20px;\n" +
                "}\n" +
                "\n" +
                "#logoutBtn:hover {\n" +
                "  background-color: #ff4d4d;\n" +
                "  color: #ffffff;\n" +
                "  border-radius: 20px;\n" +
                "  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;\n" +
                "}\n" +
                "\n" +
                "/* Page title */\n" +
                "h2 {\n" +
                "  text-align: center;\n" +
                "  margin: 20px 0;\n" +
                "  color: #333;\n" +
                "}\n" +
                "\n" +
                "/* Report buttons */\n" +
                ".report-buttons button {\n" +
                "  padding: 10px 15px;\n" +
                "  margin: 5px;\n" +
                "  background-color: #4CAF50;\n" +
                "  color: white;\n" +
                "  border: none;\n" +
                "  border-radius: 5px;\n" +
                "  cursor: pointer;\n" +
                "  font-size: 14px;\n" +
                "}\n" +
                "\n" +
                ".report-buttons button:hover {\n" +
                "  background-color: #45a049;\n" +
                "}\n" +
                "\n" +
                "/* History table styles */\n" +
                ".history-table-container {\n" +
                "  margin: 20px auto;\n" +
                "  max-width: 90%;\n" +
                "}\n" +
                "\n" +
                ".history-table {\n" +
                "  width: 100%;\n" +
                "  border-collapse: collapse;\n" +
                "  background: white;\n" +
                "  border-radius: 8px;\n" +
                "  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);\n" +
                "}\n" +
                "\n" +
                ".history-table th,\n" +
                ".history-table td {\n" +
                "  border: 1px solid #ddd;\n" +
                "  padding: 10px;\n" +
                "  text-align: left;\n" +
                "}\n" +
                "\n" +
                ".history-table th {\n" +
                "  background-color: #4CAF50;\n" +
                "  color: white;\n" +
                "}\n" +
                "\n" +
                ".history-table tr:nth-child(even) {\n" +
                "  background-color: #f2f2f2;\n" +
                "}\n" +
                "\n" +
                ".history-table tr:hover {\n" +
                "  background-color: #ddd;\n" +
                "}\n" +
                "\n" +
                "/* Footer styles */\n" +
                "footer {\n" +
                "  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;\n" +
                "  text-align: center;\n" +
                "  padding: 0px;\n" +
                "  background: #4CAF50;\n" +
                "  position: fixed;\n" +
                "  right: 0;\n" +
                "  bottom: 0;\n" +
                "  width: 20%;\n" +
                "  border-radius: 10px;\n" +
                "  margin-right: 20px;\n" +
                "  margin-bottom: 10px;\n" +
                "  font-weight: bold;\n" +
                "}</style>" +
                "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\">\n" +
                "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js\"></script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <div class=\"title-logo\">\n" +
                "            <img src=\"https://www.paterostechnologicalcollege.edu.ph/ASSETS/IMAGES/LOGO/logo-ptc.png\" alt=\"PTC Logo\"\n" +
                "                class=\"logo\">\n" +
                "            <h1>Admin</h1>\n" +
                "        </div>\n" +
                "        <nav>\n" +
                "            <a href=\"admin_main.html\">Create User Account</a>\n" +
                "            <a href=\"user_management.html\">Manage Users</a>\n" +
                "            <a href=\"borrow_return history.html\">Borrow/Return History</a>\n" +
                "        </nav>\n" +
                "        <div>\n" +
                "            <button id=\"logoutBtn\" onclick=\"logout()\">\n" +
                "                <i class=\"fas fa-sign-out-alt\"></i>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "    </header>\n" +
                "\n" +
                "    <h2 style=\"text-align:center; margin-top:20px;\">User Borrow/Return History</h2>\n" +
                "\n" +
                "    <!-- Month and Year Display -->\n" +
                "    <div id=\"monthYearDisplay\" style=\"text-align:center; font-size:1.2em; font-weight:bold; margin-bottom:10px;\"></div>\n" +
                "\n" +
                "    <!-- Report Generation Buttons -->\n" +
                "    <div class=\"report-buttons\" style=\"text-align:center; margin:20px;\">\n" +
                "        <button onclick=\"generateExcel()\">Generate Excel Report</button>\n" +
                "        <button onclick=\"generatePDF()\">Generate PDF Report</button>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- History Table -->\n" +
                "    <div class=\"history-table-container\">\n" +
                "        <!-- Month and Year Display above each table -->\n" +
                "        <div class=\"table-month-year\" style=\"text-align:center; font-size:1.1em; font-weight:bold; margin-bottom:5px;\"\n" +
                "            id=\"tableMonthYear\"></div>\n" +
                "        <table class=\"history-table\" id=\"historyTable\">\n" +
                "            <thead>\n" +
                "                <tr>\n" +
                "                    <th>User Name</th>\n" +
                "                    <th>Email</th>\n" +
                "                    <th>Book Title</th>\n" +
                "                    <th>Author</th>\n" +
                "                    <th>Date & Time BORROWED</th>\n" +
                "                    <th>Date & Time RETURNED</th>\n" +
                "                </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "                <!-- Example entries -->\n" +
                "                <tr>\n" +
                "                    <td>Juan Dela Cruz</td>\n" +
                "                    <td>example@paterostechnologicalcollege.edu.ph</td>\n" +
                "                    <td>Noli Me Tangere</td>\n" +
                "                    <td>Jose Rizal</td>\n" +
                "                    <td>2025-07-01 2:30PM</td>\n" +
                "                    <td>2025-07-01 5:00PM</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                    <td>Maria Clara</td>\n" +
                "                    <td>example@paterostechnologicalcollege.edu.ph</td>\n" +
                "                    <td>El Filibusterismo</td>\n" +
                "                    <td>Jose Rizal</td>\n" +
                "                    <td>2025-07-05 10:15AM</td>\n" +
                "                    <td>2025-07-05 1:00PM</td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "\n" +
                "    <footer>\n" +
                "        <p id=\"currentTime\"></p>\n" +
                "    </footer>\n" +
                "\n" +
                "    <script src=\"borrow_return_history.js\"></script>\n" +
                "</body>\n" +
                "\n" +
                "</html>");

        MailerSend ms = new MailerSend();

        ms.setToken("mlsn.0d3deea6c76ecc0673161c85ad1d502c6168daf7fc33629d52531a0eb26d22b7");

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

}
