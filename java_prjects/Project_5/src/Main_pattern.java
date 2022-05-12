public class Main_pattern {

    /**
     * Finds a word in a matrix.
     * @param crossword
     * @param target
     * @return an array of length 3.
     */
    public static int[] find(final char[][] crossword, final String target) {
        // strings to search:
        // - x horizontals
        // - x verticals
        // - (((x - 1) * 2) + 1) * 2 diagonals
        System.out.println(target);
        System.out.println();
        int x = crossword.length;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                System.out.print(crossword[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        // checking horizontal
        System.out.println("horizontals-----");
        for (int i = 0; i < x; i++) {
            char[] horz = crossword[i];
            System.out.println(new String(horz));
            int place = BoyerMoore.find(new String(horz) + new String(horz), target);
            if (place != -1) {
                int[] res = new int[3];
                if (place > x) {
                    place -= x;
                }
                res[0] = i;
                res[1] = place;
                res[2] = 2;
                return res;
            }
            place = BoyerMoore.find(reverse(new String(horz) + new String(horz)), target);
            if (place != -1) {
                int[] res = new int[3];
                if (place > x) {
                    place -= x;
                }
                place = x - place - 1;
                res[0] = i;
                res[1] = place;
                res[2] = 6;
                return res;
            }
            // String look = new String(horz);
            // look = look + look;
        }
        // checking verticals
        System.out.println();
        System.out.println("verticals------");
        for (int i = 0; i < x; i++) {
            char[] vert = new char[x];
            for (int j = 0; j < x; j++) {
                vert[j] = crossword[j][i];
            }
            System.out.println(new String(vert));
            int place = BoyerMoore.find(new String(vert) + new String(vert), target);
            if (place != -1) {
                int[] res = new int[3];
                if (place > x) {
                    place -= x;
                }
                res[0] = place;
                res[1] = i;
                res[2] = 0;
                System.out.println("found " + new String(vert));
                return res;
            }
            place = BoyerMoore.find(reverse(new String(vert) + new String(vert)), target);
            if (place != -1) {
                int[] res = new int[3];
                if (place > x) {
                    place -= x;
                }
                place = x - place - 1;
                res[0] = place;
                res[1] = i;
                res[2] = 4;
                return res;
            }
        }
        // checking diagonals
        System.out.println();
        System.out.println("diagonals left to right------");
        String[] diag1 = new String[2*x - 1];
        int ind = 0;

        for (int i = 0; i < x; i++) {
            diag1[ind] = "";
            diag1[diag1.length - ind - 1] = "";

            for (int j = 0; j <= i; j++) {
                char a = crossword[j][x-i+j-1];
                diag1[ind] = diag1[ind] + a;

                if (i != x - 1) {
                    a = crossword[x - i + j - 1][j];
                    diag1[diag1.length - ind - 1] = diag1[diag1.length - ind - 1] + a;
                }
            }
            //System.out.println();
            ind++;
        }
        /*
        for (int i = 0; i < x; i++) {
            if (i != 0) {
                diag1[diag1.length - i] = "";
            }
            for (int j = 0; j < x - i; j++) {
                if (i != 0) {
                    char a = crossword[j][j + i];
                    diag1[diag1.length - i] = diag1[diag1.length - i] + a;
                }
            }

            // first half
            diag1[i] = "";
            for (int j = 0; j < x - i; j++) {
                char a = crossword[j+i][j];
                diag1[i] = diag1[i] + a;
            }
        }*/

        for (int i = 0; i < diag1.length; i++) {
            System.out.println(i + ": " + diag1[i]);
        }

        for (int i = 0; i < diag1.length; i++) {
            if (target.length() <= diag1[i].length()) {
                int place = BoyerMoore.find(diag1[i] + diag1[i], target);
                if (place != -1) {
                    int[] res = new int[3];
                    System.out.println("place is " + place);
                    System.out.println(diag1[i].length());
                    System.out.println(i);
                    if (place >= diag1[i].length()) {
                        place -= diag1[i].length();
                    }
                    int[] help = diag1loc(crossword, place, i);
                    res[0] = help[1];
                    res[1] = help[0];
                    res[2] = 1;
                    System.out.println("returning " + res[0] + " " + res[1] + " " + res[2]);
                    return res;
                }
                place = BoyerMoore.find(reverse(diag1[i] + diag1[i]), target);
                if (place != -1) {
                    int[] res = new int[3];
                    if (place > x) {
                        place -= x;
                    }
                    int[] help = diag1loc(crossword, place, i);
                    res[0] = x - help[0] - 1;
                    res[1] = x- help[1] - 1;
                    res[2] = 5;
                    System.out.println("RETURNING 5-------------------------------------------");
                    return res;
                }
                System.out.println("1 " + diag1[i]);
            }
        }

        System.out.println();
        System.out.println("diagonals right to left------");
        String[] diag2 = new String[2*x - 1];
        int index = 0;
        for (int i = 0; i < x; i++) {
            diag2[index] = "";
            diag2[diag2.length - 1 - index] = "";
            for (int j = i; j >= 0; j--) {
                char a = crossword[i-j][j];
                diag2[index] += a;
                if (i != x - 1) {
                    a = crossword[x - i + j - 1][x - j - 1];
                    diag2[diag2.length - 1 - index] = a + diag2[diag2.length - 1 - index];
                }
            }
            index++;
        }

        for (int i = 0; i < diag2.length; i++) {
            System.out.println(diag2[i]);
        }
        for (int i = 0; i < diag2.length; i++) {
            if (target.length() <= diag2[i].length()) {
                int place = BoyerMoore.find(diag2[i] + diag2[i], target);
                if (place != -1) {
                    int[] res = new int[3];
                    System.out.println("place is " + place);
                    System.out.println(diag2[i].length());
                    System.out.println(i);
                    if (place >= diag2[i].length()) {
                        place -= diag2[i].length();
                    }
                    int[] help = diag1loc(crossword, place, i);
                    res[0] = help[1];
                    res[1] = help[0];
                    res[2] = 7;
                    System.out.println("returning " + res[0] + " " + res[1] + " " + res[2]);
                    System.out.println("RETURNING 7-------------------------------------------");
                    return res;
                }
                place = BoyerMoore.find(reverse(diag2[i] + diag2[i]), target);
                if (place != -1) {
                    int[] res = new int[3];
                    if (place > x) {
                        place -= x;
                    }
                    int[] help = diag2loc(crossword, place, i);
                    res[0] = x - help[0] - 1;
                    res[1] = x - help[1] - 1;
                    res[2] = 3;
                    System.out.println("RETURNING 3-------------------------------------------");
                    return res;
                }
                System.out.println("1 " + diag1[i]);
            }
        }
        System.out.println();

        int[] res = new int[3];
        res[0] = -1;
        res[1] = -1;
        res[2] = -1;
        return res;

    }

    public static String reverse(String in) {
        char[] rev = new char[in.length()];
        for (int i = 0; i < in.length(); i++) {
            rev[i] = in.charAt(in.length() - 1 - i);
        }
        return new String(rev);
    }

    public static int[] diag1loc(char[][] arr, int place, int i) {
        System.out.println("diag1loc: " + place + i);
        int x = arr.length;
        int[] res = new int[2];
        if (i < x) {
            res[0] = x - 1 - i + place;
            res[1] = place;
            return res;
        } else {
            res[0] = place;
            res[1] = i - x + 1 + place;
            return res;
        }
    }

    public static int[] diag2loc(char[][] arr, int place, int i) {
        System.out.println("diag1loc: " + place + i);
        int x = arr.length;
        int[] res = new int[2];
        if (i < x) {
            res[0] = place;
            res[1] = x - 1 - place;
            return res;
        } else {
            res[0] = i - x + place;
            res[1] = x - place - 1;
            return res;
        }
    }

    public static void main(String[] args) {

    }
}
