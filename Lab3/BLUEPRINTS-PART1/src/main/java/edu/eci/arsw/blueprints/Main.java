package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices gc = ac.getBean(BlueprintsServices.class);
        gc.addNewBlueprint(new Blueprint("Orlando Gelves","Plano de Construccion"));
        System.out.println(gc.getBlueprint("Orlando Gelves","Plano de Construccion"));
        Point[] puntos = {new Point(3,4),new Point(7,2),new Point(1,2)};
        gc.addNewBlueprint(new Blueprint("Jimmy Moya","Plano de Edificio",puntos));
        System.out.println(gc.getAllBlueprints());
        gc.addNewBlueprint(new Blueprint("Orlando Gelves","Plano de mi casa"));
        System.out.println(gc.getBlueprintsByAuthor("Orlando Gelves"));
        puntos = new Point[]{new Point(3, 4), new Point(3, 4), new Point(1, 2),new Point(5,5)};

        gc.addNewBlueprint(new Blueprint("Jimmy Moya","Plano de Escuela",puntos));


        System.out.println(gc.getBlueprint("Jimmy Moya","Plano de Escuela")+" "+ gc.filter(gc.getBlueprint("Jimmy Moya","Plano de Escuela")).getPoints());

    }

}

