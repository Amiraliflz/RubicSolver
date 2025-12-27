package domain.solver;

import domain.cube.Cube;

import domain.cube.Move;

import java.util.List;

public interface Solver {
    List<Move> solve(Cube cube);
    String name();
}
