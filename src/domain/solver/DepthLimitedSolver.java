package domain.solver;

import domain.cube.*;

import java.util.*;

public final class DepthLimitedSolver implements Solver {
    private final int maxDepth;

    public DepthLimitedSolver(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override public String name() { return "DepthLimitedSolver(depth=" + maxDepth + ")"; }

    @Override
    public List<Move> solve(Cube cube) {
        if (cube.isSolved()) return List.of();

        List<Move> path = new ArrayList<>();
        if (dfs(cube, 0, maxDepth, null, path)) return path;
        return List.of(); // empty means not found within depth
    }

    private boolean dfs(Cube cube, int depth, int limit, Face lastFace, List<Move> path) {
        if (cube.isSolved()) return true;
        if (depth == limit) return false;

        for (Face f : Face.values()) {
            // simple pruning: avoid repeating same face back-to-back
            if (lastFace != null && f == lastFace) continue;

            for (int turns = 1; turns <= 3; turns++) {
                Move m = new Move(f, turns);
                Cube next = cube.copy();
                next.apply(m);

                path.add(m);
                if (dfs(next, depth + 1, limit, f, path)) return true;
                path.remove(path.size() - 1);
            }
        }
        return false;
    }
}
