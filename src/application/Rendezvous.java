package application;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rendezvous {
	private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String sexe;
    private LocalDate dateNaissance;
    private LocalDate dateRendezVous;
    private LocalTime startTime;
    private LocalTime endtime;
    public Rendezvous() {
        // no-arg constructor
    }
    public Rendezvous(String nom, String prenom, String adresse, String email, String sexe, 
            LocalDate dateNaissance, LocalDate dateRendezVous, LocalTime startTime,LocalTime endtime) {
this.nom = nom;
this.prenom = prenom;
this.adresse = adresse;
this.email = email;
this.sexe = sexe;
this.dateNaissance = dateNaissance;
this.dateRendezVous = dateRendezVous;
this.startTime = startTime;
this.endtime = endtime;
}
    public LocalTime getEndtime() {
		return endtime;
	}
	public void setEndtime(LocalTime endtime) {
		this.endtime = endtime;
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
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public LocalDate getDateRendezVous() {
		return dateRendezVous;
	}
	public void setDateRendezVous(LocalDate dateRendezVous) {
		this.dateRendezVous = dateRendezVous;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endtime;
	}
	public void setEndTime(LocalTime endtime) {
		this.endtime = endtime;
	}
	

}
