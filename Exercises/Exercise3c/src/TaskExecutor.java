import java.util.concurrent.*;
import java.util.Random;

public class TaskExecutor {
    private static final int NUMBER_OF_THREADS = 5;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static void main(String[] args) {

        for (int i = 0; i < 25; i++) {
            final int num = i;
            executorService.submit(() -> {
                System.out.printf("[%s] Thread ID: %d - Prime: %d%n", System.currentTimeMillis(), Thread.currentThread().getId(), getNthPrime(num));
                randomDelay();
            });
        }


        for (int i = 0; i < 50; i++) {
            final int index = i;
            executorService.submit(() -> {
                System.out.printf("[%s] Thread ID: %d - Fibonacci: %d%n", System.currentTimeMillis(), Thread.currentThread().getId(), getFibonacci(index));
                randomDelay();
            });
        }


        for (int i = 0; i <= 100; i++) {
            final int num = i;
            executorService.submit(() -> {
                System.out.printf("[%s] Thread ID: %d - Factorial of %d: %d%n", System.currentTimeMillis(), Thread.currentThread().getId(), num, factorial(num));
                randomDelay();
            });
        }

        executorService.shutdown();
    }


    private static int getNthPrime(int n) {
        int count = 0, num = 2;
        while (count <= n) {
            if (isPrime(num)) {
                count++;
            }
            num++;
        }
        return num - 1;
    }


    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }


    private static int getFibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int next = a + b;
            a = b;
            b = next;
        }
        return b;
    }


    private static long factorial(int n) {
        if (n == 0) return 1;
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }


    private static void randomDelay() {
        try {
            Thread.sleep(100 + new Random().nextInt(401));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

