package fil.adom;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TP5 {

    public static String mainDir = "./files/generated/FrontScalaire/";
    
    /*
        Instructions du TP 5 :
            On va réaliser le front_scalaire.
    */

    public static void main(String[] args) {
        //Création des matrices.
        var matriceA = TP1.createMatrixFromFile("A");
        var matriceB = TP1.createMatrixFromFile("B");
        // Initialisation du front_scalaire 
        //On va définir les poids à répartir sur le couple de valeur.
        ArrayList<Double> poids = new ArrayList(List.of(0.0,0.05,0.10,0.15,0.20,0.25,0.30,0.35,0.40,0.45,0.50,0.55,0.60,0.65,0.70,0.75,0.80,0.85,0.90,0.95,1.0));  
        Integer[] sequence = generateListof100().toArray(new Integer[0]); //Génère une liste aléatoire de ville
        List<Sequence> front_scalaire = frontScalaire(sequence,poids,matriceA,matriceB);
        System.out.println("AB : \n");
        // On va afficher les 
        for (Sequence sequence2 : front_scalaire) {
            System.out.println(sequence2.getRates());
        }
        writeInfo(front_scalaire, "AB");
        System.out.println("CD : \n");
        var matriceC = TP1.createMatrixFromFile("C");
        var matriceD = TP1.createMatrixFromFile("D");
        List<Sequence> front_scalaire2 = frontScalaire(sequence,poids,matriceC,matriceD);

        for (Sequence sequence2 : front_scalaire2) {
            System.out.println(sequence2.getRates());
        }
        writeInfo(front_scalaire2, "CD");
        System.out.println("EF : \n");
        var matriceE = TP1.createMatrixFromFile("E");
        var matriceF = TP1.createMatrixFromFile("F");
        List<Sequence> front_scalaire3 = frontScalaire(sequence,poids,matriceE,matriceF);

        for (Sequence sequence2 : front_scalaire3) {
            System.out.println(sequence2.getRates());
        }
        writeInfo(front_scalaire3, "EF");

    }
    /*
        On va utiliser cette fonction pour générer 100 une liste aléatoire de 100 nombre
    */
    public static ArrayList<Integer> generateListof100(){
        ArrayList<Integer> listof100 = new ArrayList<>();
        for (int i = 1; i < 100 ; i++){
            listof100.add(i);
        }
        Collections.shuffle(listof100);
        return listof100;
    }

    /*
        Evaluer les coûts entre les deux tableaux et créer une nouvelle séquence.
    */
    public static Sequence evaluate_front(int[][] matriceA,int[][] matriceB,Integer[] seq){
        ArrayList<Integer> res = new ArrayList<>(2);
        res.add(0,cost_seq(seq, matriceA));
        res.add(1,cost_seq(seq, matriceB));
        return new Sequence(seq, res);
    }

    /*
        Calculer les coût entre les contenus d'une sequence par rapport à une matrice
    */
    public static int cost_seq( Integer[] seq, int[][] matrice){
        int eval = 0; // Par défaut 0
        for ( int src = 0 ; src < seq.length-1 ; src++){
            int citySrc = seq[src];
            int cityDst = seq[src+1];
            eval += matrice[citySrc][cityDst];
        }
        int citySrc = seq[0];
        int cityDst = seq[seq.length-1];
        eval+=matrice[citySrc][cityDst];
        return eval;
    }

    public static List<Sequence> frontScalaire (Integer[] firstSeq, ArrayList<Double> poids,int[][] matriceA,int[][] matriceB){
        // Initialise une liste pour récupérer le front scalaire des fronts
        ArrayList<Sequence> front_Seqs = new ArrayList<>();
        // On va évaluer une première fois la génération aléatoire des deux matrices
        Sequence frontScal = evaluate_front(matriceA, matriceB, firstSeq);
        for (double unPoids : poids){
            //On initialise les deux variables qui vont tourner dans les boucles
            Sequence sequence_actuelle = frontScal; // La sequence qui va tourner dans la boucle
            Sequence meilleures_sequence = frontScal; ///Les meilleurs_séquences qu'on va retenir 
            //calcul du score pour compager les score par la suite.
            double le_score = (sequence_actuelle.getRates().get(0) * unPoids) + (sequence_actuelle.getRates().get(1) * (1.0 - unPoids)); 
            boolean actualisation = true; //Par defaut on est à true
            while(actualisation){
                sequence_actuelle = meilleures_sequence; //on va constamment évaluer les meilleurs séquences
                actualisation=false;
                for ( int i = 0; i < firstSeq.length;i++){
                    for ( int j = i+1; j < firstSeq.length;j++ ){
                        Integer[] sequenceEvalue = TP2_TwoOpt.twoOpt(sequence_actuelle.getSeqs(), i, j); // On va effectuer un two-opt
                        Sequence sequence_unitaire = evaluate_front(matriceA, matriceB, sequenceEvalue); //on evalue le score du meilleur voisin
                        double scoreEvalue = (sequence_unitaire.getRates().get(0) * unPoids) + (sequence_unitaire.getRates().get(1) * (1.0 - unPoids)); // on y effectue une somme pondérée sur les critères 
                        if( le_score > scoreEvalue ){ //On continue si on trouve un meilleur voisin / sinon on passe
                            actualisation = true;
                            le_score = scoreEvalue;
                            meilleures_sequence = sequence_unitaire;
                        }
                    }
                }
            }
            //On doit faire quelque chose je pense...
            front_Seqs.add(meilleures_sequence);
        }
        //On doit possiblement faire une revérification si les meilleures données sont stockés et qu'elle 
        //peuvent possiblement être dominées
        return front_Seqs;
    }

    public static void writeInfo(List<Sequence> sequence, String instance) {
        try {
            var f = new File(mainDir + instance + ".tsp");
            var writer = new BufferedWriter(new FileWriter(f));
            for (var sequenced:  sequence) {
                writer.write(sequenced.getRates().toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

final class Sequence {

    private Integer[] seqs;
    private ArrayList<Integer> rates;

    public Sequence(Integer[] seq,ArrayList<Integer> rates){
        this.rates=rates;
        this.seqs=seq;
    }

    public void setRates(ArrayList<Integer> rates) {
        this.rates = rates;
    }
    public void setSeqs(Integer[] seqs) {
        this.seqs = seqs;
    }
    public ArrayList<Integer> getRates() {
        return rates;
    }
    public Integer[] getSeqs() {
        return seqs;
    }
    @Override
    public String toString() {
        return "rates : "+ rates +"\n" ;
    }
}