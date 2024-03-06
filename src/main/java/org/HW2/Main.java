package org.HW2;

public class Main {
    public static void main(String[] args) {
        // Создаем игрушки
        ToyItem doll = new ToyItem("Doll");
        ToyItem car = new ToyItem("Car");
        ToyItem ball = new ToyItem("Ball");

        // Создаем коробки
        ToyBox bigBox = new ToyBox();
        ToyBox smallBox = new ToyBox();

        // Добавляем игрушки в коробки
        bigBox.add(doll);
        bigBox.add(car);
        smallBox.add(ball);

        // Вложенные коробки
        ToyBox nestedBox = new ToyBox();
        nestedBox.add(smallBox);
        nestedBox.add(new ToyItem("Blocks"));

        // Добавляем вложенную коробку в большую коробку
        bigBox.add(nestedBox);

        // Выводим информацию о коробках
        bigBox.displayInfo();
    }
}
