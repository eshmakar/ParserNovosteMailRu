import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserNovosteMailRu {

    public static void main(String[] args) throws IOException {
        String url = "https://mail.ru/";
        String regex = "(\"news__list__item__link__text\">)([A-zА-я0-9\\s«»:\"-.–]{3,})(<\\/span><\\/a><\\/div>)";
        StringBuilder sb = new StringBuilder();

        InputStream inputStream = new URL(url).openStream();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine())
            sb.append(scanner.nextLine());

        scanner.close();
        inputStream.close();


        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sb.toString());
        int count = 1;
        while (m.find())
            System.out.println(count++ + ". " + m.group().replaceAll("\"news__list__item__link__text\">|<\\/span><\\/a><\\/div>", ""));

    }
}