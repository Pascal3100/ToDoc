package fr.plopez.todoc.data.utils;

import java.util.Arrays;
import java.util.List;

import fr.plopez.todoc.data.model.Project;

public class FakeProjectsGenerator {

    public static List<Project> generateFakeProjects(){
        long id = 1;
        return Arrays.asList(
                // TODO : extract string and colors to ressources?
                new Project(id++, "Awesome Project", 0xFFEADAD1),
                new Project(id++, "Miraculous Actions", 0xFFB4CDBA),
                new Project(id, "Circus Project", 0xFFA3CED2)
        );
    }
}
