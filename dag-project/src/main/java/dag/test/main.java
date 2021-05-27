package dag.test;

import java.util.*;

public class main {
    public static int BEGIN_NUMBER = 3;
    public static int MIN_WEIGHT = 0;
    public static int MAX_WEIGHT = 100;
    public static int MIN_SOCRE = 0;

    public static void main(String[] args) {

        Node theFirstNode = init();
        Node node = new Node();
        node.setWeight(1);
        addNode(theFirstNode, node);
    }

    public static Node init() {
        Node first = new Node();
        first.setWeight(1);
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();
        Node d = new Node();
        Node e = new Node();
        a.setWeight(1);
        b.setWeight(2);
        c.setWeight(3);
        d.setWeight(4);
        e.setWeight(5);
        a.setPre(first);
        b.setPre(first);
        c.setPre(first);
        d.setPre(first);
        e.setPre(first);
        first.addLastNode(a);
        first.addLastNode(b);
        first.addLastNode(c);
        first.addLastNode(d);
        first.addLastNode(e);
        a.updateScore();
        b.updateScore();
        c.updateScore();
        d.updateScore();
        e.updateScore();
        a.updateAllTotalWeight(a);
        b.updateAllTotalWeight(b);
        c.updateAllTotalWeight(c);
        d.updateAllTotalWeight(d);
        e.updateAllTotalWeight(e);

        Node f = new Node();
        Node g = new Node();
        Node h = new Node();
        Node i = new Node();
        Node j = new Node();
        f.setWeight(6);
        g.setWeight(7);
        h.setWeight(8);
        i.setWeight(9);
        j.setWeight(10);
        f.setPre(a, c);
        g.setPre(c, d);
        h.setPre(a, e);
        i.setPre(a, c);
        j.setPre(c, e);
        a.addLastNode(f);
        a.addLastNode(h);
        a.addLastNode(i);
        c.addLastNode(f);
        c.addLastNode(g);
        c.addLastNode(i);
        c.addLastNode(j);
        d.addLastNode(g);
        e.addLastNode(h);
        e.addLastNode(j);
        f.updateScore();
        g.updateScore();
        h.updateScore();
        i.updateScore();
        j.updateScore();
        f.updateAllTotalWeight(f);
        g.updateAllTotalWeight(g);
        h.updateAllTotalWeight(h);
        i.updateAllTotalWeight(i);
        j.updateAllTotalWeight(j);
//        System.out.println(a.getTotalWeight());
//        System.out.println(a.getWeight());
//        System.out.println(b.getTotalWeight());
//        System.out.println(b.getWeight());
//        System.out.println(c.getTotalWeight());
//        System.out.println(c.getWeight());
//        System.out.println(d.getTotalWeight());
//        System.out.println(d.getWeight());
//        System.out.println(e.getTotalWeight());
//        System.out.println(e.getWeight());
//        System.out.println(f.getTotalWeight());
//        System.out.println(f.getWeight());
//        System.out.println(g.getTotalWeight());
//        System.out.println(g.getWeight());
//        System.out.println(h.getTotalWeight());
//        System.out.println(h.getWeight());
//        System.out.println(i.getTotalWeight());
//        System.out.println(i.getWeight());
//        System.out.println(j.getTotalWeight());
//        System.out.println(j.getWeight());

        return first;
    }

    public static boolean addNode(Node firstNode, Node newNode) {
        List<Node> beginNode = findBegin(firstNode, BEGIN_NUMBER, MIN_WEIGHT, MAX_WEIGHT);
        List<Node> twoNode = findTwo(beginNode);
        twoNode.get(0).addLastNode(newNode);
        twoNode.get(1).addLastNode(newNode);
        newNode.setPre(twoNode.get(0), twoNode.get(1));
        newNode.updateScore();
        newNode.updateAllTotalWeight(newNode);
        System.out.println(twoNode.get(0).getWeight());
        System.out.println(twoNode.get(1).getWeight());

        return true;

    }

    public static List<Node> findBegin(Node firstNode, int count, int min, int max) {
        Set<Node> set = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        List<Node> list = new ArrayList<>();
        set.add(firstNode);
        queue.add(firstNode);
        while (!queue.isEmpty()) {
            Node a = queue.poll();
            for (Node node : a.getLastList()
            ) {
                if (!set.contains(node)) {
                    set.add(node);
                    queue.offer(node);
                    if (node.getScore() >= min && node.getScore() <= max) {
                        list.add(node);
                        count--;
                        if (count <= 0) {
                            return list;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static List<Node> findTwo(List<Node> begin) {
        Map<Node, Long> map = new HashMap<>();
        for (int i = 0; i < BEGIN_NUMBER; i++) {
            long be = System.currentTimeMillis();
            Node node = findNewest(begin.get(i));
            long ed = System.currentTimeMillis();
            if (node.getScore() < MIN_SOCRE) {
                break;
            }
            ed = ed - be;
            if (map.containsKey(node)) {
                map.put(node, map.get(node) < ed ? map.get(node) : ed);
            } else {
                map.put(node, ed);
            }
        }
        List<Node> ls = new ArrayList<>();
        map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> ls.add(e.getKey()));
        return ls.subList(0, 2);
    }

    public static Node findNewest(Node node) {
        Random random = new Random();
        while (!node.getLastList().isEmpty()) {
            List<Node> list = node.getLastList();
            int judgeNumber = random.nextInt(node.getTotalWeight() - node.getWeight());
            for (Node no : list
            ) {
                judgeNumber -= no.getWeight();
                if (judgeNumber < 0) {
                    node = no;
                    break;
                }
            }
        }
        return node;
    }
}
