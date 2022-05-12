import jdk.swing.interop.SwingInterOpUtils;

/**
 * Part 3
 */
public class SoccerTeamPriorityQueue {

    private SoccerTeam[] teamList;
    private int numTeams;
    private SoccerTeamHashTable teamTable;

    /**
     * @return return the priority queue
     */
    public SoccerTeam[] getTeamList() {
        return teamList;
    }

    /**
     * @return the team table
     */
    public SoccerTeamHashTable getTeamTable() {
        return teamTable;
    }

    /**
     * Constructor of the class.
     * Initialize a new SoccerTeam array with the argument passed.
     */
    public SoccerTeamPriorityQueue(int capacity) {
        this.teamList = new SoccerTeam[capacity];
        this.numTeams = 0;
        this.teamTable = new SoccerTeamHashTable(capacity);

    }

    /**
     * Constructor to initialize the priority queue and global variables
     *
     * @param spq
     */
    public SoccerTeamPriorityQueue(SoccerTeamPriorityQueue spq) {
        SoccerTeam[] teams = spq.getTeamList();
        this.teamList = new SoccerTeam[teams.length];
        this.numTeams = teams.length;
        this.teamTable = new SoccerTeamHashTable(this.numTeams);

        for (int i = 0; i < this.numTeams; i++) {
            this.teamList[i] = new SoccerTeam(teams[i]);
            this.teamTable.put(new SoccerTeam(teams[i]));
        }

    }

    /**
     * @return return the number of teams
     */
    public int getNumTeams() {
        return this.numTeams;
    }

    /**
     * @return String representation of the priority queue
     */
    public String toString() {
        String str = "Priority Queue:\n";

        for (SoccerTeam team : this.teamList) {
            if (team != null) {
                str = str + team.toString() + "\n";
            }
        }

        str = str.substring(0, str.length());

        str += this.teamTable.toString();

        return str;
    }


    public int getParent(int index) {
        return (int) (index - 1) / 2;
    }

    public int getLeftChild(int index) {
        return index * 2 + 1;
    }

    public int getRightChild(int index) {
        return index * 2 + 2;
    }

    /**
     * @return return the index at which the team was inserted
     */

    public int insert(SoccerTeam c) {
        // System.out.println();
        //TODO: implement insert
        // System.out.println("Inserting " + c);
        // System.out.println("running insert");
        if (c == null) {
            // System.out.println("got a null soccer team! - roshan");
            return -1;
        }
        if (numTeams == teamList.length) {
            // System.out.println("soccer teamList is full! - roshan");
            return -1;
        }
        SoccerTeam a = teamTable.get(c.getName());
        if (a != null) {
            // c is in hashTable
            // System.out.println("Inserted Nothing");
            // System.out.println(this.toString());
            return a.getPosInQueue();
        } else {
            // c is not in the hashTable
            teamTable.put(c);
            teamList[numTeams] = c;
            numTeams++;
            c.setPosInQueue(numTeams - 1);
            // System.out.println(this.toString());
            heapifyUp(numTeams - 1);
            // System.out.println("PRINTING THE TABLE");
            // System.out.println(this.toString());
            // System.out.println("numTeams is " + numTeams);
            return c.getPosInQueue();
        }

    }

    /**
     * Implement the heapifyUp function
     *
     * @param index
     * @return the index where the heapifyUp for the given index ends
     */
    public int heapifyUp(int index) {
        //TODO implement heapifyUp
        // System.out.println("===================================");
        // System.out.println("running heapifyUp on " + teamList[index].getName());
        // System.out.println("running heapifyUp");
        if ((index != 0) && (teamList[index].compareTo(teamList[this.getParent(index)]) > 0)) {
            // if index isn't the root and has higher priority than its parent
            // swap with parent
            //      - switch positions in queue
            // System.out.println("made it into if statement");
            // System.out.println(this.toString());
            int b = this.getParent(index);
            swap(index, this.getParent(index));
            int x = heapifyUp(b);
            // System.out.println();
            // System.out.println(this.toString());
            // System.out.println("===================================");
            return x;

            //      - switch positions in the teamList
            // heapify parent
        }
        // System.out.println("===================================returning");
        return index;
    }
    public void swap(int ind1, int ind2) {

        int pos1 = teamList[ind1].getPosInQueue();
        int pos2 = teamList[ind2].getPosInQueue();
        // System.out.println("running swap where pos1 and pos2 are " + pos1 + pos2);
        SoccerTeam a = teamList[ind1];
        SoccerTeam tempTeam = new SoccerTeam(a.getName(), a.getPoints(), a.getMatchesPlayed(), a.getGoalsScored(),
                a.getGoalsConceded(), a.getPosInQueue());
        teamList[ind1] = teamList[ind2];
        teamList[ind2] = tempTeam;
        teamList[ind1].setPosInQueue(pos1);
        teamList[ind2].setPosInQueue(pos2);
        teamTable.get(teamList[ind1].getName()).setPosInQueue(pos1);
        teamTable.get(teamList[ind2].getName()).setPosInQueue(pos2);
    }

    /**
     * Implement the heapifyDown function
     *
     * @param index
     * @return the index where the heapifyDown for the given index ends
     */
    public int heapifyDown(int index) {
        //TODO implement heapifyDown
        // System.out.println(this.toString());
        // System.out.println(this.toString());
        // System.out.println("running heapifyDown");
        int smallest = index;
        int left = this.getLeftChild(index);
        int right = this.getRightChild(index);
        if ((left < numTeams) && (teamList[left].compareTo(teamList[index]) > 0)) {
            // left child priority is greater than at index
            smallest = left;
            if ((right < numTeams) && (teamList[right].compareTo(teamList[left]) > 0)) {
                smallest = right;
            }
        } if ((right < numTeams) && (teamList[right].compareTo(teamList[index]) > 0)) {
            // right child priority is greater than at index
            smallest = right;
            if ((left < numTeams) && (teamList[left].compareTo(teamList[right]) > 0)) {
                smallest = left;
            }
        } if (smallest != index) {
            swap(smallest, index);
            heapifyDown(smallest);
        }
        return index;
        /*
        System.out.println("running heapifyDown on index" + index);
        System.out.println(this.toString());
        int s = index;
        int left = this.getLeftChild(index);
        int right = this.getRightChild(index);
        System.out.println(teamList[left].compareTo(teamList[index]) > 0);
        System.out.println(teamList[right].compareTo(teamList[index]) > 0);
        if (teamList[left].compareTo(teamList[index]) > 0) {
            if (left <= index) {
                System.out.println("first if");
                s = left;
            }
        } if (teamList[right].compareTo(teamList[index]) > 0) {
            if (right <= index) {
                System.out.println("second if");
                s = right;
            }
        } if (s != index) {
            // swap s and index
            swap(s, index);
            heapifyDown(s);
        } else {
            System.out.println(this.toString());
            return index;
        }
        System.out.println("failed miserably - heapify down - roshan");
        return Integer.MIN_VALUE;*/

    }

    /**
     * remove the team with the highest priority from the queue
     *
     * @return return the team removed
     */
    public SoccerTeam delMax() {
        //TODO implement delMax
        // System.out.println("running delMax");
        //System.out.println(this.toString());
        if (numTeams == 0) {
            System.out.println("delMax has found that numTeams is 0");
            return null;
        }
        SoccerTeam a = teamList[0];
        SoccerTeam tempTeam = new SoccerTeam(a.getName(), a.getPoints(), a.getMatchesPlayed(), a.getGoalsScored(),
                a.getGoalsConceded(), a.getPosInQueue());

        numTeams--;
        teamList[0] = teamList[numTeams];
        teamList[0].setPosInQueue(0);
        teamList[numTeams] = null;
        this.teamTable.remove(a.getName());

        heapifyDown(0);


        // System.out.println(this.toString());
        return tempTeam;
    }

    /**
     * @return return the number of teams currently in the queue
     */
    public int size() {
        return this.numTeams;
    }

    /**
     * @return return true if the queue is empty; false else
     */
    public boolean isEmpty() {
        return (this.numTeams == 0);
    }

    /**
     * @return return the team with the maximum priority
     */
    public SoccerTeam getMax() {
        //TODO implement getMax
        if (numTeams == 0) {
            return null;
        } else {
            return teamList[0];
        }
    }

    /**
     * @param index
     * @param oldTeamNewValue
     * @return return the new index of the team oldTeamNewValue updated in the heap
     */
    public int update(int index, SoccerTeam oldTeamNewValue) {
        // System.out.println("running update");
        // System.out.println(oldTeamNewValue);
        // System.out.println(this.toString());
        //TODO implement update
        this.teamTable.put(oldTeamNewValue);
        teamList[oldTeamNewValue.getPosInQueue()] = oldTeamNewValue;
        heapifyDown(oldTeamNewValue.getPosInQueue());
        heapifyUp(oldTeamNewValue.getPosInQueue());
        // System.out.println(this.toString());
        return oldTeamNewValue.getPosInQueue();
    }

}