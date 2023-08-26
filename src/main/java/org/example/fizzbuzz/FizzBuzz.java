package org.example.fizzbuzz;

public class FizzBuzz {

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            System.out.println(fizzbuzz(i));
        }
    }
    static String fizzbuzz(int num) {

        if (num == 42) {
            return "Answer to the Ultimate Question of Life, the Universe, and Everything";
        }

        String str = "";
        if (num % 3 == 0) {
            str += "Fizz ";
        }

        if (num % 5 == 0) {
            str += "Buzz ";
        }

        if (!str.isEmpty()) {
            return str;
        }

        return Integer.toString(num);
    }
}
