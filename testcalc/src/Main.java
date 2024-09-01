import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение с пробелом (например, 3 + 4 или II + III):");

        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println("Ответ: " + result);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    static String calculate(String input) throws Exception {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new Exception("Неправильный арефметическое вырожение. Ожидается 'число операция число'.");
        }

        String firstNumber = parts[0];
        String operator = parts[1];
        String secondNumber = parts[2];

        boolean isRoman = isRomanNumber(firstNumber) && isRomanNumber(secondNumber);
        boolean isArabic = isArabicNumber(firstNumber) && isArabicNumber(secondNumber);

        if (!isRoman && !isArabic) {
            throw new Exception("Необходимо использовать либо арабские, либо римские числа.");
        }
        int numberone, nombertwo;

        if (isArabic) {
            numberone = Integer.parseInt(firstNumber);
            nombertwo = Integer.parseInt(secondNumber);
            checkArabicNumbers(numberone, nombertwo);
        } else {
            numberone = romanToArabic(firstNumber);
            nombertwo = romanToArabic(secondNumber);
            checkRomanNumbers(numberone, nombertwo);
        }

        int result = calc(numberone, nombertwo, operator);

        if (isArabic) {
            return String.valueOf(result);
        } else {
            return arabicToRoman(result);
        }
    }

    static int calc(int nomberone, int numbertwo, String operator) throws Exception {
        int result;

        switch (operator) {
            case "+":
                result = nomberone + numbertwo;
                break;
            case "-":
                result = nomberone - numbertwo;
                break;
            case "*":
                result = nomberone * numbertwo;
                break;
            case "/":
                if (numbertwo == 0) {
                    throw new Exception("Деление на ноль невозможно.");
                }
                result = nomberone / numbertwo;
                break;
            default:
                throw new Exception("Неизвестная операция: " + operator);
        }
        return result;
    }

    static boolean isRomanNumber(String str) {
        return str.matches("[I]{1,3}|IV|V|VI|VII|VIII|IX|X");
    }

    static boolean isArabicNumber(String str) {
        return str.matches("[1-9]|10");
    }

    static void checkArabicNumbers(int a, int b) throws Exception {
        if (a < 1 || a > 10 || b < 1 || b > 10) {
            throw new Exception("Арабские числа должны быть в диапазоне от 1 до 10.");
        }
    }

    static void checkRomanNumbers(int a, int b) throws Exception {
        if (a < 1 || b < 1) {
            throw new Exception("В римской системе нет чисел < I.");
        }
        if (a > 11 || b > 11){
            throw new Exception("Вводимое число должно быть не больше X");
        }
    }

    static int romanToArabic(String roman) {
        int total = 0;
        int prevValue = 0;

        for (char ch : roman.toCharArray()) {
            int value = romanValue(ch);
            total += value;

            if (prevValue < value) {
                total -= 2 * prevValue;
            }
            prevValue = value;
        }

        return total;
    }

    static int romanValue(char ch) {
        switch (ch) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            default: return 0;
        }
    }

    static String arabicToRoman(int number) throws Exception {
        if (number < 1 ){
            throw new Exception("В римской системе нет отрицательных чисел");
        }
        String[] hundreds = {"", "C"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        StringBuilder roman = new StringBuilder();
        roman.append(hundreds[(number % 1000) / 100]);
        roman.append(tens[(number % 100) / 10]);
        roman.append(units[number % 10]);

        return roman.toString();
    }

}