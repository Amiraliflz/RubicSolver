package app;

import adapter.console.*;
import domain.cube.*;
import domain.solver.*;

import java.util.*;

public final class Main {
    public static void main(String[] args) {
        ConsoleIO io = new ConsoleIO();
        CubeRenderer renderer = new CubeRenderer();

        Cube cube = new Cube();

        io.println("3x3 Rubik Console");
        io.println("Commands:");
        io.println("  scramble <moves>   e.g. scramble R U R' U'");
        io.println("  apply <moves>      apply moves to current cube");
        io.println("  solve-inverse      solve by inverting last scramble");
        io.println("  solve-depth <n>    brute force up to depth n (small n like 6..9)");
        io.println("  reset");
        io.println("  show");
        io.println("  exit");

        List<Move> lastScramble = List.of();
        io.println(renderer.render(cube));

        while (true) {
            String line = io.readLine("> ").trim();
            if (line.isEmpty()) continue;
            if (line.equalsIgnoreCase("exit")) break;

            try {
                if (line.equalsIgnoreCase("show")) {
                    io.println(renderer.render(cube));
                    continue;
                }
                if (line.equalsIgnoreCase("reset")) {
                    cube.resetSolved();
                    lastScramble = List.of();
                    io.println(renderer.render(cube));
                    continue;
                }

                if (line.startsWith("scramble ")) {
                    String alg = line.substring("scramble ".length());
                    List<Move> moves = Move.parseAlgorithm(alg);
                    cube.resetSolved();
                    cube.apply(moves);
                    lastScramble = moves;
                    io.println("Scrambled.");
                    io.println(renderer.render(cube));
                    continue;
                }

                if (line.startsWith("apply ")) {
                    String alg = line.substring("apply ".length());
                    List<Move> moves = Move.parseAlgorithm(alg);
                    cube.apply(moves);
                    io.println(renderer.render(cube));
                    continue;
                }

                if (line.equalsIgnoreCase("solve-inverse")) {
                    if (lastScramble.isEmpty()) {
                        io.println("No scramble recorded. Use: scramble <moves>");
                        continue;
                    }
                    Solver solver = new InverseScrambleSolver(lastScramble);
                    List<Move> solution = solver.solve(cube);
                    io.println("Solver: " + solver.name());
                    io.println("Solution: " + Move.format(solution));

                    cube.apply(solution);
                    io.println("After solution:");
                    io.println(renderer.render(cube));
                    io.println("Solved? " + cube.isSolved());
                    continue;
                }

                if (line.startsWith("solve-depth ")) {
                    int n = Integer.parseInt(line.substring("solve-depth ".length()).trim());
                    Solver solver = new DepthLimitedSolver(n);
                    List<Move> sol = solver.solve(cube);
                    io.println("Solver: " + solver.name());
                    if (sol.isEmpty()) {
                        io.println("No solution found within depth " + n + ".");
                    } else {
                        io.println("Solution: " + Move.format(sol));
                        cube.apply(sol);
                        io.println(renderer.render(cube));
                        io.println("Solved? " + cube.isSolved());
                    }
                    continue;
                }

                io.println("Unknown command. Type show/reset/scramble/apply/solve-inverse/solve-depth/exit");
            } catch (Exception ex) {
                io.println("Error: " + ex.getMessage());
            }
        }
    }
}
