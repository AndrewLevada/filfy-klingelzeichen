package com.example.filfyklingelzeichen;

public class Equation implements Task {
    int b, c, x1, x2;

    public Equation(Difficulty difficulty) {
        do {
            x1 = difficulty.getX1();
            x2 = difficulty.getX2();
            b = x1 + x2;
            c = x1 * x2;
        } while (b == 0 || c == 0);
    }

    @Override
    public String getEquation() {
        return "x² "
                + (b > 0 ? "- " : "+ ") + Math.abs(b) + "x "
                + (c > 0 ? "+ " : "- ") + Math.abs(c) + " = 0";
    }

    @Override
    public int[] getAnswers() {
        if (x1 == x2)
            return new int[] { x1 };
        else
            return new int[] { x1, x2 };
    }

    public interface Difficulty {
        int getX1();
        int getX2();
    }

    public static class Easy implements Difficulty {

        @Override
        public int getX1() {
            return (int) (-3 + Math.random() * 9); // [-3;6)
        }

        @Override
        public int getX2() {
            return (int) (-3 + Math.random() * 9); // [-3;6)
        }
    }

    public static class Medium implements Difficulty {

        @Override
        public int getX1() {
            return (int) (-9 + Math.random() * 20); // [-9;11)
        }

        @Override
        public int getX2() {
            return (int) (-9 + Math.random() * 20); // [-9;11)
        }
    }

    public static class Hard implements Difficulty {

        @Override
        public int getX1() {
            return (int) (-12 + Math.random() * 28); // [-12;16)
        }

        @Override
        public int getX2() {
            return (int) (-12 + Math.random() * 28); // [-12;16)
        }
    }
}
