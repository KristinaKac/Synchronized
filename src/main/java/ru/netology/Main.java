package ru.netology;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> cars = new ArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                synchronized (cars) {
                    cars.add("Toyota");
                    System.out.println("Производитель выпустил авто - " + cars.get(0));
                    cars.notify();
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                synchronized (cars) {
                    System.out.println("Покупатель 1 зашел в автосалон.");
                    if (cars.isEmpty()) {
                        try {
                            System.out.println("Новых авто пока нет...");
                            cars.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Покупатель 1 уехал на новом авто " + cars.remove(0));
                }
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                synchronized (cars) {
                    System.out.println("Покупатель 2 зашел в автосалон.");
                    if (cars.isEmpty()) {
                        try {
                            System.out.println("Новых авто пока нет...");
                            cars.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Покупатель 2 уехал на новом авто " + cars.remove(0));
                }
            }
        }).start();
    }
}