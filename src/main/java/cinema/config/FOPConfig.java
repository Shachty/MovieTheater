package cinema.config;

/**
 * Created by Daniel on 26.05.2015.
 */
import org.apache.camel.Exchange;

public class FOPConfig {

    public static final String EXT_DELIM = ".";
    private static final String fopMainTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">\n" +
            "\n" +
            "<fo:layout-master-set>\n" +
            "  <fo:simple-page-master master-name=\"A4\">\n" +
            "    <fo:region-body margin=\"25pt\"/>\n" +
            "  </fo:simple-page-master>\n" +
            "</fo:layout-master-set>\n" +
            "\n" +
            "<fo:page-sequence master-reference=\"A4\">\n" +
            "  <fo:flow flow-name=\"xsl-region-body\">\n" +
            "#BLOCK_CONTENT" +
            "  </fo:flow>\n" +
            "</fo:page-sequence>\n" +
            "\n" +
            "</fo:root>";
    private static final String fopBlockTemplate = "    <fo:block font-family=\"Courier\" font-weight=\"normal\" " +
            "font-style=\"normal\" score-spaces=\"true\" white-space=\"pre\" linefeed-treatment=\"preserve\" " +
            "white-space-collapse=\"false\" white-space-treatment=\"preserve\" font-size=\"10pt\">#CONTENT</fo:block>\n";

    public static String getFileNameWithoutExtension(Exchange exchange){
        String fileName = (String) exchange.getIn().getHeader(Exchange.FILE_NAME);
        return fileName.substring(0, fileName.indexOf(EXT_DELIM));
    }

    public static String getFilledXSLFO(String content){
        return fopMainTemplate.replaceAll("#BLOCK_CONTENT", getXSLFOBlock(content));
    }

    private static String getXSLFOBlock(String line){
        return fopBlockTemplate.replaceAll("#CONTENT", line);
    }
}