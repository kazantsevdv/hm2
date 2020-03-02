package hw2;

public class Main {


    public static void main(String[] args) {
        String[][] str = {
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"},
                {"1", "1", "1", "1"}
        };

        try {
            System.out.println(sumArr(str));
        } catch (MyArraySizeException e) {
            // e.printStackTrace();
            System.out.println("Не верный размер массива");
        } catch (MyArrayDataException e) {
            // e.printStackTrace();
            System.out.println("Ошибка данных в ячейке [" + e.getRow() + "][" + e.getCol() + "]");
        }
    }

    public static int sumArr(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        //проверка размера массива 4х4
        boolean wrongSize = false;
        if (arr.length != 4) wrongSize = true;
        for (String[] strings : arr) {
            if (strings.length != 4) {
                wrongSize = true;
                break;
            }
        }
        if (wrongSize) throw new MyArraySizeException("не верный размер массива");

        //Суммирем элементы
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {

                    throw new MyArrayDataException("Ошибка данных [" + i + "][" + j + "]", i, j);
                }
            }
        }
        return sum;
    }

}
