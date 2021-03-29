package com.tutorat;

import com.tutorat.dto.request.*;
import com.tutorat.model.*;
import com.tutorat.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TutoratApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutoratApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UtilisateurService userService, AnneeService anService, NiveauService nivService,
							SerieService serieService, TypeEncadrementService typeEncadrementService,
							ApprenantService apprenantService, ModeEncadrementService modeEncadrementService){
		return args -> {
			//	Ajout d'un utilisateur
			UtilisateurRequest uReq = new UtilisateurRequest();
			uReq.setPrenoms("User 1"); uReq.setNom("U1"); uReq.setDateNaissance("08/05/1960");
			uReq.setLieuNaissance("Lieu naiss U1"); uReq.setSexe("C"); uReq.setAdresse("Adresse U1");
			uReq.setTelephone("771230001"); uReq.setEmail("user1@gmail.com"); uReq.setUsername("user1");
			uReq.setMdpasse("P@sser123"); uReq.setSituationFamiliale("C");
			Utilisateur user_app = userService.add(uReq);

			UtilisateurRequest tuteurReq = new UtilisateurRequest();
			tuteurReq.setPrenoms("Tuteur"); tuteurReq.setNom("TUT"); tuteurReq.setDateNaissance("12/10/1970");
			tuteurReq.setLieuNaissance("Lieu naiss Tut"); tuteurReq.setSexe("M"); tuteurReq.setAdresse("Adresse Tut");
			tuteurReq.setTelephone("771230002"); tuteurReq.setEmail("tuteur@gmail.com");
			tuteurReq.setUsername("tuteur"); tuteurReq.setMdpasse("P@sser123"); tuteurReq.setSituationFamiliale("M");
			Utilisateur user_tut = userService.add(tuteurReq);

			UtilisateurRequest ensReq = new UtilisateurRequest();
			ensReq.setPrenoms("Enseignant"); ensReq.setNom("ENS"); ensReq.setDateNaissance("20/12/1989"); ensReq.setLieuNaissance("Lieu naiss Ens");
			ensReq.setSexe("M"); ensReq.setAdresse("Adresse Ens"); ensReq.setTelephone("771230003"); ensReq.setEmail("enseignant@gmail.com");
			ensReq.setUsername("enseignant"); ensReq.setMdpasse("P@sser123");
			ensReq.setSituationFamiliale("C");
			Utilisateur user_ens = userService.add(ensReq);

			UtilisateurRequest uApprenantReq = new UtilisateurRequest();
			uApprenantReq.setPrenoms("Apprenant"); uApprenantReq.setNom("APPREN"); uApprenantReq.setDateNaissance("02/07/2003");
			uApprenantReq.setLieuNaissance("Lieu naiss Appren"); uApprenantReq.setSexe("F"); uApprenantReq.setAdresse("Adresse Apprenant");
			uApprenantReq.setTelephone("771230004"); uApprenantReq.setEmail("apprenant@gmail.com"); uApprenantReq.setUsername("apprenant");
			uApprenantReq.setMdpasse("P@sser123"); uApprenantReq.setSituationFamiliale("C");
			Utilisateur user_apprenant = userService.add(uApprenantReq);

			//	Ajout d'une année
			AnneeRequest a2019Req = new AnneeRequest();
			a2019Req.setLibelle("2019-2020"); a2019Req.setSigle("2019");
			Annee an2019 = anService.add(a2019Req);

			AnneeRequest a2020Req = new AnneeRequest();
			a2020Req.setLibelle("2020-2021"); a2020Req.setSigle("2020");
			Annee an2020 = anService.add(a2020Req);

			AnneeRequest a2021Req = new AnneeRequest();
			a2021Req.setLibelle("2021-2022"); a2021Req.setSigle("2021");
			Annee an2021 = anService.add(a2021Req);

			//	$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			//	Ajout des niveau
			NiveauRequest tleReq = new NiveauRequest();
			tleReq.setLibelle("Terminale"); tleReq.setSigle("Tle");
			Niveau terminale = nivService.add(tleReq);

			NiveauRequest premReq = new NiveauRequest();
			premReq.setLibelle("Premiére"); premReq.setSigle("1ere");
			Niveau premiere = nivService.add(premReq);

			NiveauRequest secReq = new NiveauRequest();
			secReq.setLibelle("Seconde"); secReq.setSigle("2nde");
			Niveau seconde = nivService.add(secReq);

			//	$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			//	Ajout des series
			SerieRequest s2Req = new SerieRequest();
			s2Req.setLibelle("Sciences expérimentales"); s2Req.setSigle("S2");
			Serie s2 = serieService.add(s2Req);

			SerieRequest s1Req = new SerieRequest();
			s1Req.setLibelle("Sciences mathématiques"); s1Req.setSigle("S1");
			Serie s1 = serieService.add(s1Req);

			SerieRequest l1Req = new SerieRequest();
			l1Req.setLibelle("Langues et littérature"); l1Req.setSigle("L1");
			Serie l1 = serieService.add(l1Req);

			//	$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			//	Ajout d'un apprenant
			ApprenantRequest apprenReq1 = new ApprenantRequest();
			apprenReq1.setAnnee("2"); apprenReq1.setNiveau("1"); apprenReq1.setSerie("1");
			apprenReq1.setUtilisateur("4");
			Apprenant apprenant1 = apprenantService.add(apprenReq1);
			
			//	$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			//	Ajout type d'encadrement
			TypeEncadrementRequest a1Req = new TypeEncadrementRequest();
			a1Req.setLibelle("Individuel"); a1Req.setSigle("A 1");
			TypeEncadrement indiv = typeEncadrementService.add(a1Req);

			TypeEncadrementRequest a2Req = new TypeEncadrementRequest();
			a2Req.setLibelle("A deux"); a2Req.setSigle("A 2");
			TypeEncadrement aDeux = typeEncadrementService.add(a2Req);

			TypeEncadrementRequest a3Req = new TypeEncadrementRequest();
			a3Req.setLibelle("A trois"); a3Req.setSigle("A 3");
			TypeEncadrement aTrois = typeEncadrementService.add(a3Req);

			//	$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			//	Ajout mode encadrement
			ModeEncadrementRequest domicileReq = new ModeEncadrementRequest();
			domicileReq.setLibelle("A Domicile"); domicileReq.setSigle("A DOMICILE");
			ModeEncadrement aDomicile = modeEncadrementService.add(domicileReq);

			ModeEncadrementRequest enLigneReq = new ModeEncadrementRequest();
			enLigneReq.setLibelle("En ligne"); enLigneReq.setSigle("EN LIGNE");
			ModeEncadrement enLigne = modeEncadrementService.add(enLigneReq);

			ModeEncadrementRequest centreReq = new ModeEncadrementRequest();
			centreReq.setLibelle("Centre"); centreReq.setSigle("CENTRE");
			ModeEncadrement centre = modeEncadrementService.add(centreReq);
		};
	}
}
