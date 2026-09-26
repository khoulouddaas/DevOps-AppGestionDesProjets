package tn.esprit.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.EntrepriseRepository;
import tn.esprit.backend.repository.EquipeRepository;
import tn.esprit.backend.repository.ProjetRepository;
import tn.esprit.backend.service.EquipeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EquipeServiceImplTest {

	@Mock
	private EquipeRepository equipeRepository;

	@Mock
	private EntrepriseRepository entrepriseRepository;

	@Mock
	private ProjetRepository projetRepository;

	@InjectMocks
	private EquipeServiceImpl equipeService;

	@Test
	public void testGetAllEquipes(){
		List<Equipe> equipeList = new ArrayList<Equipe>();
		equipeList.add(Equipe.builder().id(1L).nom("Equipe 1").specialite("Cloud").build());
		equipeList.add(Equipe.builder().id(2L).nom("Equipe 2").specialite("DevOps").build());
		when(equipeRepository.findAll()).thenReturn(equipeList);

		List<Equipe> result = equipeService.getAllEquipes();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetEquipeById(){
		Equipe equipe = Equipe.builder().id(1L).nom("Equipe 1").specialite("Cloud").build();
		when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));

		Equipe result = equipeService.getEquipeById(1L);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals("Equipe 1", result.getNom());
		assertEquals("Cloud", result.getSpecialite());
	}

	@Test
	public void testGetEquipeByIdNotFound(){
		when(equipeRepository.findById(99L)).thenReturn(Optional.empty());

		Equipe result = equipeService.getEquipeById(99L);
		assertNull(result);
	}

	@Test
	public void addEquipe(){
		Equipe equipe = Equipe.builder().id(8L).nom("Equipe 8").specialite("Full-stack").build();
		when(equipeRepository.save(equipe)).thenReturn(equipe);

		Equipe result = equipeService.addEquipe(equipe);
		assertEquals(Long.valueOf(8L), result.getId());
		assertEquals("Equipe 8", result.getNom());
	}

	@Test
	public void updateEquipe(){
		Equipe equipe = Equipe.builder().id(8L).nom("Equipe 8 Updated").specialite("Full-stack").build();
		when(equipeRepository.save(equipe)).thenReturn(equipe);

		Equipe result = equipeService.updateEquipe(equipe);
		assertEquals("Equipe 8 Updated", result.getNom());
	}

	@Test
	public void deleteEquipe(){
		equipeService.deleteEquipe(8L);
		verify(equipeRepository, times(1)).deleteById(8L);
	}

	@Test
	public void testGetEquipesByEntreprise(){
		List<Equipe> equipeList = new ArrayList<Equipe>();
		equipeList.add(Equipe.builder().id(1L).nom("Equipe 1").build());
		when(equipeRepository.findByEntrepriseId(5L)).thenReturn(equipeList);

		List<Equipe> result = equipeService.getEquipesByEntreprise(5L);
		assertEquals(1, result.size());
	}

	@Test
	public void assignEquipeToEntreprise(){
		Equipe equipe = Equipe.builder().id(1L).nom("Equipe 1").build();
		Entreprise entreprise = Entreprise.builder().id(5L).nom("Entreprise 5").build();
		when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));
		when(entrepriseRepository.findById(5L)).thenReturn(Optional.of(entreprise));
		when(equipeRepository.save(equipe)).thenReturn(equipe);

		Equipe result = equipeService.assignEquipeToEntreprise(1L, 5L);
		assertEquals(entreprise, result.getEntreprise());
	}

	@Test
	public void assignEquipeToProjet(){
		Equipe equipe = Equipe.builder().id(1L).nom("Equipe 1").projets(new ArrayList<Projet>()).build();
		Projet projet = Projet.builder().id(9L).sujet("Sujet 9").build();
		when(equipeRepository.findById(1L)).thenReturn(Optional.of(equipe));
		when(projetRepository.findById(9L)).thenReturn(Optional.of(projet));
		when(equipeRepository.save(equipe)).thenReturn(equipe);

		Equipe result = equipeService.assignEquipeToProjet(1L, 9L);
		assertEquals(1, result.getProjets().size());
		assertEquals(projet, result.getProjets().get(0));
	}

}
