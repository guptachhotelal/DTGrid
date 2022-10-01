package com.dt.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HTMLtoPDF {

	private static String USER_PASSWORD = "password";
	private static String OWNER_PASSWORD = "password";

	public static void main(String[] args) throws Exception {
		pdf(html(), "Report" + System.currentTimeMillis() + ".pdf", false);
	}

	private static void pdf(String html, String fileName, boolean protect) throws Exception {
		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(System.getProperty("java.io.tmpdir") + File.separator + fileName));
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

	private static String html() {
		String html = "<!DOCTYPE html><html><head><title>Customer Report</title><style>"
				+ "html * {font-size: 12px;font-family: \"Arial\";}.headStyle {"
				+ "background-color: #eaedfa;padding: 1px;border-radius: 40px;}th, td {"
				+ "padding: 5px;}table {border-collapse: collapse;}"
				+ "tr.border_bottom td, th {border-bottom: 1px solid #eeeeee;}</style></head>"
				+ "<body><table style=\"width: 100%;height: 75px;\"><tbody><tr>" + "<td><img src=\"\""
				+ "style=\"height: 50px; width: 140px;\" /></td>"
				+ "<td style=\"text-align: right;\"><h1>All Customers Report</h1></td></tr></tbody></table>"
				+ "<div class=\"headStyle\"><b>&nbsp;Basic Details</b></div>"
				+ "<div style=\"padding-top: 10px;\"></div><table style=\"width: 60%;\"><tbody>"
				+ "<tr><td>Khata download date</td><td>20-DEC-2019</td></tr><tr><td>Filtered by</td>"
				+ "<td>&#8377;1,000 - &#8377;3,500 (Receivable)</td></tr></tbody>"
				+ "</table><div style=\"padding-top: 10px;\"></div><div class=\"headStyle\">"
				+ "<b>&nbsp;Customer-Wise Report</b></div><table style=\"width: 100%;\"><tbody>"
				+ "<tr class=\"border_bottom\"><th style=\"text-align: left;\">Name</th>"
				+ "<th style=\"text-align: right;\">Mobile No.</th>"
				+ "<th style=\"text-align: right;\">Settlement Date</th>"
				+ "<th style=\"text-align: right;\">Reminder Date</th>"
				+ "<th style=\"text-align: right;\">Receivable(&#8377;)</th>"
				+ "<th style=\"text-align: right;\">Advance(&#8377;)</th>"
				+ "<th style=\"text-align: right;\">Khata Limit</th></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Anil Yadav</td><td style=\"text-align: right;\">9876543210</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1500.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">5000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Avinash Kundra</td><td style=\"text-align: right;\">9998979695</td>"
				+ "<td style=\"text-align: right;\">15-DEC-2019</td><td style=\"text-align: right;\">28-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">1200.00</td><td style=\"text-align: right;\">2000.00</td>"
				+ "<td style=\"text-align: right;\">3000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Achalesh Khare</td><td style=\"text-align: right;\">8786858483</td>"
				+ "<td style=\"text-align: right;\">01-JAN-2020</td><td style=\"text-align: right;\">20-JAN-2020</td>"
				+ "<td style=\"text-align: right;\">1000.00</td><td style=\"text-align: right;\">--</td>"
				+ "<td style=\"text-align: right;\">2000.00</td></tr>"
				+ "<tr class=\"border_bottom\"><td>Bhavesh Singh</td><td style=\"text-align: right;\">7675747372</td>"
				+ "<td style=\"text-align: right;\">10-DEC-2019</td><td style=\"text-align: right;\">20-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">500.00</td><td style=\"text-align: right;\">700.00</td>"
				+ "<td style=\"text-align: right;\">1000.00</td></tr><tr class=\"border_bottom\"><td>Abc Yadav</td>"
				+ "<td style=\"text-align: right;\">7879654646</td><td style=\"text-align: right;\">10-DEC-2019</td>"
				+ "<td style=\"text-align: right;\">20-DEC-2019</td><td style=\"text-align: right;\">1500.00</td>"
				+ "<td style=\"text-align: right;\">--</td><td style=\"text-align: right;\">3500.00</td></tr></tbody>"
				+ "</table></body></html>";
		return html;
	}
}
