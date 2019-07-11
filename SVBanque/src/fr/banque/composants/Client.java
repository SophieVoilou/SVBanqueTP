package fr.banque.composants;

public class Client {
	// 1.1.1 Création de la classe client
	protected String nom;
	protected String prenom;
	protected int numClient;
	protected static int iD = 1;
	protected static final String GUI = "\"";
    protected static final String TAB = "\t";
    protected static final String CRLF = "\r\n";
    
    public Client () {}
	public Client(String nom, String prenom) {

		this.nom = nom;
		this.prenom = prenom;
		this.numClient = iD;
		iD++;
	}

	public int getnumClient() {
		return numClient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "Client [nom=" + nom + ", prenom=" + prenom + ", numClient=" + numClient + "]";
	}
	//2.2 Fichier XML des comptes
	public void fromXml(String xmlObject) {
        String object = xmlObject;
        object = object.replaceAll(TAB, "");
        object = object.replaceAll(GUI, "");
        object = object.replaceAll("/", "");
        object = object.replaceAll(">", "<");
        String[] lines = object.split(CRLF);

        for (String line : lines) {
            String[] content = line.split("<");
            switch (content[1]) {
                case "nom":
                    this.nom = content[2];
                    break;
                case "prenom":
                    this.prenom = content[2];
                    break;  
                case "numClient":
                 this.numClient = Integer.valueOf(content[2]);
                    break;
                
                default:
                    break; 
            }
        }
    }
}
