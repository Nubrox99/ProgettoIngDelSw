package backtracking;

public interface Problema<P, S> {

    P primoPuntoDiScelta();

    P prossimoPuntoDiScelta(P ps);

    P ultimoPuntoDiScelta();

    S primaScelta(P ps);

    S prossimaScelta(S s);

    S ultimaScelta(P ps);

    boolean assegnabile(S scelta, P puntoDiScelta);

    void assegna(S scelta, P puntoDiScelta);

    void deassegna(S scelta, P puntoDiScelta);

    boolean ultimoPuntoDiScelta(P puntoDiScelta);

    boolean primoPuntoDiScelta(P puntoDiScelta);

    P precedentePuntoDiScelta(P puntoDiScelta);

    S ultimaSceltaAssegnataA(P puntoDiScelta);

    void scriviSoluzione(int nr_sol);

    default  void risolvi(){
        risolvi(Integer.MAX_VALUE);
    }

    default void risolvi(int maxSolution) {
        int nSol = 0;
        P ps = primoPuntoDiScelta();
        S s = primaScelta(ps);
        boolean backtrack = false, fine = false;
        do {
            // forward
            while (!backtrack && (nSol < maxSolution)) {
                if (assegnabile(s, ps)) {
                    assegna(s, ps);
                    if (ultimoPuntoDiScelta(ps)) {
                        nSol++;
                        scriviSoluzione(nSol);
                        deassegna(s, ps);
                        if (!s.equals(ultimaScelta(ps))) {
                            s = prossimaScelta(s);
                        } else {
                            backtrack = true;
                        }
                    }
                    else {
                        ps = prossimoPuntoDiScelta(ps);
                        s = primaScelta(ps);
                    }
                } else if (!s.equals(ultimaScelta(ps))) {
                    s = prossimaScelta(s);
                } else {
                    backtrack = true;
                }
            }

            // backward
            fine = primoPuntoDiScelta(ps) || nSol == maxSolution;
            while (backtrack && !fine) {
                ps = precedentePuntoDiScelta(ps);
                s = ultimaSceltaAssegnataA(ps);
                deassegna(s, ps);
                if (!s.equals(ultimaScelta(ps))) {
                    s = prossimaScelta(s);
                    backtrack = false;
                } else if (primoPuntoDiScelta(ps)) {
                    fine = true;
                }
            }
        } while (!fine);
    }// risolvi

}
