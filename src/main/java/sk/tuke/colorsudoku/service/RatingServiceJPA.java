package sk.tuke.colorsudoku.service;

import sk.tuke.colorsudoku.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public List<Rating> getNewestRatings() {
        return entityManager.createQuery("select r from Rating r").getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating");
    }
}
