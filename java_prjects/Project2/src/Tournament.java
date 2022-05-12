/**
 * Part 4
 */


public class Tournament {

    private int teamNo;
    private String[] teamNames;
    private SoccerTeamPriorityQueue teamPQ;

    /**
     * Initialize the fields
     * @param teamNames
     */
    public Tournament(String[] teamNames) {
        //TODO implement tournament
        this.teamNo = teamNames.length;
        this.teamNames = teamNames;
        SoccerTeam s;
        teamPQ = new SoccerTeamPriorityQueue(teamNo);
        for (int i = 0; i < teamNo; i++) {
            teamPQ.insert(new SoccerTeam(teamNames[i]));
            // put teamNames[i] in queue
        }
    }

    /**
     * @return an array of team names
     */
    public String[] getTeamNames() {
        return this.teamNames;
    }

    /**
     * Find the kth team (1-index) from the teams
     * @param k
     */
    public SoccerTeam findKthTeam(int k) {
        //TODO implement findKthTeam
        // System.out.println("running findKthTeam for ===================== " + k);
        // System.out.println(this.teamPQ);
        SoccerTeamPriorityQueue P = new SoccerTeamPriorityQueue(this.teamNo);
        SoccerTeamPriorityQueue R = new SoccerTeamPriorityQueue(this.teamNo);
        for (int i = 0; i < teamNo; i++) {
            SoccerTeam a = teamPQ.getTeamList()[i];
            SoccerTeam tempTeam = new SoccerTeam(a.getName(), a.getPoints(), a.getMatchesPlayed(), a.getGoalsScored(),
                    a.getGoalsConceded(), a.getPosInQueue());
            R.insert(tempTeam);
        }
        // System.out.println(this.teamPQ);
        // System.out.println(R);
        // System.out.println(R);
        // P.insert(R.getMax());
        // System.out.println("this is P");
        // System.out.println(P);
        for (int i = 1; i < k; i++) {
            R.delMax();
        }
        // System.out.println(R.getMax());
        // System.out.println("returning ........");
        // System.out.println(this.teamPQ.getTeamTable().get(R.getMax().getName()));
        return this.teamPQ.getTeamTable().get(R.getMax().getName());
        /*
        for (int i = 1; i < k; i++) {
            SoccerTeam T = P.getMax();
            int left = R.getLeftChild(T.getPosInQueue());
            int right = R.getRightChild(T.getPosInQueue());
            if ((left < R.getNumTeams()) && (left != T.getPosInQueue())) {
                P.insert(R.getTeamList()[left]);
            }
            if ((right < R.getNumTeams()) && (right != T.getPosInQueue())) {
                P.insert(R.getTeamList()[right]);
            }
            P.delMax();
        }
        System.out.println("returning........");
        System.out.println(P.getMax());
        String name = P.getMax().getName();
        SoccerTeam hello = teamPQ.getTeamTable().get(name);
        return hello;*/

    }

    /**
     * Update the priority queue and hash table with this information from a match.
     * @param teamName1
     * @param goal1
     * @param teamName2
     * @param goal2
     */
    public void updateTournament(String teamName1, int goal1, String teamName2, int goal2) {
        //TODO implement updateTournament
        System.out.println("888888888888888888888888888888888888888");
        System.out.println(teamName1 + " scored " + goal1 + " on " + teamName2);
        System.out.println(teamName2 + " scored " + goal2 + " on " + teamName1);
        // System.out.println(this.teamPQ);

        SoccerTeam t1 = this.teamPQ.getTeamTable().get(teamName1);
        SoccerTeam team1 = new SoccerTeam(teamName1);
        int p1 = t1.getPoints();
        int gc1 = t1.getGoalsConceded();
        int gs1 = t1.getGoalsScored();
        int mp1 = t1.getMatchesPlayed();
        team1.setGoalsConceded(gc1 + goal2);
        team1.setGoalsScored(gs1 + goal1);
        team1.setMatchesPlayed(mp1 + 1);
        team1.setPosInQueue(t1.getPosInQueue());
        if (goal1 > goal2) {
            // team1 won
            team1.setPoints(p1 + 3);
        } else if (goal1 == goal2) {
            // tie
            team1.setPoints(p1 + 1);
        }
        this.teamPQ.update(team1.getPosInQueue(), team1);

        SoccerTeam t2 = this.teamPQ.getTeamTable().get(teamName2);
        SoccerTeam team2 = new SoccerTeam(teamName2);
        int p2 = t2.getPoints();
        int gc2 = t2.getGoalsConceded();
        int gs2 = t2.getGoalsScored();
        int mp2 = t2.getMatchesPlayed();
        team2.setGoalsConceded(gc2 + goal1);
        team2.setGoalsScored(gs2 + goal2);
        team2.setMatchesPlayed(mp2 + 1);
        team2.setPosInQueue(t2.getPosInQueue());
        if (goal1 < goal2) {
            // team2 won
            team2.setPoints(p2 + 3);
        } else if (goal1 == goal2) {
            // tie
            team2.setPoints(p2 + 1);
        }
        this.teamPQ.update(team2.getPosInQueue(), team2);
        System.out.println(this.teamPQ);
        System.out.println("99999999999999999999999999999999999999999999999");
        /*
        SoccerTeam t1 = this.teamPQ.getTeamTable().get(teamName1);
        SoccerTeam t2 = this.teamPQ.getTeamTable().get(teamName2);
        SoccerTeam t3 = this.teamPQ.getTeamList()[t1.getPosInQueue()];
        SoccerTeam t4 = this.teamPQ.getTeamList()[t2.getPosInQueue()];

        int p1 = t1.getPoints();
        int gc1 = t1.getGoalsConceded();
        int gs1 = t1.getGoalsScored();
        int mp1 = t1.getMatchesPlayed();
        int p2 = t2.getPoints();
        int gc2 = t2.getGoalsConceded();
        int gs2 = t2.getGoalsScored();
        int mp2 = t2.getMatchesPlayed();
        t1.setGoalsConceded(gc1 + goal2);
        t2.setGoalsConceded(gc2 + goal1);
        t1.setGoalsScored(gs1 + goal1);
        t2.setGoalsScored(gs2 + goal2);
        t1.setMatchesPlayed(mp1 + 1);
        t2.setMatchesPlayed(mp2 + 1);

        t3.setGoalsConceded(gc1 + goal2);
        t4.setGoalsConceded(gc2 + goal1);
        t3.setGoalsScored(gs1 + goal1);
        t4.setGoalsScored(gs2 + goal2);
        t3.setMatchesPlayed(mp1 + 1);
        t4.setMatchesPlayed(mp2 + 1);

        if (goal1 > goal2) {
            // team1 won
            t1.setPoints(p1 + 3);
            t3.setPoints(p1 + 3);
        } else if (goal1 < goal2) {
            // team2 won
            t2.setPoints(p2 + 3);
            t4.setPoints(p2 + 3);
        } else {
            // tie
            t1.setPoints(p1 + 1);
            t3.setPoints(p1 + 1);
            t2.setPoints(p2 + 1);
            t4.setPoints(p2 + 1);
        }
        System.out.println();
        System.out.println("After the variables were added");
        System.out.println(this.teamPQ);
        this.teamPQ.update(t1.getPosInQueue(), t1);
        int pos2 = this.teamPQ.getTeamTable().get(t2.getName()).getPosInQueue();
        this.teamPQ.update(pos2, t2);
        */

    }

    /**
     * @return string representation of the priority queue
     */
    public String toString() {
        return "Priority Queue and hash table:\n" + this.teamPQ.toString();
    }

}