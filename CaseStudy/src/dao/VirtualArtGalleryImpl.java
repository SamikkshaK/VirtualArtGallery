package dao;

import entity.Artwork;
import entity.Artist;
import entity.Gallery;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualArtGalleryImpl {


    public boolean addArtwork(Artwork artwork) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "INSERT INTO Artwork (Title, Description, CreationDate, Medium, ImageURL, ArtistID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, artwork.getTitle());
            ps.setString(2, artwork.getDescription());
            ps.setString(3, artwork.getCreationDate());
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getImageUrl());
            ps.setInt(6, artwork.getArtistId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Failed to add artwork: " + e.getMessage());
            return false;
        }
    }

    public boolean updateArtwork(Artwork artwork) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "UPDATE Artwork SET Title=?, Description=?, CreationDate=?, Medium=?, ImageURL=?, ArtistID=? WHERE ArtworkID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, artwork.getTitle());
            ps.setString(2, artwork.getDescription());
            ps.setString(3, artwork.getCreationDate());
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getImageUrl());
            ps.setInt(6, artwork.getArtistId());
            ps.setInt(7, artwork.getArtworkId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update artwork: " + e.getMessage());
            return false;
        }
    }

    public boolean removeArtwork(int artworkId) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "DELETE FROM Artwork WHERE ArtworkID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, artworkId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to remove artwork: " + e.getMessage());
            return false;
        }
    }

    public Artwork getArtworkById(int artworkId) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT * FROM Artwork WHERE ArtworkID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, artworkId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Artwork art = new Artwork();
                art.setArtworkId(rs.getInt("ArtworkID"));
                art.setTitle(rs.getString("Title"));
                art.setDescription(rs.getString("Description"));
                art.setCreationDate(rs.getString("CreationDate"));
                art.setMedium(rs.getString("Medium"));
                art.setImageUrl(rs.getString("ImageURL"));
                art.setArtistId(rs.getInt("ArtistID"));
                return art;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching artwork by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Artwork> searchArtworks(String keyword) {
        List<Artwork> results = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT * FROM Artwork WHERE Title LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Artwork art = new Artwork();
                art.setArtworkId(rs.getInt("ArtworkID"));
                art.setTitle(rs.getString("Title"));
                art.setDescription(rs.getString("Description"));
                art.setCreationDate(rs.getString("CreationDate"));
                art.setMedium(rs.getString("Medium"));
                art.setImageUrl(rs.getString("ImageURL"));
                art.setArtistId(rs.getInt("ArtistID"));
                results.add(art);
            }
        } catch (SQLException e) {
            System.out.println("Error searching artworks: " + e.getMessage());
        }
        return results;
    }

    public boolean addArtworkToFavorite(int userId, int artworkId) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, artworkId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to add to favorites: " + e.getMessage());
            return false;
        }
    }

    public boolean removeArtworkFromFavorite(int userId, int artworkId) {
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "DELETE FROM User_Favorite_Artwork WHERE UserID = ? AND ArtworkID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, artworkId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to remove from favorites: " + e.getMessage());
            return false;
        }
    }

    public List<Artwork> getUserFavoriteArtworks(int userId) {
        List<Artwork> favorites = new ArrayList<>();
        try (Connection conn = DBConnection.getDbConnection()) {
            String sql = "SELECT a.* FROM Artwork a JOIN User_Favorite_Artwork ufa ON a.ArtworkID = ufa.ArtworkID WHERE ufa.UserID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Artwork art = new Artwork();
                art.setArtworkId(rs.getInt("ArtworkID"));
                art.setTitle(rs.getString("Title"));
                art.setDescription(rs.getString("Description"));
                art.setCreationDate(rs.getString("CreationDate"));
                art.setMedium(rs.getString("Medium"));
                art.setImageUrl(rs.getString("ImageURL"));
                art.setArtistId(rs.getInt("ArtistID"));
                favorites.add(art);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching favorites: " + e.getMessage());
        }
        return favorites;
    }

    

    private List<Gallery> galleries = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private int galleryIdCounter = 1;
    private int artistIdCounter = 1;

    public boolean addGallery(Gallery gallery) {
        gallery.setGalleryID(galleryIdCounter++);
        return galleries.add(gallery);
    }

    public List<Gallery> getAllGalleries() {
        return new ArrayList<>(galleries);
    }

    public Gallery getGalleryById(int id) {
        for (Gallery g : galleries) {
            if (g.getGalleryID() == id) {
                return g;
            }
        }
        return null;
    }

    public boolean updateGallery(Gallery gallery) {
        for (int i = 0; i < galleries.size(); i++) {
            if (galleries.get(i).getGalleryID() == gallery.getGalleryID()) {
                galleries.set(i, gallery);
                return true;
            }
        }
        return false;
    }

    public boolean removeGallery(int id) {
        return galleries.removeIf(g -> g.getGalleryID() == id);
    }

    public boolean addArtist(Artist artist) {
        artist.setArtistId(artistIdCounter++);
        return artists.add(artist);
    }

    public Artist getArtistById(int id) {
        for (Artist a : artists) {
            if (a.getArtistId() == id) {
                return a;
            }
        }
        return null;
    }
}
