package br.com.itau.rh.ocr;

import org.bytedeco.javacpp.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.tesseract.*;
import static org.bytedeco.leptonica.global.lept.*;
import static org.bytedeco.tesseract.global.tesseract.*;

public class BasicExample {
    public String convert(String pathImage) {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(".", "por", 1) != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

	api.SetPageSegMode(3);
//	api.InitLangMod(".", "por");

        // Open input image with leptonica library
        PIX image = pixRead(pathImage);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
	String retorno = outText.getString();
        System.out.println("OCR output:\n" + outText.getString());
        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);

	return retorno;
    }
}
