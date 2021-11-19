package Main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class CatalogParser {
    public static ArrayList<String> ParseCatalog() throws IOException{

        // Get all the catalogs for CCI
        String ITIS_Undergrad_Url = "https://catalog.uncc.edu/content.php?filter%5B27%5D=ITIS&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=1&cur_cat_oid=29&expand=&navoid=2952&search_database=Filter#acalog_template_course_filter";
        String ITCS_Undergrad_Url = "https://catalog.uncc.edu/content.php?filter%5B27%5D=ITCS&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=1&cur_cat_oid=29&expand=&navoid=2952&search_database=Filter#acalog_template_course_filter";
        String ITSC_Undergrad_Url = "https://catalog.uncc.edu/content.php?filter%5B27%5D=ITSC&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=1&cur_cat_oid=29&expand=&navoid=2952&search_database=Filter#acalog_template_course_filter";
        String ITIS_Grad_Url = "https://catalog.uncc.edu/content.php?filter%5B27%5D=ITIS&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=1&cur_cat_oid=30&expand=&navoid=3124&search_database=Filter#acalog_template_course_filter";
        String ITCS_Grad_Url = "https://catalog.uncc.edu/content.php?filter%5B27%5D=ITCS&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=1&cur_cat_oid=30&expand=&navoid=3124&search_database=Filter#acalog_template_course_filter";
        String ITCS_Grad2_Url = "https://catalog.uncc.edu/content.php?catoid=30&navoid=3124&filter%5B27%5D=ITCS&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=2&filter%5Bitem_type%5D=3&filter%5Bonly_active%5D=1&filter%5B3%5D=1#acalog_template_course_filter";
        String ITSC_Grad_Url = "https://catalog.uncc.edu/content.php?filter%5B27%5D=ITSC&filter%5B29%5D=&filter%5Bcourse_type%5D=-1&filter%5Bkeyword%5D=&filter%5B32%5D=1&filter%5Bcpage%5D=1&cur_cat_oid=30&expand=&navoid=3124&search_database=Filter#acalog_template_course_filter";
        Document ITIS_Undergrad_Page = Jsoup.connect(ITIS_Undergrad_Url).get();
        Document ITCS_Undergrad_Page = Jsoup.connect(ITCS_Undergrad_Url).get();
        Document ITSC_Undergrad_Page = Jsoup.connect(ITSC_Undergrad_Url).get();
        Document ITIS_Grad_Url_Page = Jsoup.connect(ITIS_Grad_Url).get();
        Document ITCS_Grad_Url_Page = Jsoup.connect(ITCS_Grad_Url).get();
        Document ITCS_Grad2_Url_Page = Jsoup.connect(ITCS_Grad2_Url).get();
        Document ITSC_Grad_Url_Page = Jsoup.connect(ITSC_Grad_Url).get();

        // Get class list from each page
        Elements ITIS_Undergrad_String = ITIS_Undergrad_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");
        Elements ITCS_Undergrad_String = ITCS_Undergrad_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");
        Elements ITSC_Undergrad_String = ITSC_Undergrad_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");
        Elements ITIS_Grad_String = ITIS_Grad_Url_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");
        Elements ITCS_Grad_String = ITCS_Grad_Url_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");
        Elements ITCS_Grad2_String = ITCS_Grad2_Url_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");
        Elements ITSC_Grad_String = ITSC_Grad_Url_Page.getElementsByClass("table_default").get(6).getElementsByTag("tr");

        // Put all elements into a single list
        Elements allClassElems = ITIS_Undergrad_String;
        allClassElems.addAll(ITCS_Undergrad_String);
        allClassElems.addAll(ITSC_Undergrad_String);
        allClassElems.addAll(ITIS_Grad_String);
        allClassElems.addAll(ITCS_Grad_String);
        allClassElems.addAll(ITCS_Grad2_String);
        allClassElems.addAll(ITSC_Grad_String);
        ArrayList<String> classLinks = new ArrayList<>();

        // Clean up the list
        for(int k = 0; k < allClassElems.size()-1; k++){
            if(allClassElems.get(k).getElementsByAttribute("href").attr("href").startsWith("/")){
                allClassElems.remove(k);
            }
            if(!allClassElems.get(k).getElementsByAttribute("href").attr("href").startsWith("preview_course_nopop")){
                allClassElems.remove(k);
            }
            classLinks.add("https://catalog.uncc.edu/" + allClassElems.get(k).getElementsByAttribute("href").attr("href"));
        }
        classLinks.removeIf(s -> s.equals("https://catalog.uncc.edu/"));
        classLinks.removeIf(s -> s.contains("https://catalog.uncc.edu//"));

        // Remove classes that have not been offered in 3 years
        for(int k = 0; k < classLinks.size(); k++){
            Document classPage = Jsoup.connect(classLinks.get(k)).get();
            boolean notOfferedDay = classPage.html().contains("Most Recently Offered (Day):</strong> Course has not been offered at this time in the past 3 years");
            boolean notOfferedEven = classPage.html().contains("Most Recently Offered (Evening):</strong> Course has not been offered at this time in the past 3 years");
            if(notOfferedDay && notOfferedEven){
                classLinks.remove(k);
            }
        }

        // Get class names
        ArrayList<String> classNames = new ArrayList<>();
        for (String classLink : classLinks) {
            Document classPage = Jsoup.connect(classLink).get();
            classNames.add(classPage.getElementById("course_preview_title").text());
        }

        classNames.sort(Comparator.naturalOrder());

        for (String s : classNames) {
            System.out.println(s);
        }

        return classNames;
    }
}
