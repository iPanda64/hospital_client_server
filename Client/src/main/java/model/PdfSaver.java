package model;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.io.IOException;

public class PdfSaver {

    public static void saveTextToDesktop(String textContent, String fileName) {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop" + File.separator + fileName;

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);

                if (textContent != null) {
                    for (String line : textContent.split("\n")) {
                        contentStream.showText(line);
                        contentStream.newLineAtOffset(0, -15);
                    }
                }
                contentStream.endText();
            }

            document.save(desktopPath);
            System.out.println("Saved successfully to: " + desktopPath);

        } catch (IOException e) {
            System.err.println("Error creating PDF: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        saveTextToDesktop("This is a test PDF.\nSaved directly to your Desktop.", "test_file.pdf");
    }
}
