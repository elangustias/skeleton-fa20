package bearmaps.proj2c;

import java.util.ArrayList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private List<Vertex> solution = new ArrayList<>();
    private double weight = 0;
    private int dequeues = 0;
    private double time = 0;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

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
