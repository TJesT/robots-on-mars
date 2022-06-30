package engine.robot.mode;

import engine.robot.AbstractRobot;
import engine.robot.exception.RobotException;
import engine.util.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Auto extends AbstractMode {
    private final LinkedList<Action> taskQueue;

    public Auto(AbstractRobot robot) {
        super(robot);
        this.type = ModeType.AUTO;
        this.taskQueue = new LinkedList<>();
        /* TODO: analyze explored map and create a task queue
                  to collect APPLEs.
         *       queue should contain only MOVEs and GRABs. */
        this.bfs();
        System.out.println(taskQueue);
    }

    private void bfs() {
        class NodeWrapper {
            public Node node;
            public Action action;
            public NodeWrapper next;
            public NodeWrapper prev;

            public NodeWrapper(Node node, Action action, NodeWrapper next, NodeWrapper prev) {
                this.next = next;
                this.prev = prev;
                this.action = action;
                this.node = node;
            }

            public NodeWrapper(NodeWrapper nw) {
                this.node = nw.node;
                this.next = nw.next;
                this.prev = nw.prev;
                this.action = nw.action;
            }
        }

        Set<Node> visitedNodes = new HashSet<>();
        LinkedList<NodeWrapper> targetList = new LinkedList<>();

        Node startNode = this.worker.getPosition();
        visitedNodes.add(startNode);

        for (Direction direction : Direction.Collection()) {
            targetList.add(new NodeWrapper(startNode,
                    new Action(ActionType.MOVE, direction), null, null));
        }

        while(!targetList.isEmpty()) {
            NodeWrapper curNW = targetList.poll();

            Node curNode = curNW.node;
//            System.out.println(curNode);

            Node adjNode = curNode.getNeighbour(curNW.action.direction);

            if (adjNode != null && adjNode.block != null &&
                    adjNode.block.item != null && adjNode.block.item.getType() == ItemType.APPLE) {
//                System.out.println("@");
                NodeWrapper currentNode = new NodeWrapper(curNW);
                currentNode.next = new NodeWrapper(currentNode.node,
                        new Action(ActionType.GRAB, null), null, currentNode);
                while(currentNode.prev != null) {
                    currentNode.prev.next = currentNode;
                    currentNode = new NodeWrapper(currentNode.prev);
                }
                for (; currentNode.next != null; currentNode = currentNode.next) {
                    this.taskQueue.add(currentNode.action);
                }
                return;
            }

            for (Direction direction :
                    Direction.Collection()) {
                if (direction.inverse() == curNW.action.direction) {
                    continue;
                }

                if (adjNode != null && !visitedNodes.contains(adjNode)) {
                    NodeWrapper nextNW = new NodeWrapper(
                            adjNode, new Action(ActionType.MOVE, direction), null, curNW);
//                    System.out.println('#');
                    targetList.add(nextNW);
                    visitedNodes.add(adjNode);
                }
            }
        }
    }

    @Override
    public void step(Action action) throws RobotException {
        if (this.taskQueue.isEmpty()) return;

        this.manualStep(this.taskQueue.poll());
    }
}
