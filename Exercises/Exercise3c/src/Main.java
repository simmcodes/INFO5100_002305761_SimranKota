import java.util.Random;

class PrimeCalculator extends Thread {
    public void run() {
        int count = 0;
        int num = 2;

        while (count < 25) {
            if (isPrime(num)) {

                System.out.printf("[%s] Thread ID: %d - Prime: %d%n", System.currentTimeMillis(), Thread.currentThread().getId(), num);
                count++;
            }
            num++;
            randomDelay();
        }
    }


    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }


    private void randomDelay() {
        try {
            Thread.sleep(100 + new Random().nextInt(401));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


class FibonacciCalculator extends Thread {
    public void run() {
        int a = 0, b = 1;

        for (int i = 0; i < 50; i++) {
            System.out.printf("[%s] Thread ID: %d - Fibonacci: %d%n", System.currentTimeMillis(), Thread.currentThread().getId(), a);
            int next = a + b;
            a = b;
            b = next;
            randomDelay();
        }
    }


    private void randomDelay() {
        try {
            Thread.sleep(100 + new Random().nextInt(401));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


class FactorialCalculator extends Thread {
    public void run() {
        for (int i = 0; i <= 100; i++) {
            System.out.printf("[%s] Thread ID: %d - Factorial of %d: %d%n", System.currentTimeMillis(), Thread.currentThread().getId(), i, factorial(i));
            randomDelay();
        }
    }


    private long factorial(int n) {
        if (n == 0) return 1;
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }


    private void randomDelay() {
        try {
            Thread.sleep(100 + new Random().nextInt(401));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


public class Main {
    public static void main(String[] args) {

        Thread primeThread = new PrimeCalculator();
        Thread fibonacciThread = new FibonacciCalculator();
        Thread factorialThread = new FactorialCalculator();


        primeThread.start();
        fibonacciThread.start();
        factorialThread.start();


        try {
            primeThread.join();
            fibonacciThread.join();
            factorialThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
