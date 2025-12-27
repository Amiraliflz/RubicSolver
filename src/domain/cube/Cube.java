package domain.cube;
import java.util.*;

public final class Cube {
    private final Color[][] faces = new Color [6][9];

    public Cube() {resetSolved();}
    public Cube copy ()
    {
        Cube c = new Cube();
        for(int i = 0; i < 6; i++)
            System.arraycopy(this.faces[i], 0, c.faces[i], 0, 9);
        return c;
    }
    public void resetSolved()
    {
        fillFace(Face.U, Color.W);
        fillFace(Face.D, Color.Y);
        fillFace(Face.F, Color.G);
        fillFace(Face.B, Color.B);
        fillFace(Face.L, Color.O);
        fillFace(Face.R, Color.R);
    }
    private void fillFace(Face face, Color color)
    {
        int idx = faceIndex(face);
        Arrays.fill(this.faces[idx], color);
    }
    public Color[] face(Face face) {
        return faces[faceIndex(face)];
    }
    public boolean isSolved()
    {
        for(Face f : Face.values())
        {
            Color[] a = face(f);
            Color center = a [4];
            for(Color x : a ) if (x!= center)
                return false;
        }
        return true;
    }
    public void apply(List<Move> moves)
    {
        for(Move m : moves)
        { apply(m);}
    }

    public void apply(Move m) {
        for (int i = 0; i < m.turns(); i++) rotateFaceCW(m.face());
    }

    private void rotateFaceCW(Face f) {
        rotateFaceStickersCW(f);
        rotateSideRingsCW(f);
    }
    private void rotateFaceStickersCW(Face f) {
        Color[] a = face(f);

        cycle(a, 0, 6, 8, 2);
        cycle(a, 1, 3, 7, 5);
    }

    private void rotateSideRingsCW(Face f) {

        switch (f) {
            case U -> cycleStrips(
                    Face.B, new int[]{0,1,2},
                    Face.R, new int[]{0,1,2},
                    Face.F, new int[]{0,1,2},
                    Face.L, new int[]{0,1,2}
            );
            case D -> cycleStrips(
                    Face.F, new int[]{6,7,8},
                    Face.R, new int[]{6,7,8},
                    Face.B, new int[]{6,7,8},
                    Face.L, new int[]{6,7,8}
            );
            case F -> cycleStrips(
                    Face.U, new int[]{6,7,8},
                    Face.R, new int[]{0,3,6},
                    Face.D, new int[]{2,1,0},
                    Face.L, new int[]{8,5,2}
            );
            case B -> cycleStrips(
                    Face.U, new int[]{2,1,0},
                    Face.L, new int[]{0,3,6},
                    Face.D, new int[]{6,7,8},
                    Face.R, new int[]{8,5,2}
            );
            case L -> cycleStrips(
                    Face.U, new int[]{0,3,6},
                    Face.F, new int[]{0,3,6},
                    Face.D, new int[]{0,3,6},
                    Face.B, new int[]{8,5,2}
            );
            case R -> cycleStrips(
                    Face.U, new int[]{8,5,2},
                    Face.B, new int[]{0,3,6},
                    Face.D, new int[]{8,5,2},
                    Face.F, new int[]{8,5,2}
            );
        }
    }

    private void cycleStrips(Face f1, int[] i1, Face f2, int[] i2, Face f3, int[] i3, Face f4, int[] i4) {
        Color[] a1 = face(f1); Color[] a2 = face(f2); Color[] a3 = face(f3); Color[] a4 = face(f4);

        Color t0 = a1[i1[0]], t1 = a1[i1[1]], t2 = a1[i1[2]];
        a1[i1[0]] = a4[i4[0]]; a1[i1[1]] = a4[i4[1]]; a1[i1[2]] = a4[i4[2]];
        a4[i4[0]] = a3[i3[0]]; a4[i4[1]] = a3[i3[1]]; a4[i4[2]] = a3[i3[2]];
        a3[i3[0]] = a2[i2[0]]; a3[i3[1]] = a2[i2[1]]; a3[i3[2]] = a2[i2[2]];
        a2[i2[0]] = t0;        a2[i2[1]] = t1;        a2[i2[2]] = t2;
    }

    private void cycle(Color[] a, int i, int j, int k, int l) {
        Color tmp = a[i];
        a[i] = a[j];
        a[j] = a[k];
        a[k] = a[l];
        a[l] = tmp;
    }

    private int faceIndex(Face f) {
        return switch (f) {
            case U -> 0; case D -> 1; case F -> 2; case B -> 3; case L -> 4; case R -> 5;
        };
    }




}
