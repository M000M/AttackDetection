package dag.test;

import java.util.*;

public class Node {
    private Node firstPre;
    private Node secondPre;
    private List<Node> lastList;
    private int weight;// 1,3,9,27,81...
    private int score;
    private int totalWeight;

    public Node() {
        weight = 0;
        score = 0;
        totalWeight = 0;
        lastList = new ArrayList<>();
    }

    public void addLastNode(Node node) {
        lastList.add(node);
    }

    public boolean setPre(Node node1, Node node2) {
        if (firstPre == null && secondPre == null) {
            firstPre = node1;
            secondPre = node2;
            return true;
        }
        return false;
    }

    public boolean setPre(Node node1) {
        if (firstPre == null) {
            firstPre = node1;
            return true;
        }
        return false;
    }

    public Node getFirstPre() {
        return firstPre;
    }

    public Node getSecondPre() {
        return secondPre;
    }

    public List<Node> getLastList() {
        return lastList;
    }

    public void updateTotalWeight(int detaWeight) {
        this.totalWeight = this.getTotalWeight() + detaWeight;
    }

    public void updateScore() {
        int firstScore = firstPre == null ? 0 : firstPre.getScore();
        int secondScore = secondPre == null ? 0 : secondPre.getScore();
        this.score = this.weight + firstScore + secondScore;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public int getScore() {
        return score;
    }

    public void updateAllTotalWeight(Node node) {
        Set<Node> nodeSet = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        int detaW = node.getWeight();
        queue.offer(node.getFirstPre());
        queue.offer(node.getSecondPre());
        nodeSet.add(node);
        while (!queue.isEmpty()) {
            Node temptnode = queue.poll();
            if (temptnode != null && !nodeSet.contains(temptnode)) {
                if (temptnode.getFirstPre() != null) {
                    queue.offer(temptnode.getFirstPre());
                }
                if (temptnode.getSecondPre() != null) {
                    queue.offer(temptnode.getSecondPre());
                }
                nodeSet.add(temptnode);
            }
        }
        for (Node temptnode : nodeSet
        ) {
//            System.out.println("weight"+temptnode.getWeight());
            temptnode.updateTotalWeight(detaW);
//            System.out.println("tw"+temptnode.getTotalWeight());
        }

    }
}
