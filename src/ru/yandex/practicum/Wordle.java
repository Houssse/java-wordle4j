package ru.yandex.practicum;

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

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WordleGame game = new WordleGame();

        System.out.println("Я загадал слово из 5 у вас 5 ходов");

        while (!game.isFished()) {
            System.out.println("ход  " + (game.getSteps() + 1));
            System.out.print("-> ");
            String answer = input.nextLine();
            System.out.print("-> ");
            System.out.println(game.checkAnswer(answer));
        }
    }

}
