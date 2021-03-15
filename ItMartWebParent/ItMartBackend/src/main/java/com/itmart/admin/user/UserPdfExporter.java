package com.itmart.admin.user;

import com.itmart.itmartcommon.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter {

    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("List of User", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100F);
        pdfPTable.setSpacingBefore(10);
        pdfPTable.setWidths(new float[] {1.2F, 3.5F, 3.0F, 3.0F, 3.0F, 1.7F});

        writeTableHeader(pdfPTable);
        writeTableData(pdfPTable, userList);

        document.add(pdfPTable);
        document.close();
    }

    private void writeTableData(PdfPTable pdfPTable, List<User> userList) {
        for (User user : userList) {
            pdfPTable.addCell(String.valueOf(user.getId()));
            pdfPTable.addCell(user.getEmail());
            pdfPTable.addCell(user.getFirstName());
            pdfPTable.addCell(user.getLastName());
            pdfPTable.addCell(user.getRoles().toString());
            pdfPTable.addCell(String.valueOf(user.isEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable pdfPTable) {
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setBackgroundColor(Color.BLUE);
        pdfPCell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        pdfPCell.setPhrase(new Phrase("ID", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("E-mail", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("First Name", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Last Name", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Roles", font));
        pdfPTable.addCell(pdfPCell);

        pdfPCell.setPhrase(new Phrase("Enabled", font));
        pdfPTable.addCell(pdfPCell);
    }
}
