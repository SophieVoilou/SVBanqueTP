package fr.banque.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import fr.banque.composants.Client;
import fr.banque.composants.Compte;
import fr.banque.composants.CompteCourant;
import fr.banque.composants.CompteEpargne;


public class Main {
	// 1.1.2 Création d’un classe main pour les tests
	
	public static void main(String[] args) {
		ArrayList<Client> collectionClient = new ArrayList<>();
		ArrayList<Compte> collectionCompte = new ArrayList<>();
		Hashtable tableID = new Hashtable();
		ArrayList<Flux> tableFlux = new ArrayList<>();
	

		ajouterClient(3, collectionClient);
		afficherClient(collectionClient);
		ajouterCompte(collectionCompte, collectionClient);
		afficherCompte(collectionCompte);
		
		afficherTableID(tableID);
		ajouterFlux(tableFlux);
		afficherFlux(tableFlux);

		mouvementFlux(tableFlux, collectionCompte);
		importJson(tableFlux);
		afficherFlux(tableFlux);
		importXml(collectionCompte);
		afficherCompte(collectionCompte);
	}

	public static void ajouterClient(int x, ArrayList<Client> collectionClient) {
		for (int i = 0; i < x; i++) {
			String nomClient = "nom" + String.valueOf(i + 1);
			String prenomClient = "prenom" + String.valueOf(i + 1);
			Client client = new Client(nomClient, prenomClient);
			collectionClient.add(client);
		}
	}

	public static void afficherClient(List<Client> collectionClient) {
		String str;
		System.out.println(" Affichage des Clients");
	
		for (Client client : collectionClient) {

			

			System.out.println(client);
			
		}
	}

	// 1.2.3 Création du tableau des comptes
	public static void ajouterCompte(List<Compte> collectionCompte, List<Client> collectionClient) {

		// Boucle sur les clients

		for (Client client : collectionClient) {

			CompteCourant compteCourant = new CompteCourant("compte Courant", 0, 0, client);// les info dont solde = 0et
																							// client = client)

			CompteEpargne compteEpargne = new CompteEpargne("compte Epargne", 0, 0, client);// les info dont solde = 0et
																							// client = client)

			collectionCompte.add(compteCourant);

			collectionCompte.add(compteEpargne);

		}

	}

	public static void afficherCompte(List<Compte> collectionCompte) {
		String str;
		
		System.out.println(" Affichage des comptes ");
		for (Compte compte : collectionCompte) {

			str = compte.toString();

			System.out.println(str);

		}
		
	}

	

	public static void afficherTableID(Hashtable tableID) {
		Enumeration e = tableID.elements();
		Enumeration k = tableID.keys();
		System.out.println("Affichage de la table des identifiants");
	
		while (e.hasMoreElements())

			System.out.println("Identifiant Compte" + k.nextElement() + ":" + e.nextElement());

	}

	// 1.3.4 Création du tableau des flux
	public static List<Flux> ajouterFlux(List<Flux> tableFlux) {

		Debit debit = new Debit(null, 1, 50, 1, false, null);
		Credit credit1 = new Credit(null, 1, 100.50, 1, false, null);
		Credit credit2 = new Credit(null, 3, 100.50, 3, false, null);
		Credit credit3 = new Credit(null, 5, 100.50, 5, false, null);
		Credit credit4 = new Credit(null, 2, 1500, 2, false, null);
		Credit credit5 = new Credit(null, 4, 1500, 4, false, null);
		Credit credit6 = new Credit(null, 6, 1500, 6, false, null);
		Virement virement = new Virement(null, 1, 50, 1, false, null, 2);
		

		tableFlux.add(debit);
		tableFlux.add(credit1);
		tableFlux.add(credit2);
		tableFlux.add(credit3);
		tableFlux.add(credit4);
		tableFlux.add(credit5);
		tableFlux.add(credit6);
		tableFlux.add(virement);

		return tableFlux;

	}

	public static void afficherFlux(List<Flux> tableFlux) {
		String str = null;
		System.out.println("Affichage des Flux ");
		
		for (Flux flux : tableFlux) {

			str = flux.toString();

			System.out.println(str);

		}
	
	}

	public static void mouvementFlux(List<Flux> tableFlux, List<Compte> collectionCompte) {
		for (Flux flux : tableFlux) {
			if (flux instanceof Debit) {
				for (Compte compte : collectionCompte) {

					if (flux.getNumCompteCible() == compte.getNumCompte()) {
						compte.setSolde(-1 * flux.getMontant());
					}
				}
			}

			else if (flux instanceof Credit) {
				for (Compte compte : collectionCompte) {
					if (flux.getNumCompteCible() == compte.getNumCompte()) {
						compte.setSolde(flux.getMontant());
					}
				}
			}

			else if (flux instanceof Virement) {
				Virement virement = (Virement) flux;
				for (Compte compte : collectionCompte) {
					if (virement.getNumComEm() == compte.getNumCompte()) {
						compte.setSolde(-1 * virement.getMontant());
					}
					if (virement.getNumCompteCible() == compte.getNumCompte()) {
						compte.setSolde(virement.getMontant());
					}

				}

			}
		}
	}

	// 2.1 Fichier JSON des flux
	public static void importJson(List<Flux> tableFlux) {

		String objet = "";
		String typeObjet = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("flux.json"));
			String line = br.readLine();
			while (line != null) {
				line = line.replaceAll("\t", "");
				line = line.replaceAll(",", "");
				if ("collectionDebit:[".equals(line)) {
					objet = "";
					typeObjet = "Débit";
				} else if ("collectionCredit:[".equals(line)) {
					objet = "";
					typeObjet = "Crédit";
				} else if ("collectionVirement:[".equals(line)) {
					objet = "";
					typeObjet = "Virement";
				} else if ("]".equals(line)) {
					objet = "";
				} else if ("{".equals(line)) {
					objet = line + "\r\n";
				} else if ("}".equals(line)) {
					if (!"".equals(objet)) {
						objet += line + "\r\n";
						switch (typeObjet) {
						case "Débit":
							Debit deb = new Debit();
							deb.fromJson(objet);
							tableFlux.add(deb);
							break;
						case "Crédit":
							Credit cred = new Credit();
							cred.fromJson(objet);
							tableFlux.add(cred);
							break;
						case "Virement":
							Virement vir = new Virement();
							vir.fromJson(objet);
							tableFlux.add(vir);
							break;
						default:
						   
						    break;
						}
					}
				} else {
					objet += line + "\r\n";
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 //2.2 Fichier XML des comptes
	public static void importXml(ArrayList<Compte> collectionCompte) {

		String objet = "";

		try {

			BufferedReader br = new BufferedReader(new FileReader("compte.xml"));

			String line = br.readLine();

			while (line != null) {

				line = line.replaceAll("\t", "");

				if ("<compteCourant>".equals(line)) {

					objet = line + "\r\n";

				} else if ("<compteEpargne>".equals(line)) {

					objet = line + "\r\n";

				} else if ("</compteCourant>".equals(line)) {

					objet += line + "\r\n";

					CompteCourant comCour = new CompteCourant();

					comCour.fromXml(objet);

					collectionCompte.add(comCour);

				} else if ("</compteEpargne>".equals(line)) {

					objet += line + "\r\n";

					CompteEpargne comEpar = new CompteEpargne();

					comEpar.fromXml(objet);

					collectionCompte.add(comEpar);

				} else {

					objet += line + "\r\n";

				}

				line = br.readLine();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}