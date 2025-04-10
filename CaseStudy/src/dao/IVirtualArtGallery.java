package dao;

import entity.Artwork;
import myexceptions.ArtWorkNotFoundException;
import myexceptions.UserNotFoundException;

import java.util.List;

public interface IVirtualArtGallery {
    boolean addArtwork(Artwork artwork);
    boolean updateArtwork(Artwork artwork);
    boolean removeArtwork(int artworkId);
    Artwork getArtworkById(int artworkId) throws ArtWorkNotFoundException;
    List<Artwork> searchArtworks(String keyword);
    boolean addArtworkToFavorite(int userId, int artworkId) throws ArtWorkNotFoundException, UserNotFoundException;
    boolean removeArtworkFromFavorite(int userId, int artworkId) throws ArtWorkNotFoundException, UserNotFoundException;
    List<Artwork> getUserFavoriteArtworks(int userId) throws UserNotFoundException;
}
