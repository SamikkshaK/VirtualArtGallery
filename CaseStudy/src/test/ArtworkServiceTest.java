package test;

import dao.VirtualArtGalleryImpl;
import entity.Artist;
import entity.Artwork;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtworkServiceTest {

    static VirtualArtGalleryImpl service;
    static int testArtistId;

    @BeforeAll
    public static void setup() {
        service = new VirtualArtGalleryImpl();

        
        Artist artist = new Artist();
        artist.setName("Test Artist");
        service.addArtist(artist);
        testArtistId = artist.getArtistId();
    }

    @Test
    @DisplayName("a. Test the ability to upload a new artwork")
    public void testAddArtwork() {
        Artwork art = new Artwork();
        art.setTitle("Test Upload");
        art.setDescription("Uploaded artwork");
        art.setCreationDate("2024-01-01");
        art.setMedium("Oil");
        art.setImageUrl("http://image.url");
        art.setArtistId(testArtistId);

        boolean result = service.addArtwork(art);
        assertTrue(result, "Artwork should be uploaded successfully");
    }

    @Test
    @DisplayName("b. Verify that updating artwork details works correctly")
    public void testUpdateArtwork() {
        Artwork art = new Artwork();
        art.setTitle("Old Title");
        art.setDescription("Old Description");
        art.setCreationDate("2023-01-01");
        art.setMedium("Watercolor");
        art.setImageUrl("http://old.image");
        art.setArtistId(testArtistId);
        service.addArtwork(art);

        Artwork existing = service.searchArtworks("Old Title").get(0);
        existing.setTitle("Updated Title");
        existing.setDescription("Updated Description");

        boolean updated = service.updateArtwork(existing);
        assertTrue(updated, "Artwork should be updated");

        Artwork result = service.searchArtworks("Updated Title").get(0);
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    @DisplayName("c. Test removing an artwork from the gallery")
    public void testRemoveArtwork() {
        Artwork art = new Artwork();
        art.setTitle("To Remove");
        art.setDescription("Temporary");
        art.setCreationDate("2022-10-10");
        art.setMedium("Digital");
        art.setImageUrl("http://remove.url");
        art.setArtistId(testArtistId);
        service.addArtwork(art);

        int id = service.searchArtworks("To Remove").get(0).getArtworkId();
        boolean removed = service.removeArtwork(id);

        assertTrue(removed, "Artwork should be removed");
        assertTrue(service.searchArtworks("To Remove").isEmpty(), "Artwork should no longer exist");
    }

    @Test
    @DisplayName("d. Check if searching for artworks returns the expected results")
    public void testSearchArtwork() {
        Artwork art = new Artwork();
        art.setTitle("Galaxy Art");
        art.setDescription("Stars and nebulae");
        art.setCreationDate("2023-05-05");
        art.setMedium("Acrylic");
        art.setImageUrl("http://galaxy.art");
        art.setArtistId(testArtistId);
        service.addArtwork(art);

        List<Artwork> results = service.searchArtworks("Galaxy");
        assertFalse(results.isEmpty(), "Artwork should be found");
        assertEquals("Galaxy Art", results.get(0).getTitle());
    }
}


