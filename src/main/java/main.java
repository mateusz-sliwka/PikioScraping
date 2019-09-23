
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.io.File.separator;


public class main {
    static boolean newArticles = false;

    public static String removeWord(String string, String word) {
        if (string.contains(word)) {
            String tempWord = word + " ";
            string = string.replaceAll(tempWord, "");
            tempWord = " " + word;
            string = string.replaceAll(tempWord, "");
        }
        return string;
    }

    public static String sanitizeFilename(String name) {
        return name.replaceAll("[:\\\\/*\"?|<>]", "_");
    }

    public static void downloadArticle(String link) {
        Document doc = null;
        boolean sthNew = false;
        try {
            doc = Jsoup.connect(link).get();
            boolean flag = isInArticles(sanitizeFilename(doc.title()).substring(0,20) + ".txt");
            if (!flag) {

                Elements formElements = doc.select("div.article-container");
                Elements e = formElements.select("p:not([class]),h2");

                BufferedWriter writer = new BufferedWriter(new FileWriter("artykuly" + separator + sanitizeFilename(doc.title()).substring(0, 20) + ".txt"));
                String result = Jsoup.parse(e.text()).text();
                result = removeWord(result, " DALSZA CZĘŚĆ TEKSTU POD GALERIĄ");
                result = removeWord(result, " DALSZA CZĘŚĆ TEKSTU POD GALERIĄ:");
                writer.write(result);
                writer.close();
                System.out.println("[NOWY] Dodano do bazy artykuł: " + link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInArticles(String newArticle) {
        File folder = new File("artykuly" + separator);
        boolean exist = false;
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                if (file.getName().equals(newArticle))
                    exist = true;
            }
        }
        if (exist) {
            return true;
        }
        newArticles = true;
        return false;
    }


    public static void checkPikiopl() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://pikio.pl/").get();
            Elements formElements = doc.select("div.news-description");
            Elements e = formElements.select("a");
            int i = 0;
            for (Element x : e) {
                String result = x.attr("href");
                if (!result.contains("/tag/") && !result.contains("/galeria"))
                    downloadArticle(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        checkPikiopl();
        if (newArticles == false)
            System.out.println("Baza jest aktualna nic nie dodano.");
        else
            System.out.println("Proces zakończony.");

    }

}

