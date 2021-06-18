package fr.plopez.todoc.data.utils;

import java.util.Arrays;
import java.util.List;

import fr.plopez.todoc.data.model.Project;

/**
 * This is a fake projects generator to populate the add task project spinner.
 *
 */
public class FakeProjectsGenerator {

    public List<Project> generateFakeProjects(){
        long id = 1;
        return Arrays.asList(
                // TODO : extract string and colors to resources?
                new Project(id++, "Awesome Project", 0xFFEADAD1),
                new Project(id++, "Miraculous Actions", 0xFFB4CDBA),
                new Project(id, "Circus Project", 0xFFA3CED2)
        );
    }
}
