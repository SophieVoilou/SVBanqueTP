package fr.banque.composants;

//1.2.1 Création de la classe Compte
public abstract class Compte {
	protected String libelle;
	protected double solde;
	protected int numCompte;
	protected Client client;
	protected static int iD = 1;
	protected static final String GUI = "\"";
    protected static final String TAB = "\t";
    protected static final String CRLF = "\r\n";
    
    public Compte() {}

	public Compte(String libelle, double solde, int numCompte, Client client) {
		super();
		this.libelle = libelle;
		this.solde = solde;
		this.numCompte = iD;
		iD++;
		this.client = client;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double montant) {

		this.solde += montant;

		}

	public int getNumCompte() {
		return numCompte;
	}

	public void setNumCompte(int numCompte) {
		this.numCompte = numCompte;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public static int getID() {
		return iD;
	}

	public  void setID(int iD) {
		this.iD = iD;
	}

	
	@Override
	public String toString() {
		return "Compte [Libelle=" + libelle + ", Solde=" + solde + ", NumCompte=" + numCompte + ", client=" + client
				+ "]";
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
                case "libelle":
                    this.libelle = content[2];
                    break;
                case "solde":
                    this.solde = Double.valueOf(content[2]);
                    break;
                
                    
                case "numCompte":
                    this.numCompte = Integer.valueOf(content[2]);
                    break;
                case "client":
                    Client cli = new Client();
                    cli.fromXml(object);
                    this.client=cli;
                    break;
                default:
                    break;
            }
        }
    }
}
