package com.example.PanAfricanMail.security;

import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;

public class RtfToHtml {
    public static String convert(String rtf) throws Exception {
        RTFEditorKit rtfParser = new RTFEditorKit();
        Document doc = rtfParser.createDefaultDocument();
        rtfParser.read(new ByteArrayInputStream(rtf.getBytes()), doc, 0);

        StringWriter writer = new StringWriter();
        new HTMLEditorKit().write(writer, doc, 0, doc.getLength());
        return writer.toString();
    }
}

