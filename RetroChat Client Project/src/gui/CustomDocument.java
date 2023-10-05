package gui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CustomDocument extends JTextPane{
	private SimpleAttributeSet leftAlign = new SimpleAttributeSet();
    private SimpleAttributeSet rightAlign = new SimpleAttributeSet();
   	private DefaultStyledDocument doc;
   	private String text;
	
	public CustomDocument () {
		super();
		StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);
		StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
		setContentType("text/html");
		
		restartDocument();
	}

	public void restartDocument() {
		text = "";
		updateText();
	}
	
/*	public void restartDocument() {
		this.doc = new DefaultStyledDocument();
		setDocument(doc);
	}
	
	try {
			SimpleAttributeSet align = you ? leftAlign:rightAlign;
			doc.insertString(doc.getLength(), text, rightAlign);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	*/

	public void append(String subject, String text, String time, boolean you, boolean whitLoad) {
		String content;
		if(you){
			content = "<div class=\"right\">\n"
					+ "    <p align='right'><b>"+subject+"</b> <i>"+time+"</i></p>\n"
					+ "   <p align='right'>"+text+"</p>\n"
					+ "</div>";
		} 
		
		else{
			content = "<div class=\"left\">\n"
					+ "    <p ><b>"+subject+"</b> <i>"+time+"</i></p>\n"
					+ "   <p>"+text+"</p>\n"
					+ "</div>";
			} 
		this.text += content;
		
		if (whitLoad) 
			updateText();
	}
	
	public void updateText() {
		String start = "<html>\n"
				+ "<head>\n"
				+ "    <style>\n"
				+ "        /* Estilos CSS aquí (se mantienen iguales) */\n"
				+ "        div {\n"
				+ "            display: flex;\n"
				+ "            flex-direction: column;\n"
				+ "        }\n"
				+ "\n"
				+ "        .left {\n"
				+ "            background-color: rgb(60, 60, 60);\n"
				+ "            padding: 10px;\n"
				+ "            color: white;\n"
				+ "            width: fit-content;\n"
				+ "        }p .left{text-align:left;} \n"
				+ "\n"
				+ "        .right {\n"
				+ "            background-color: rgb(60, 60, 130);\n"
				+ "            padding: 10px;\n"
				+ "            margin:auto;\n"
				+ "            color: white;\n"
				+ "            width: fit-content;\n"
				+ "        }p .right{text-align:right;}\n"
				+ "\n"
				+ "        i {\n"
				+ "            opacity: 0.5;\n"
				+ "        }\n"
				+ "    </style>\n"
				+ "</head>\n"
				+ "<body>",
				
		end = "</body></html>";
		setText(start + this.text + end);
		StyledDocument styleDoc = getStyledDocument();
		setCaretPosition(styleDoc.getLength());
	}
	
}
