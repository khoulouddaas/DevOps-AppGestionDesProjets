package tn.esprit.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.repository.ProjetDetailleRepository;
import tn.esprit.backend.repository.ProjetRepository;
import tn.esprit.backend.service.ProjetDetailleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjetDetailleServiceImplTest {

	@Mock
	private ProjetDetailleRepository projetDetailleRepository;

	@Mock
	private ProjetRepository projetRepository;

	@InjectMocks
	private ProjetDetailleServiceImpl projetDetailleService;

	@Test
	public void testGetAllProjetsDetailles(){
		List<ProjetDetaille> list = new ArrayList<ProjetDetaille>();
		list.add(ProjetDetaille.builder().id(1L).description("Desc 1").technologie("Java").coutProvisoire(1000.0).dateDebut(LocalDate.now()).build());
		list.add(ProjetDetaille.builder().id(2L).description("Desc 2").technologie("Angular").coutProvisoire(2000.0).dateDebut(LocalDate.now()).build());
		when(projetDetailleRepository.findAll()).thenReturn(list);

		List<ProjetDetaille> result = projetDetailleService.getAllProjetsDetailles();
		assertEquals(2, result.size());
	}

	@Test
	public void testGetProjetDetailleById(){
		ProjetDetaille pd = ProjetDetaille.builder().id(1L).description("Desc 1").technologie("Java").coutProvisoire(1000.0).build();
		when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(pd));

		ProjetDetaille result = projetDetailleService.getProjetDetailleById(1L);
		assertEquals(Long.valueOf(1L), result.getId());
		assertEquals("Java", result.getTechnologie());
	}

	@Test
	public void testGetProjetDetailleByIdNotFound(){
		when(projetDetailleRepository.findById(99L)).thenReturn(Optional.empty());

		ProjetDetaille result = projetDetailleService.getProjetDetailleById(99L);
		assertNull(result);
	}

	@Test
	public void addProjetDetaille(){
		ProjetDetaille pd = ProjetDetaille.builder().id(8L).description("Desc 8").technologie("Spring").coutProvisoire(500.0).build();
		when(projetDetailleRepository.save(pd)).thenReturn(pd);

		ProjetDetaille result = projetDetailleService.addProjetDetaille(pd);
		assertEquals(Long.valueOf(8L), result.getId());
		assertEquals("Spring", result.getTechnologie());
	}

	@Test
	public void updateProjetDetaille(){
		ProjetDetaille pd = ProjetDetaille.builder().id(8L).description("Desc 8 Updated").technologie("Spring").build();
		when(projetDetailleRepository.save(pd)).thenReturn(pd);

		ProjetDetaille result = projetDetailleService.updateProjetDetaille(pd);
		assertEquals("Desc 8 Updated", result.getDescription());
	}

	@Test
	public void deleteProjetDetaille(){
		projetDetailleService.deleteProjetDetaille(8L);
		verify(projetDetailleRepository, times(1)).deleteById(8L);
	}

	@Test
	public void testGetProjetDetaillesByProjet(){
		List<ProjetDetaille> list = new ArrayList<ProjetDetaille>();
		list.add(ProjetDetaille.builder().id(1L).description("Desc 1").build());
		when(projetDetailleRepository.findByProjetId(3L)).thenReturn(list);

		List<ProjetDetaille> result = projetDetailleService.getProjetDetaillesByProjet(3L);
		assertEquals(1, result.size());
	}

	@Test
	public void assignProjetDetailleToProjet(){
		ProjetDetaille pd = ProjetDetaille.builder().id(1L).description("Desc 1").build();
		Projet projet = Projet.builder().id(3L).sujet("Sujet 3").build();
		when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(pd));
		when(projetRepository.findById(3L)).thenReturn(Optional.of(projet));
		when(projetDetailleRepository.save(pd)).thenReturn(pd);

		ProjetDetaille result = projetDetailleService.assignProjetDetailleToProjet(1L, 3L);
		assertEquals(projet, result.getProjet());
	}

}
