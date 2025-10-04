package com.dt.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import org.jsoup.Jsoup;

import com.dt.model.TestData;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public final class HTMLtoPDF {

	private static String TEMP_DIR = System.getProperty("java.io.tmpdir");

	private static String USER_PASSWORD = "password";
	private static String OWNER_PASSWORD = "password";

	public static String pdf(String html, String fileName, boolean protect) throws Exception {
		Document document = new Document(PageSize.A4);
		String filePath = TEMP_DIR + File.separator + fileName;
		FileOutputStream fos = new FileOutputStream(filePath);
		PdfWriter writer = PdfWriter.getInstance(document, fos);
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
		return filePath;
	}

	public static String html(Collection<TestData> collection) {
		StringBuilder temp = new StringBuilder();
		temp.append("<!DOCTYPE html><html><head><title>Report</title><style>)")
				.append("html * {font-size: 12px;font-family: \"Arial\";}.headStyle {")
				.append("background-color: #eaedfa;padding: 1px;border-radius: 40px;}th, td {")
				.append("padding: 5px;}table {border-collapse: collapse;}")
				.append("tr.border_bottom td, th {border: 1px solid #eeeeee;}</style></head><body>")
				.append("<table style=\"width: 100%;\"><tbody><tr class=\"border_bottom\">")
				.append("<th style=\"text-align: center;\">Sr No</th><th style=\"text-align: center;\">Name</th>")
				.append("<th style=\"text-align: center;\">Date oF Birth</th>")
				.append("<th style=\"text-align: center;\">Phone</th><th style=\"text-align: center;\">Email</th>")
				.append("<th style=\"text-align: center;\">City</th><th style=\"text-align: center;\">Pincode</th>")
				.append("<th style=\"text-align: center;\">State</th><th style=\"text-align: center;\">Create Date</th>")
				.append("<th style=\"text-align: center;\">Update Date</th></tr>");

		List<TestData> data = collection.stream().toList();
		for (int i = 0; i < data.size(); i++) {
			TestData td = data.get(i);
			temp.append("<tr class=\"border_bottom\">")
					.append("<td style=\"text-align: right;\">").append(i + 1).append("</td>")
					.append("<td style=\"text-align: left;\">").append(td.getName()).append("</td>")
					.append("<td style=\"text-align: left;\">").append(DateUtil.longToDate(td.getDob())).append("</td>")
					.append("<td style=\"text-align: left;\">").append(td.getPhone()).append("</td>")
					.append("<td style=\"text-align: left;\">").append(td.getEmail()).append("</td>")
					.append("<td style=\"text-align: left;\">").append(td.getCity()).append("</td>")
					.append("<td style=\"text-align: right;\">").append(td.getPincode()).append("</td>")
					.append("<td style=\"text-align: left;\">").append(td.getState()).append("</td>")
					.append("<td style=\"text-align: left;\">").append(DateUtil.longToDate(td.getCreateDate())).append("</td>")
					.append("<td style=\"text-align: left;\">").append(DateUtil.longToDate(td.getUpdateDate())).append("</td>")
			.append("</tr>");
		}
		temp.append("</tbody></table></body></html>");
		String html = String.valueOf(temp);
		temp.setLength(0);
		return html;
	}

	private HTMLtoPDF() {
		throw new UnsupportedOperationException("Cannot instantiate  " + getClass().getName());
	}
}
