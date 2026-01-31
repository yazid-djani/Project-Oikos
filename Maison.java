public class Maison extends Objet {
    
    int nb_arbre;
    int nb_nourriture;
    MinecosysPanel world;


    public Maison(MinecosysPanel world) {
        super();
        this.world=world;
        nb_nourriture=50;
        nb_arbre=0;
    }
    
    public int getNb_arbre() {
        return nb_arbre;
    }

    public int getNb_nourriture() {
        return nb_nourriture;
    }
    
    public void utilisearbre(){
        nb_arbre--;
    }

    public void eat(){
        nb_nourriture--;
    }

    public void addarbre(int nb){
        nb_arbre=nb_arbre+nb;
    }

    public void addnourriture(int nb){
        nb_nourriture=nb_nourriture+nb;
    }
    
    public void step(){}
    
}
