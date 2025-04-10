package main;

import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import myexceptions.ArtWorkNotFoundException;
import myexceptions.UserNotFoundException;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) throws UserNotFoundException, ArtWorkNotFoundException, SQLException, Exception {
        Scanner sc = new Scanner(System.in);
        VirtualArtGalleryImpl service = new VirtualArtGalleryImpl();
        Connection connection = DBConnection.getDbConnection();

        
        try {
            String artistInsert = "INSERT INTO Artist (Name, Biography, BirthDate, Nationality, Website, ContactInformation) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement artistPs = connection.prepareStatement(artistInsert);

            artistPs.setString(1, "Vincent van Gogh");
            artistPs.setString(2, "Dutch post-impressionist painter known for Starry Night.");
            artistPs.setString(3, "1853-03-30");
            artistPs.setString(4, "Dutch");
            artistPs.setString(5, "https://vangoghgallery.com");
            artistPs.setString(6, "vincent@artmail.com");
            artistPs.executeUpdate();

            artistPs.setString(1, "Leonardo da Vinci");
            artistPs.setString(2, "Italian Renaissance artist known for Mona Lisa and The Last Supper.");
            artistPs.setString(3, "1452-04-15");
            artistPs.setString(4, "Italian");
            artistPs.setString(5, "https://davinciart.com");
            artistPs.setString(6, "leo@renaissance.com");
            artistPs.executeUpdate();

            String artworkInsert = "INSERT INTO Artwork (Title, Description, CreationDate, Medium, ImageURL, ArtistID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement artworkPs = connection.prepareStatement(artworkInsert);

            artworkPs.setString(1, "Starry Night");
            artworkPs.setString(2, "One of Van Gogh's most famous works, painted in 1889.");
            artworkPs.setString(3, "1889-06-01");
            artworkPs.setString(4, "Oil on canvas");
            artworkPs.setString(5, "https://example.com/starrynight.jpg");
            artworkPs.setInt(6, 1);
            artworkPs.executeUpdate();

            artworkPs.setString(1, "Mona Lisa");
            artworkPs.setString(2, "A portrait painting by Leonardo da Vinci.");
            artworkPs.setString(3, "1503-01-01");
            artworkPs.setString(4, "Oil on poplar panel");
            artworkPs.setString(5, "https://example.com/monalisa.jpg");
            artworkPs.setInt(6, 2);
            artworkPs.executeUpdate();

            System.out.println("Sample artists and artworks inserted successfully!");
        } catch (SQLException e) {
            System.out.println("Data seeding skipped or failed: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n Virtual Art Gallery Menu ");
            System.out.println("1. Add Artist");
            System.out.println("2. Add Artwork");
            System.out.println("3. Update Artwork");
            System.out.println("4. Remove Artwork");
            System.out.println("5. Get Artwork by ID");
            System.out.println("6. Search Artworks");
            System.out.println("7. Add Artwork to Favorites");
            System.out.println("8. Remove Artwork from Favorites");
            System.out.println("9. Get User Favorite Artworks");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Artist Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Biography: ");
                        String bio = sc.nextLine();
                        System.out.print("Enter Birth Date (YYYY-MM-DD): ");
                        String dob = sc.nextLine();
                        System.out.print("Enter Nationality: ");
                        String nationality = sc.nextLine();
                        System.out.print("Enter Website: ");
                        String website = sc.nextLine();
                        System.out.print("Enter Contact Information: ");
                        String contact = sc.nextLine();

                        PreparedStatement artistStmt = connection.prepareStatement(
                                "INSERT INTO Artist (Name, Biography, BirthDate, Nationality, Website, ContactInformation) VALUES (?, ?, ?, ?, ?, ?)");
                        artistStmt.setString(1, name);
                        artistStmt.setString(2, bio);
                        artistStmt.setString(3, dob);
                        artistStmt.setString(4, nationality);
                        artistStmt.setString(5, website);
                        artistStmt.setString(6, contact);

                        int rows = artistStmt.executeUpdate();
                        System.out.println(rows > 0 ? " Artist added!" : " Failed to add artist.");
                        break;

                    case 2:
                        Artwork a1 = new Artwork();
                        System.out.print("Enter Title: ");
                        a1.setTitle(sc.nextLine());
                        System.out.print("Enter Description: ");
                        a1.setDescription(sc.nextLine());
                        System.out.print("Enter Creation Date (YYYY-MM-DD): ");
                        a1.setCreationDate(sc.nextLine());
                        System.out.print("Enter Medium: ");
                        a1.setMedium(sc.nextLine());
                        System.out.print("Enter Image URL: ");
                        a1.setImageUrl(sc.nextLine());
                        System.out.print("Enter Artist ID: ");
                        a1.setArtistId(Integer.parseInt(sc.nextLine()));
                        System.out.println(service.addArtwork(a1) ? " Artwork added!" : " Failed to add artwork.");
                        break;

                    case 3:
                        Artwork a2 = new Artwork();
                        System.out.print("Enter Artwork ID to update: ");
                        a2.setArtworkId(Integer.parseInt(sc.nextLine()));
                        System.out.print("Enter Title: ");
                        a2.setTitle(sc.nextLine());
                        System.out.print("Enter Description: ");
                        a2.setDescription(sc.nextLine());
                        System.out.print("Enter Creation Date (YYYY-MM-DD): ");
                        a2.setCreationDate(sc.nextLine());
                        System.out.print("Enter Medium: ");
                        a2.setMedium(sc.nextLine());
                        System.out.print("Enter Image URL: ");
                        a2.setImageUrl(sc.nextLine());
                        System.out.print("Enter Artist ID: ");
                        a2.setArtistId(Integer.parseInt(sc.nextLine()));
                        System.out.println(service.updateArtwork(a2) ? "Artwork updated!" : " Update failed.");
                        break;

                    case 4:
                        System.out.print("Enter Artwork ID to remove: ");
                        int rid = Integer.parseInt(sc.nextLine());
                        System.out.println(service.removeArtwork(rid) ? "Artwork removed!" : " Failed to remove.");
                        break;

                    case 5:
                        System.out.print("Enter Artwork ID: ");
                        int artworkId = Integer.parseInt(sc.nextLine());
                        Artwork art = service.getArtworkById(artworkId);
                        if (art == null) {
                            throw new ArtWorkNotFoundException("Invalid Artwork ID: " + artworkId);
                        }
                        System.out.println(" Artwork Found:\nTitle: " + art.getTitle() + "\nMedium: " + art.getMedium());
                        break;



                    case 6:
                        System.out.print("Enter search keyword: ");
                        String keyword = sc.nextLine();
                        List<Artwork> found = service.searchArtworks(keyword);
                        System.out.println("Found " + found.size() + " artworks:");
                        for (Artwork aw : found) {
                            System.out.println("- " + aw.getTitle() + " [" + aw.getMedium() + "]");
                        }
                        break;

                    case 7:
                        System.out.print("Enter User ID: ");
                        int uid1 = Integer.parseInt(sc.nextLine());
                        if (!userExists(connection, uid1)) {
                            throw new UserNotFoundException("Invalid User ID: " + uid1);
                        }
                        System.out.print("Enter Artwork ID: ");
                        int aid1 = Integer.parseInt(sc.nextLine());
                        System.out.println(service.addArtworkToFavorite(uid1, aid1) ? " Added to favorites!" : "Failed.");
                        break;

                    case 8:
                        System.out.print("Enter User ID: ");
                        int uid2 = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Artwork ID: ");
                        int aid2 = Integer.parseInt(sc.nextLine());
                        System.out.println(service.removeArtworkFromFavorite(uid2, aid2) ? "Removed from favorites!" : " Failed.");
                        break;

                    case 9:
                        System.out.print("Enter User ID: ");
                        int uid3 = Integer.parseInt(sc.nextLine());
                        List<Artwork> favs = service.getUserFavoriteArtworks(uid3);
                        if (favs == null || favs.isEmpty()) {
                            System.out.println("No favorites found.");
                        } else {
                            System.out.println("Favorite Artworks:");
                            for (Artwork fav : favs) {
                                System.out.println("- " + fav.getTitle() + " (" + fav.getMedium() + ")");
                            }
                        }
                        break;

                    case 10:
                        System.out.println(" Exiting Virtual Art Gallery. Have a nice day!");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
            	throw new RuntimeException(e);
            }
        }
    }

    private static boolean userExists(Connection connection, int userId) {
        String query = "SELECT COUNT(*) FROM User WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	}


