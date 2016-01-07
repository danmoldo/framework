package today.learnjava.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Created by Development on 10/12/2015.
 */
public class EmailTemplateUtil {

    public static String fillTemplate(String subject, String message, String unsubscribe) {
        String template = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<!-- If you delete this tag, the sky will fall on your head -->\n" +
                "<meta name=\"viewport\" content=\"width=device-width\" />\n" +
                "\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "\n" +
                "</head>\n" +
                " \n" +
                "<body bgcolor=\"#FFFFFF\">\n" +
                "<div style='font-family: \"Helvetica Neue\", \"Helvetica\", Helvetica, Arial, sans-serif; '>\n" +
                "\n" +
                "<!-- BODY -->\n" +
                "<table class=\"body-wrap\" style=\"width: 100%;\">\n" +
                "\t<tr>\n" +
                "\t\t<td></td>\n" +
                "\t\t<td class=\"container\" style=\"display:block!important;\n" +
                "\tmax-width:600px!important;\n" +
                "\tmargin:0 auto!important; \n" +
                "\tclear:both!important;\" bgcolor=\"#FFFFFF\">\n" +
                "\n" +
                "\t\t\t<div class=\"content\" style=\"padding:15px;\n" +
                "\tmax-width:600px;\n" +
                "\tmargin:0 auto;\n" +
                "\tdisplay:block; \">\n" +
                "\t\t\t<table style=\"width: 100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t<p style=\"margin-bottom: 10px; \n" +
                "\tfont-weight: normal; \n" +
                "\tfont-size:14px; \n" +
                "\tline-height:1.6;\"><img src=\"https://s3-eu-west-1.amazonaws.com/guidebubble/resources/bannerLogo.png\" style=\"max-width: 100%;\"/></p>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<h3><%subject%></h3>\n" +
                "\t\t\t\t\t\t<p style=\"margin-bottom: 10px; \n" +
                "\tfont-weight: normal; \n" +
                "\tfont-size:14px; \n" +
                "\tline-height:1.6;\"><%body%></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<br/>\n" +
                "\t\t\t\t\t\t<br/>\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t<!-- social & contact -->\n" +
                "\t\t\t\t\t\t<table align=\"center\" class=\"social\" width=\"75%\" style=\"background-color: #ebebeb;\">\n" +
                "\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<!--- column 1 -->\n" +
                "\t\t\t\t\t\t\t\t\t<table align=\"left\" class=\"column\" style=\"width: 100%;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 15px;\">\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h5 class=\"\">Connect with us:</h5>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"\" style=\"margin-bottom: 10px; \n" +
                "\tfont-weight: normal; \n" +
                "\tfont-size:14px; \n" +
                "\tline-height:1.6;\"><a href=\"https://www.facebook.com/GuideBubble\" class=\"soc-btn fb\" style=\"padding: 3px 7px;\n" +
                "\tfont-size:12px;\n" +
                "\tmargin-bottom:10px;\n" +
                "\ttext-decoration:none;\n" +
                "\tcolor: #FFF;font-weight:bold;\n" +
                "\tdisplay:block;\n" +
                "\ttext-align:center;\n" +
                "\tbackground-color: #3B5998!important;\">Facebook</a></p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</table><!-- /column 1 -->\t\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<!--- column 2 -->\n" +
                "\t\t\t\t\t\t\t\t\t<table align=\"left\" class=\"column\" style=\"width: 100%\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 15px;\">\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h5 class=\"\">Send us a message:</h5>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"\" style=\"margin-bottom: 10px; \n" +
                "\tfont-weight: normal; \n" +
                "\tfont-size:14px; \n" +
                "\tline-height:1.6;\"><a href=\"http://www.guidebubble.com/contact\" class=\"soc-btn gp\" style=\"padding: 3px 7px;\n" +
                "\tfont-size:12px;\n" +
                "\tmargin-bottom:10px;\n" +
                "\ttext-decoration:none;\n" +
                "\tcolor: #FFF;font-weight:bold;\n" +
                "\tdisplay:block;\n" +
                "\ttext-align:center;\n" +
                "\tbackground-color: #DB4A39!important;\">Contact page</a></p>\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</table><!-- /column 2 -->\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t<span class=\"clear\" style=\"display: block; clear: both;\"></span>\t\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t</table><!-- /social & contact -->\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "\t\t</td>\n" +
                "\t\t<td></td>\n" +
                "\t</tr>\n" +
                "</table><!-- /BODY -->\n" +
                "\n" +
                "<!-- FOOTER -->\n" +
                "<table class=\"footer-wrap\" style=\"width: 100%;\tclear:both!important;\">\n" +
                "\t<tr>\n" +
                "\t\t<td></td>\n" +
                "\t\t<td class=\"container\" style=\"display:block!important;\n" +
                "\tmax-width:600px!important;\n" +
                "\tmargin:0 auto!important; \n" +
                "\tclear:both!important;\">\n" +
                "\t\t\t\n" +
                "\t\t\t\t<!-- content -->\n" +
                "\t\t\t\t<div class=\"content\" style=\"padding:15px;\n" +
                "\tmax-width:600px;\n" +
                "\tmargin:0 auto;\n" +
                "\tdisplay:block; \">\n" +
                "\t\t\t\t<table style=\"width: 100%;\">\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t<p style=\"border-top: 1px solid rgb(215,215,215); padding-top:15px; \">\n" +
                "\t\t\t\t\t\t\t<a href=\"http://www.guidebubble.com/termsAndConditions\">Terms and conditions</a>" +
                " <%unsubscribe%>\n" +
                "\t\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t\t\t</div><!-- /content -->\n" +
                "\t\t\t\t\n" +
                "\t\t</td>\n" +
                "\t\t<td></td>\n" +
                "\t</tr>\n" +
                "</table><!-- /FOOTER -->\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        return template.replace("<%subject%>", subject).replace("<%body%>", message).replace("<%unsubscribe%>", unsubscribe);
    }

}
