package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import dao.VirtualArtGalleryImpl;
import entity.Gallery;

public class GalleryServiceTest {

    private VirtualArtGalleryImpl service;

    @BeforeEach
    public void setup() {
        service = new VirtualArtGalleryImpl();
    }

    // a. Test creating a new gallery
    @Test
    public void testAddGallery() {
        Gallery gallery = new Gallery();
        gallery.setName("Nature Gallery");
        gallery.setLocation("Mumbai");
        gallery.setDescription("Gallery of Nature Art");

        boolean added = service.addGallery(gallery);
        assertTrue(added, "Gallery should be added successfully");

        List<Gallery> galleries = service.getAllGalleries();
        assertEquals(1, galleries.size(), "There should be one gallery in the system");
        assertEquals("Nature Gallery", galleries.get(0).getName());
    }

    // b. Verify updating gallery information
    @Test
    public void testUpdateGallery() {
        Gallery gallery = new Gallery();
        gallery.setName("Abstract Art");
        gallery.setLocation("Delhi");
        gallery.setDescription("Abstract Collection");
        service.addGallery(gallery);

        Gallery added = service.getAllGalleries().get(0);
        added.setName("Modern Abstract Art");
        boolean updated = service.updateGallery(added);

        assertTrue(updated, "Gallery should be updated");
        assertEquals("Modern Abstract Art", service.getAllGalleries().get(0).getName());
    }

    // c. Test removing a gallery
    @Test
    public void testRemoveGallery() {
        Gallery gallery = new Gallery();
        gallery.setName("Classic Gallery");
        gallery.setLocation("Chennai");
        gallery.setDescription("Old Masterpieces");
        service.addGallery(gallery);

        Gallery added = service.getAllGalleries().get(0);
        boolean removed = service.removeGallery(added.getGalleryID());

        assertTrue(removed, "Gallery should be removed successfully");
        assertTrue(service.getAllGalleries().isEmpty(), "Galleries list should be empty");
    }

    // d. Check search functionality
    @Test
    public void testSearchGalleryById() {
        Gallery gallery = new Gallery();
        gallery.setName("Sci-Fi Art");
        gallery.setLocation("Pune");
        gallery.setDescription("Space and Future");

        service.addGallery(gallery);
        int id = service.getAllGalleries().get(0).getGalleryID();

        Gallery found = service.getGalleryById(id);
        assertNotNull(found, "Gallery should be found");
        assertEquals("Sci-Fi Art", found.getName());
    }
}
