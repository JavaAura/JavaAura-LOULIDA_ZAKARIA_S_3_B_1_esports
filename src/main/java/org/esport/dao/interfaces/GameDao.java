package org.esport.dao.interfaces;

import org.esport.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    Game create(Game game);

    Game update(Game game);

    void delete(Long id);

    Optional<Game> findById(Long id);

    List<Game> findAll();
}
