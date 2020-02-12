package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.stereotype.Service;

@Service("commonFilter")
public interface CommonFilter {
    public Blueprint filter(Blueprint blueprint);
}
