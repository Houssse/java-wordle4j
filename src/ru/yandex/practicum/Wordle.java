package ru.yandex.practicum;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) throws IOException {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        List<String> words = loader.readingFile(Paths.get("words_ru.txt"));

        WordleDictionary dictionary = new WordleDictionary();
        dictionary.setWords(new HashSet<>(words));

        WordleGame game = new WordleGame(dictionary);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("У вас " + game.getSteps() + " попыток");
            System.out.println("Введите слово: ");

            String guess = scanner.nextLine();

            try {
                String result = game.processGuess(guess);
                System.out.println(result);

                if (result.equals("+++++")) {
                    System.out.println("Победа " + guess + " верное слово " + result);
                    break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (game.getSteps() == 0) {
                System.out.println("У вас не осталось попыток вы проиграли");
                System.out.println("Верное слово было " + game.getAnswer());
                break;
            }

        }

    }

}
