
package domain.cube;
import java.util.*;

public final class Move{
    private final Face face;
    private  final int turns;


    public Move(Face face, int turns){
        if(turns<1 || turns>3){
            throw new IllegalArgumentException("turns must be between 1 and 3");}
        this.face = Objects.requireNonNull(face);
        this.turns = turns;

    }

    public Face face(){return face;}

    public int turns(){return turns;}

    public Move inverse()
    {
        return new Move(face,turns()==2?2:(4-turns));
    }

    @Override public String toString()
    {
        return switch (turns)
        {
            case 1 -> face.name();
            case 2 -> face.name()+"2";
            case 3 -> face.name()+"'";
            default -> throw new IllegalStateException("");
        };
    }


    public static List<Move> parseAlgorithm(String alg){
        if (alg== null || alg.trim().isEmpty())
            return List.of();
        String[] tokens = alg.trim().split("\\s+");
        List<Move> moves = new ArrayList<>();
        for(String t : tokens)
        {
            moves.add(parseToken(t));
        }
        return moves;
    }
    public static String format(List<Move> moves){
        return  moves.stream().map(Move::toString).reduce((a,b)->a+" "+b).orElse("");

    }
    private static Move parseToken(String t){
        t = t.trim();
        if (t.isEmpty())
            throw new IllegalArgumentException("token is empty");
        char f = t.charAt(0);
        Face face = switch (f)
        {
         case 'U' ->Face .U;
         case 'D' ->Face.D;
         case 'F' ->Face.F;
         case 'B' ->Face.B;
         case 'L' ->Face.L;
         case 'R' ->Face.R;
         default -> throw new IllegalStateException("Unknow face :"+f);

        };
        int turns = 1;
        if(t.length()>1){
            char s = t.charAt(1);
            if(s=='2')
                turns =2 ;
            else if (s == '\'')
                turns = 3;
            else
                throw new IllegalStateException("Bad Suffix :"+t);
        }
        if(t.length()>2){
            throw new IllegalStateException("Bad move token :"+t);
        }
        return new Move(face,turns);
    }


}
