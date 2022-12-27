import java.util.Scanner;

public class qw {

    // Размер игровово поля
    private static final byte bPoleSize = 3;
    private static String[] pole = new String[bPoleSize * bPoleSize];
    // А тут у нас задается текущий игрок
    private static byte bPlayerNum = 0;

    public static void main(String[] args) throws Exception {
        int iTmp = 0;
        Scanner sc = new Scanner(System.in);

        // Инициализация поля
        for (int i = 0; i < bPoleSize * bPoleSize; i++)
            pole[i] = Integer.toString(++iTmp);

        // Играем, пока не наступит конец игры
        while (!isGameEnd()) {
            nextPlayer();
            while (true) {
                System.out.println("\nХод игрока " + bPlayerNum);
                showPole(); // Рисуем поле
                System.out.print("Наберите число, куда вы хотите вставить " + (1 == bPlayerNum ? "крестик" : "нолик") + ": ");
                if (sc.hasNextInt()) { // проверяем, есть ли в потоке целое число
                    iTmp = sc.nextInt() - 1; // считывает целое число с потока ввода и сохраняем в переменную
                    if (isValidInput(iTmp))
                        break;
                }
                System.out.println("Вы ввели неправильное число. Повторите ввод");
                sc.next();
            }
            try {
                putX(iTmp); // Вставляем на поле крестик или нолик
            } catch (Exception e) {
                System.out.println("Что-то пошло не так ;(");
            }
        }
        showPole();
    }

    /**
     * Проверяем корректность ввода. Введенное число должно быть по размеру поля
     * и поле должно быть в этом месте еще не заполнено
     */
    private static boolean isValidInput(int iIn) {
        if (iIn >= bPoleSize * bPoleSize) return false;
        if (iIn < 0) return false;
        switch (getX(iIn)) {
            case 'O':
            case 'X':
                return false;
        }

        return true;
    }

    /**
     * Функция задает номер следующего игрока
     */
    private static void nextPlayer() {
        bPlayerNum = (byte) (1 == bPlayerNum ? 2 : 1);
    }

    /**
     * Определяем, наступил конец игры или нет
     * Условия:
     * 1) Победили крестики
     * 2) Победили нолики
     * 3) Кончились ходы
     */
    private static boolean isGameEnd() {
        int i, j;
        boolean bRowWin = false, bColWin = false;

        // Проверка победы на колонках и столбиках
        for (i = 0; i < bPoleSize; i++) {
            bRowWin = true;
            bColWin = true;
            for (j = 0; j < bPoleSize-1; j++) {
                bRowWin &= (getXY(i,j).charAt(0) == getXY(i,j+1).charAt(0));
                bColWin &= (getXY(j,i).charAt(0) == getXY(j+1,i).charAt(0));
            }
            if (bColWin || bRowWin) {
                System.out.println("Победил игрок " + bPlayerNum);
                return true;
            }
        }

        // Проверка победы по диагоналям
        bRowWin = true;
        bColWin = true;
        for (i=0; i<bPoleSize-1; i++) {
            bRowWin &= (getXY(i,i).charAt(0) == getXY(i+1,i+1).charAt(0));
            bColWin &= (getXY(i, bPoleSize-i-1).charAt(0) == getXY(i+1, bPoleSize-i-2).charAt(0));
        }
        if (bColWin || bRowWin) {
            System.out.println("Победил игрок " + bPlayerNum);
            return true;
        }

        // Проверка существования новых ходов
        for (i = 0; i < bPoleSize * bPoleSize; i++) {
            switch (getX(i)) {
                case 'O':
                case 'X':
                    break;
                default:
                    return false;
            }
        }
        if (bPoleSize*bPoleSize <= i) {
            System.out.println("Ничья. Кончились ходы.");
            return true;
        }

        // Продолжаем игру
        return false;
    }

    /**
     * Получает значение координаты на поле
     */
    private static String getXY(int x, int y) {
        return pole[x * bPoleSize + y];
    }

    /**
     * Получает значение координаты на поле
     */
    private static char getX(int x) {
        return pole[x].charAt(0);
    }

    /**
     * Вставляет на поле крестик или нолик
     */
    private static void putX(int x) {
        pole[x] = 1 == bPlayerNum ? "X" : "O";
    }

    /**
     * Вывести игровое поле
     */
    private static void showPole() {
        for (int i = 0; i < bPoleSize; i++) {
            for (int j = 0; j < bPoleSize; j++) {
                System.out.printf("%4s", getXY(i, j));
            }
            System.out.print("\n");
        }
    }

}