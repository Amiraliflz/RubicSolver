package domain.solver;

import domain.cube.*;

import java.util.*;

public final class InverseScrambleSolver implements Solver {
    private final List<Move> scramble;

    public InverseScrambleSolver(List<Move> scramble) {
        this.scramble = List.copyOf(scramble);
    }

    @Override public String name() { return "InverseScrambleSolver"; }

    @Override
    public List<Move> solve(Cube cube) {

        List<Move> out = new ArrayList<>(scramble.size());
        for (int i = scramble.size() - 1; i >= 0; i--) out.add(scramble.get(i).inverse());
        return out;
    }
}
