import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinksFromMailNewsToCLCKru_nado_dodelat {
    private static List<String> textOfNews() throws IOException{
        String url = "https://mail.ru/";
        String regex = "(\"news__list__item__link__text\">)([A-zА-я0-9\\s«»:\"-.–]{3,})(<\\/span><\\/a><\\/div>)";
        StringBuilder sb = new StringBuilder();
        List<String> list0 = new ArrayList<>();

        InputStream inputStream = new URL(url).openStream();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine())
            sb.append(scanner.nextLine());

        scanner.close();
        inputStream.close();


        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sb.toString());
        while (m.find()) {
            list0.add(m.group().replaceAll("\"news__list__item__link__text\">|<\\/span><\\/a><\\/div>", ""));
        }
        return list0;
    }

    private static List<String> urlsMailLinks() throws IOException {
        String url = "https://mail.ru/";
        String regex = "(\\srel=\"noopener\" href=\"https://)(.*?\")";
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = new URL(url).openStream();
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine())
            sb.append(scanner.nextLine());

        scanner.close();
        inputStream.close();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sb);

        List<String> list = new LinkedList<>();

        while (m.find()) {
            list.add(m.group().replaceAll(" rel=\"noopener\" href=\"|\"|/\\?from.*\\b", ""));
        }
        return list;
    }
    private static List<String> urlsClckLinks(List<String> list) throws IOException {
        String urlClck = "https://clck.ru/--?url=";
        List<String> list1 = new LinkedList<>();

        for (int i = 0; i < list.size(); i++) {
            InputStream inputStream = new URL(urlClck + list.get(i)).openStream();
            Scanner scanner = new Scanner(inputStream);

            while (scanner.hasNextLine())
                list1.add(scanner.nextLine());

            scanner.close();
            inputStream.close();
        }
        return list1;
    }
    private static void printTwoLinks() throws IOException {
        List<String> mailTexts = textOfNews();
        List<String> mail = urlsMailLinks();
        List<String> clck = urlsClckLinks(mail);

        for (int i = 0; i < mailTexts.size(); i++) {
            System.out.println(clck.get(i) + " - " + mailTexts.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        printTwoLinks();
    }
}
