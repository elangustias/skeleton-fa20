package bearmaps.proj2c;

import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private List<Vertex> solution = new ArrayList<>();
    private double weight = 0;
    private int dequeues = 0;
    private double time = 0;
    private HashMap<Vertex, Double> distTo = new HashMap<>();
    private HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
    private ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        distTo.put(start, 0.0);
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        while (true) {
            if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                break;
            }
            if (fringe.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
                break;
            }
            Vertex curr = fringe.removeSmallest();
            dequeues = dequeues + 1;
            if (curr.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                createSol(end, start);
                weight = distTo.get(end);
                break;
                /** Would this create a bug? If theoretically a vertex can be dequeued and requeued over and
                 *  over again, then shouldn't I run the solver until the fringe is empty to make sure I have
                 *  the best possible path to the goal vertex?
                 */
            }
            relax(curr, end, input);
        }
        time = sw.elapsedTime();
    }
    private void relax(Vertex v, Vertex end, AStarGraph<Vertex> g) {
        List<WeightedEdge<Vertex>> neighbors = g.neighbors(v);
        for (WeightedEdge<Vertex> edge : neighbors) {
            // Code is broken. Do I need to make sure everything that has been dequeued no longer is?
            // Or maybe avoid checking where you just came from to avoid huge loops?
            Vertex n = edge.to();
            double dist = distTo.get(v) + edge.weight();
            if (!distTo.containsKey(n) || dist < distTo.get(n)) {
                distTo.put(n, dist);
                edgeTo.put(n, v);
                double estDist = g.estimatedDistanceToGoal(n, end);
                if (!fringe.contains(n)) {
                    fringe.add(n, dist + estDist);
                } else {
                    fringe.changePriority(n, dist + estDist);
                }
            }
        }
    }
    private void createSol(Vertex end, Vertex start) {
        Vertex curr = end;
        solution.add(0, start);
        while (!curr.equals(start)) {
            solution.add(1, curr);
            curr = edgeTo.get(curr);
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        return weight;
    }
    public int numStatesExplored() {
        return dequeues;
    }
    public double explorationTime() {
        return time;
    }
}
