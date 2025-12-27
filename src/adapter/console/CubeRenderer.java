package adapter.console;

import domain.cube.*;

public final class CubeRenderer {

    // Set to false if your console doesn't support ANSI.
    private final boolean ansiEnabled;

    public CubeRenderer() {
        this(true);
    }

    public CubeRenderer(boolean ansiEnabled) {
        this.ansiEnabled = ansiEnabled;
    }

    public String render(Cube c) {
        StringBuilder sb = new StringBuilder();

        // Optional title line
        sb.append("\n");

        // U
        sb.append(faceRow(c, Face.U, 0, "            ")).append('\n');
        sb.append(faceRow(c, Face.U, 3, "            ")).append('\n');
        sb.append(faceRow(c, Face.U, 6, "            ")).append('\n');
        sb.append('\n');

        // L F R B
        for (int r = 0; r < 3; r++) {
            sb.append(faceRow(c, Face.L, r * 3, ""))
                    .append("    ")
                    .append(faceRow(c, Face.F, r * 3, ""))
                    .append("    ")
                    .append(faceRow(c, Face.R, r * 3, ""))
                    .append("    ")
                    .append(faceRow(c, Face.B, r * 3, ""))
                    .append('\n');
        }

        sb.append('\n');

        // D
        sb.append(faceRow(c, Face.D, 0, "            ")).append('\n');
        sb.append(faceRow(c, Face.D, 3, "            ")).append('\n');
        sb.append(faceRow(c, Face.D, 6, "            ")).append('\n');

        // Reset ANSI at the end
        if (ansiEnabled) sb.append(Ansi.RESET);

        return sb.toString();
    }

    private String faceRow(Cube c, Face f, int start, String pad) {
        Color[] a = c.face(f);

        return pad
                + sticker(a[start])     + " " + sticker(a[start + 1]) + " " + sticker(a[start + 2]);
    }

    private String sticker(Color color) {
        // Make tiles look like squares using two blocks.
        String tile = "██";

        if (!ansiEnabled) {
            // Fallback: just letters if ANSI is off
            return color.name();
        }

        // Colored background tiles
        return Ansi.bg(color) + tile + Ansi.RESET;
    }

    // Minimal ANSI helper (kept inside renderer to avoid leaking into domain)
    private static final class Ansi {
        private static final String RESET = "\u001B[0m";

        private static String bg(Color c) {
            // Background colors (approximate cube colors)
            // Note: orange is approximated with yellow/red depending on terminal.
            return switch (c) {
                case W -> "\u001B[47m"; // white bg
                case Y -> "\u001B[43m"; // yellow bg
                case R -> "\u001B[41m"; // red bg
                case O -> "\u001B[48;5;208m"; // orange (256-color). If not supported, replace with 43 or 41
                case B -> "\u001B[44m"; // blue bg
                case G -> "\u001B[42m"; // green bg
            };
        }
    }
}
