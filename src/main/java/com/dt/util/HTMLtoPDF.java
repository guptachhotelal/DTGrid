package com.dt.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

import com.dt.entity.TestData;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public final class HTMLtoPDF {

	private static String TEMP_DIR = System.getProperty("java.io.tmpdir");

	private static String USER_PASSWORD = "password";
	private static String OWNER_PASSWORD = "password";

	public static void main(String[] args) throws Exception {
		String html = html(DataGenerator.store(100).values());
		pdf(html, "Report" + System.nanoTime() + ".pdf", false);
	}

	private static void pdf(String html, String fileName, boolean protect) throws Exception {
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(TEMP_DIR + File.separator + fileName));
		if (protect) {
			writer.setEncryption(USER_PASSWORD.getBytes(), OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING,
					PdfWriter.ENCRYPTION_AES_256);
		}

		document.open();
		document.addAuthor("Created By Me");
		document.addCreator("Created By Me");
		document.addSubject("Test Reports");
		document.addCreationDate();
		document.addTitle("Test Reports");

		Charset cs = StandardCharsets.UTF_8;

		org.jsoup.nodes.Document doc = Jsoup.parse(html, cs.name());

		doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

		InputStream is = new ByteArrayInputStream(doc.html().getBytes());

		XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, cs);

		is.close();
		document.close();
		writer.close();
		System.out.println("Done.");
	}

	private static String html(Collection<TestData> collection) {
		String html = "<!DOCTYPE html><html><head><title>Report</title><style>"
				+ "html * {font-size: 12px;font-family: \"Arial\";}.headStyle {"
				+ "background-color: #eaedfa;padding: 1px;border-radius: 40px;}th, td {"
				+ "padding: 5px;}table {border-collapse: collapse;}"
				+ "tr.border_bottom td, th {border: 1px solid #eeeeee;}</style></head><body>"

				+ "<table style=\"width: 100%;\"><tbody><tr class=\"border_bottom\">"
				+ "<th style=\"text-align: center;\">Sr No</th><th style=\"text-align: center;\">Name</th>"
				+ "<th style=\"text-align: center;\">Date oF Birth</th>"
				+ "<th style=\"text-align: center;\">Phone</th><th style=\"text-align: center;\">Email</th>"
				+ "<th style=\"text-align: center;\">City</th><th style=\"text-align: center;\">Pincode</th>"
				+ "<th style=\"text-align: center;\">State</th><th style=\"text-align: center;\">Create Date</th>"
				+ "<th style=\"text-align: center;\">Update Date</th></tr>";
		List<TestData> data = collection.stream().collect(Collectors.toList());
		for (int i = 0; i < data.size(); i++) {
			TestData td = data.get(i);
			html = html + "<tr class=\"border_bottom\">" + "<td style=\"text-align: right;\">" + (i + 1) + "</td>"
					+ "<td style=\"text-align: left;\">" + td.getName() + "</td><td style=\"text-align: left;\">"
					+ DateUtil.longToDate(td.getDob()) + "</td><td style=\"text-align: left;\">" + td.getPhone()
					+ "</td><td style=\"text-align: left;\">" + td.getEmail() + "</td>"
					+ "<td style=\"text-align: left;\">" + td.getCity() + "</td><td style=\"text-align: right;\">"
					+ td.getPincode() + "</td><td style=\"text-align: left;\">" + td.getState() + "</td>"
					+ "<td style=\"text-align: left;\">" + DateUtil.longToDate(td.getCreateDate()) + "</td>"
					+ "<td style=\"text-align: left;\">" + DateUtil.longToDate(td.getUpdateDate()) + "</td></tr>";
		}
		html = html + "</tbody></table></body></html>";
		return html;
	}
}
