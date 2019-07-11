package fr.banque.main;
import java.io.Serializable;
//1.3.2 Création de la classe Flux
import java.util.Date;

public abstract class Flux  implements Serializable{
    
    protected static final String GUI = "\"";
    protected static final String TAB = "\t";
    protected static final String CRLF = "\r\n";
    
	@Override
	public String toString() {
		return "Flux [Commentaire=" + commentaire + ", Identifiant=" + identifiant + ", Montant=" + montant
				+ ", NumCompteCible=" + numCompteCible + ", Effectue=" + effectue + ", dateFlux=" + dateFlux + "]";
	}

	protected String commentaire;
	protected int identifiant;
	protected double montant;
	protected int numCompteCible;
	protected boolean effectue;
	protected Date dateFlux = new Date(); // import java.util.Date

    public Flux() {    
    }

	public Flux(String commentaire, int identifiant, double montant, int numCompteCible, boolean effectue,
			Date dateFlux) {
		super();
		this.commentaire = commentaire;
		this.identifiant = identifiant;
		this.montant = montant;
		this.numCompteCible = numCompteCible;
		this.effectue = effectue;
		this.dateFlux = dateFlux;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public double getNumCompteCible() {
		return numCompteCible;
	}

	public void setNumCompteCible(int numCompteCible) {
		this.numCompteCible = numCompteCible;
	}

	public boolean isEffectue() {
		return effectue;
	}

	public void setEffectue(boolean effectue) {
		this.effectue = effectue;
	}

	public Date getDateFlux() {
		return dateFlux;
	}

	public void setDateFlux(Date dateFlux) {
		this.dateFlux = dateFlux;
	}
	// 2.1 Fichier JSON des flux        
        

        public void fromJson(String jsonObject) {
            String object = jsonObject;
            object = object.replaceAll(TAB, "");
            object = object.replaceAll(GUI, "");
            object = object.replaceAll(",", "");
            String[] lines = object.split(CRLF);

            for (String line : lines) {
                if ("{".equals(line)) {

                } else if ("}".equals(line)) {

                } else {
                    String[] content = line.split(":");
                    switch (content[0]) {
                        case "commentaire":
                            this.commentaire = content[1];
                            break;
                        case "identifiant":
                            this.identifiant = Integer.valueOf(content[1]);
                            break;
                        case "montant":
                            this.montant = Double.valueOf(content[1]);
                            break;
                        case "numCompteCible":
                            this.numCompteCible = Integer.valueOf(content[1]);
                            break;
                        case "effectue":
                            this.effectue = Boolean.valueOf(content[1]);
                            break;
                        case "dateFlux":
                            this.dateFlux = new Date(content[1]); //Need work
                            break;
                        default:
                            break;
                    }
                }
            }
        }
                
        
                
        


}