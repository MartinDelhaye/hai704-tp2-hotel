package hai704i.tp2.hotel.outil;

import hai704i.tp2.hotel.metier.securite.Hachage;

public class GenererEmpreinte {
    public static void main(String[] args) {

        System.out.println(Hachage.versTexte(Hachage.deriver(args[0])));
    }
}