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

import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.ProjetRepository;
import tn.esprit.backend.service.ProjetServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjetServiceImplTest {

	@Mock
	private ProjetRepository projetRepository;

	@InjectMocks
	private ProjetServiceImpl projetService;

	@Test
	public void testGetAllProjets(){
		List<Projet> projetList = new ArrayList<Projet>();
		projetList.add(Projet.builder().id(1L).sujet("Sujet 1").build());
		projetList.add(Projet.builder().id(2L).sujet("Sujet 2").build());
		when(projetRepository.findAll()).thenReturn(projetList);

		List<Projet> result = projetService.getAllProjets();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetProjetById(){
		Projet projet = Projet.builder().id(1L).sujet("Sujet 1").build();
		when(projetRepository.findById(1L)).thenReturn(Optional.of(projet));

		Projet result = projetService.getProjetById(1L);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals("Sujet 1", result.getSujet());
	}

	@Test
	public void testGetProjetByIdNotFound(){
		when(projetRepository.findById(99L)).thenReturn(Optional.empty());

		Projet result = projetService.getProjetById(99L);
		assertNull(result);
	}

	@Test
	public void addProjet(){
		Projet projet = Projet.builder().id(8L).sujet("Sujet 8").build();
		when(projetRepository.save(projet)).thenReturn(projet);

		Projet result = projetService.addProjet(projet);
		assertEquals(Long.valueOf(8L), result.getId());
		assertEquals("Sujet 8", result.getSujet());
	}

	@Test
	public void updateProjet(){
		Projet projet = Projet.builder().id(8L).sujet("Sujet 8 Updated").build();
		when(projetRepository.save(projet)).thenReturn(projet);

		Projet result = projetService.updateProjet(projet);
		assertEquals("Sujet 8 Updated", result.getSujet());
	}

	@Test
	public void deleteProjet(){
		projetService.deleteProjet(8L);
		verify(projetRepository, times(1)).deleteById(8L);
	}

}
