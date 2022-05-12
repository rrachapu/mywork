/**
 * Part 2
 */

import java.util.ArrayList;

public class SoccerTeamHashTable {

    private ArrayList<SoccerTeam>[] table;
    private int numTeams;
    private int tableCapacity;

    /**
     * Initialize a new SoccerTeam array with the argument passed.
     */
    public SoccerTeamHashTable(int capacity) {
        this.tableCapacity = this.getNextPrime(capacity);
        this.table = new ArrayList[this.tableCapacity];
        this.numTeams = 0;
    }

    // manual for testing
    public static void main(String[] args) {

    }

    /**
     * Write the code for the evaluating the expression
     *
     * @param expression
     * @return
     */
    public int evaluateExpression(String expression) {
        int values[] = new int[28];
        for (int i = 0; i < 26; i++) {
            values[i] = 'a' + i;
        }
        values[26] = 10;
        values[27] = tableCapacity;
        Expression exp = new Expression(expression);
        return exp.evaluate(exp.makeTree(), values);
    }

    /**
     * for a given name, it will return an expression to calculate the hash value.
     *
     * @param name
     * @return
     */
    public String getExpression(String name) {
        //TODO implement getExpression
        if (name.length() == 0) {
            return "";
        }
        String out = "((";
        out += name.charAt(0);
        out += "%M)";
        String timesE = "";
        for (int i = 1; i < name.length(); i++) {
            out += "+(((";
            out += name.charAt(i);
            out += "%M)";
            timesE += "*E";
            out += timesE;
            out += ")%M)";
        }
        out += ")%M";

        return out;
    }

    /**
     * calculate the hashvalue.
     *
     * @param name
     * @return
     */
    public int getHash(String name) {
        //TODO implement getHash
        return this.evaluateExpression(this.getExpression(name));
    }

    /**
     * find if a SoccerTeam object with the name is in the hash table
     *
     * @param name
     * @return
     */
    public SoccerTeam get(String name) {
        int ind = getHash(name);
        if (table[ind] != null) {
            for (int i = 0; i < table[ind].size(); i++) {
                if (table[ind].get(i).getName().equals(name)) {
                    return table[ind].get(i);
                }
            }
        }
        return null;
    }

    /**
     * Insert the SoccerTeam object in the hash table.
     *
     * @param c
     */
    public void put(SoccerTeam c) {
        //TODO implement put
        int ind = getHash(c.getName());

        if (table[ind] == null) {
            // if no soccer teams were added at the hash value
            table[ind] = new ArrayList<SoccerTeam>();
            table[ind].add(c);
            numTeams++;
        } else {
            // if soccer teams are already added at the hash value
            if (this.get(c.getName()) != null) {
                // soccer team with the same name already exists at the index
                if (this.get(c.getName()).compareTo(c) == 0) {
                    // soccer team has the same exact values
                    return;
                } else {
                    // soccer team has same name but not same values
                    for (int i = 0; i < table[ind].size(); i++) {
                        // looping through arraylist at hash value
                        if (table[ind].get(i).getName().equals(c.getName())) {
                            table[ind].set(i, c);
                        }
                    }
                }
            } else {
                // soccer team with same name doesn't exist
                table[ind].add(c);
                numTeams++;
            }
        }
    }

    /**
     * remove the soccer team else return null
     *
     * @param name
     * @return
     */
    public SoccerTeam remove(String name) {
        //TODO implement remove
        SoccerTeam a = this.get(name);
        if (a == null) {
            // if soccer team doesn't exist in table
            return null;
        } else {
            // soccer team exists in table
            int ind = getHash(name);
            for (int i = 0; i < table[ind].size(); i++) {
                if (table[ind].get(i).getName().equals(name)) {
                    numTeams--;
                    return table[ind].remove(i);
                }
            }
        }
        return null;
    }

    // return the number of teams
    public int size() {
        return this.numTeams;
    }

    // get the table capacity
    public int getTableCapacity() {
        return this.tableCapacity;
    }

    // get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3) {
            return num;
        }

        int rem = num % 6;

        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }

        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }

        return num;
    }

    // determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;

        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    // return the table
    public ArrayList<SoccerTeam>[] getArray() {
        return this.table;
    }

    // returns the string representation of the object
    public String toString() {
        String ret = "";
        for (int i = 0; i < this.table.length; i++) {
            ret += Integer.valueOf(i);

            // System.out.println(this.table[i]);

            if (this.table[i] != null) {
                for (SoccerTeam team : this.table[i]) {
                    ret += " ";
                    ret += team.toString();
                    ret += " ";
                }

                ret = ret.trim();
                // ret = ret.substring(0, ret.length() - 1);

            }

            ret += "\n";
        }
        return ret.substring(0, ret.length() - 1);
    }
}