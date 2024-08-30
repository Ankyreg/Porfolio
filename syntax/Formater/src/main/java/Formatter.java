
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {
    private final String splitCharReg = "[\\s,._\\-;]+";
    private Scanner scanner = new Scanner(System.in);

    public Person personCreator(Person person) throws Exception {
        System.out.println("Enter your name and surname, please");
        String name = scanner.nextLine();
        String[] nameSplit = checkLengthAndSymbols(name,0).split(splitCharReg);
        person.setName(nameSplit[0].replaceFirst(nameSplit[0].substring(0, 1), nameSplit[0].substring(0, 1).toUpperCase()));
        person.setSurname(nameSplit[1].replaceFirst(nameSplit[1].substring(0, 1), nameSplit[1].substring(0, 1).toUpperCase()));
        System.out.println("Enter your phone number, please!");
        person.setNumber(addPhone(scanner.nextLine(), 0));
        person.setCountry(addCountry(person.getNumber()));

        return person;
    }

    /**
     * The method checks for invalid characters and the length of the input.
     * If the input is correct, then the method calls the following method for checking input in Cyrillic {@link #checkCyrillic(String)}
     * @param text
     * @return
     */
    private String checkLengthAndSymbols(String text, int currentDeep) throws Exception {
        if (currentDeep == 5){
            throw new Exception("too many invalid input attempts");
        }
        Pattern pattern = Pattern.compile("[`~!@#%^&*+()/?>\\[<:|\\\\}{'\"№\\][0-9]]");
        Matcher matcher = pattern.matcher(text);
        String[] splitCheck = text.split(splitCharReg);
        if (splitCheck.length != 2 || matcher.find() || splitCheck[0].length() < 2 || splitCheck[1].length() < 2) {
        System.out.println("Input is incorrect, please try again!");
        return checkLengthAndSymbols(new Scanner(System.in).nextLine(), currentDeep + 1);
            } else return checkCyrillic(text);
    }

    /**
     * If the input contains Cyrillic, the method calls the method of converting to Latin {@link #replaceCyrillic(char)}
     * @param text
     * @return
     */

    private String checkCyrillic(String text) {
        Pattern pattern = Pattern.compile("[А-Яа-я]");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            StringBuilder builder = new StringBuilder();
            for (char ch : text.toCharArray()) {
                builder.append(replaceCyrillic(ch));
            }
            return builder.toString();
        }
        return text;
    }

    /**
     * The method validates the input and formats the number
     * @param number
     * @param currentDeep
     * @return
     * @throws Exception
     */

    private String addPhone(String number, int currentDeep) throws Exception {
        if (currentDeep == 5){
            throw new Exception("too many invalid input attempts");
        }
        if (number.matches("^\\+?[0-9]{8,15}")) {
            return number.replaceAll("\\+", "");
        }
        System.out.println("wrong format");
        return addPhone(scanner.nextLine(), currentDeep +1);
    }

    /**
     * The method tries to determine the expected country code from the first 6 digits
     * @param num
     * @return
     */

    public String addCountry(String num) {
        String code = num.substring(0, 6);
        ArrayList<CountryCode> matches = new ArrayList();
        for (CountryCode c : CountryCode.values()) {
            if (code.contains(c.getCountry())) {
                int country = c.getCountry().length();
                String numCode = code.substring(0, country);
                if (!numCode.equals(c.getCountry())) {
                    continue;
                }
                matches.add(c);
            }
        }
        if (matches.isEmpty()) {
            return "Unknown";
        }
        if (matches.size() > 1) {
            return checkCountryMatches(matches);
        }
        return matches.get(0).toString();
    }

    private String checkCountryMatches(ArrayList<CountryCode> matches) {
        int smaller = 0;
        while (!(matches.size() == 1)) {
            if (matches.get(0).getCountry().length() > matches.get(1).getCountry().length()) {
                smaller = 1;
            }
            matches.remove(smaller);
        }
       return matches.get(0).toString();
    }


    private String replaceCyrillic(char ch) {
            return switch (ch) {
                case 'a' -> "a";
                case 'б' -> "b";
                case 'в' -> "v";
                case 'г' -> "g";
                case 'д' -> "d";
                case 'е' -> "e";
                case 'ё' -> "jo";
                case 'ж' -> "zh";
                case 'з' -> "z";
                case 'и' -> "i";
                case 'й' -> "y";
                case 'к' -> "k";
                case 'л' -> "l";
                case 'м' -> "m";
                case 'н' -> "n";
                case 'о' -> "o";
                case 'п' -> "p";
                case 'р' -> "r";
                case 'с' -> "s";
                case 'т' -> "t";
                case 'у' -> "u";
                case 'ф' -> "f";
                case 'х' -> "h";
                case 'ц' -> "c";
                case 'ч' -> "ch";
                case 'ш' -> "sh";
                case 'щ' -> "shh";
                case 'ы' -> "ih";
                case 'ъ' -> "jhh";
                case 'ь' -> "jh";
                case 'э' -> "eh";
                case 'ю' -> "ju";
                case 'я' -> "ya";
                default -> String.valueOf(ch);
            };
}
}

