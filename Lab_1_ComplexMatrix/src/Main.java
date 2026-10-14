import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Matrix resultMemory = null;
        while (true) {
            if (resultMemory != null) {
                System.out.println("В памяти сохранен предыдущий результат матрицы");
            }
            System.out.println("1. Сложение матриц (A + B)");
            System.out.println("2. Умножение матриц (A * B)");
            System.out.println("3. Деление матриц (A / B)");
            System.out.println("4. Транспонирование матрицы (A^T)");
            System.out.println("5. Вычислить определитель матрицы (det A)");
            System.out.println("6. Очистить память и начать заново");
            System.out.println("7. Выйти из программы");
            System.out.print("Выберите пункт меню 1-7: ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("7")) {
                System.out.println("Программа успешно завершена");
                break;
            }
            if (choice.equals("6")) {
                resultMemory = null;
                System.out.println("Память калькулятора успешно очищена");
                continue;
            }

            try {
                Matrix a = null;
                if (resultMemory != null) {
                    a = resultMemory;
                } else if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5")) {
                    a = Matrix.readFromConsole(scanner, 1);
                }
                if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                    Matrix b = Matrix.readFromConsole(scanner, 2);
                    System.out.println("\nМатрица A:");
                    a.display();
                    System.out.println("\nМатрица B:");
                    b.display();
                    System.out.println("\nРезультат операции:");
                    switch (choice) {
                        case "1" -> {
                            resultMemory = a.add(b);
                            resultMemory.display();
                        }
                        case "2" -> {
                            resultMemory = a.multiply(b);
                            resultMemory.display();
                        }
                        case "3" -> {
                            resultMemory = a.divide(b);
                            resultMemory.display();
                        }
                    }
                }
                else if (choice.equals("4") || choice.equals("5")) {
                    System.out.println("\nМатрица A:");
                    a.display();
                    System.out.println("\nРезультат операции:");
                    if (choice.equals("4")) {
                        resultMemory = a.transpose();
                        resultMemory.display();
                    } else {
                        Complex det = a.determinant();
                        System.out.println("det A = " + det);
                    }
                } else {
                    System.out.println("Ошибка! Неверный пункт меню, попробуйте еще раз.");
                }

            } catch (Exception e) {
                System.out.println("\nПРОИЗОШЛА ОШИБКА: " + e.getMessage());
            }
        }
        scanner.close();
    }
}