package hm5;


import java.util.Arrays;

public class Main {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        method1();
        method2();
    }

    static void method1() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        // System.out.println(Arrays.toString(arr));
        System.out.print("Время выполнения метода 1 ");
        System.out.println(System.currentTimeMillis() - a);

    }

    static void method2() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);

        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF);
        System.arraycopy(arr, HALF, arr2, 0, HALF);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr1.length; i++) {
                    arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < arr2.length; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(Arrays.toString(arr1));
//        System.out.println(Arrays.toString(arr2));

        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);

//        System.out.println(Arrays.toString(arr));
        System.out.print("Время выполнения метода 2 ");
        System.out.println(System.currentTimeMillis() - a);


    }
}
