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
import tn.esprit.backend.repository.EntrepriseRepository;
import tn.esprit.backend.service.EntrepriseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EntrepriseServiceImplTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks
    private EntrepriseServiceImpl entrepriseService;

    @Test
    public void testGetAllEntreprises(){
        List<Entreprise> entrepriseList = new ArrayList<Entreprise>();
        entrepriseList.add(Entreprise.builder().id(1L).nom("Entreprise 1").adresse("Tunis").build());
        entrepriseList.add(Entreprise.builder().id(2L).nom("Entreprise 2").adresse("Sfax").build());
        entrepriseList.add(Entreprise.builder().id(3L).nom("Entreprise 3").adresse("Sousse").build());
        when(entrepriseRepository.findAll()).thenReturn(entrepriseList);

        List<Entreprise> result = entrepriseService.getAllEntreprises();
        assertEquals(3, result.size());
    }

    @Test
    public void testGetEntrepriseById(){
        Entreprise entreprise = Entreprise.builder().id(1L).nom("Entreprise 1").adresse("Tunis").build();
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(entreprise));

        Entreprise result = entrepriseService.getEntrepriseById(1L);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("Entreprise 1", result.getNom());
        assertEquals("Tunis", result.getAdresse());
    }

    @Test
    public void testGetEntrepriseByIdNotFound(){
        when(entrepriseRepository.findById(99L)).thenReturn(Optional.empty());

        Entreprise result = entrepriseService.getEntrepriseById(99L);
        assertNull(result);
    }

    @Test
    public void addEntreprise(){
        Entreprise entreprise = Entreprise.builder().id(8L).nom("Entreprise 8").adresse("Sfax").build();
        when(entrepriseRepository.save(entreprise)).thenReturn(entreprise);

        Entreprise result = entrepriseService.addEntreprise(entreprise);
        assertEquals(Long.valueOf(8L), result.getId());
        assertEquals("Entreprise 8", result.getNom());
        assertEquals("Sfax", result.getAdresse());
    }

    @Test
    public void updateEntreprise(){
        Entreprise entreprise = Entreprise.builder().id(8L).nom("Entreprise 8 Updated").adresse("Sfax").build();
        when(entrepriseRepository.save(entreprise)).thenReturn(entreprise);

        Entreprise result = entrepriseService.updateEntreprise(entreprise);
        assertEquals("Entreprise 8 Updated", result.getNom());
    }

    @Test
    public void deleteEntreprise(){
        entrepriseService.deleteEntreprise(8L);
        verify(entrepriseRepository, times(1)).deleteById(8L);
    }

}