package de.ferienakademie.smartquake.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.ferienakademie.smartquake.kernel1.Testing;

public class StructureFactory {
    public static Structure getSimpleHouse() {
        double width = 8;
        double height = 8;

        double half = width * 0.5;

        List<Double> unode1 = new LinkedList<>();
        unode1.add(0.0);
        unode1.add(0.0);
        unode1.add(0.0);

        Structure structure = new Structure();
        Material testMaterial = new Material();

        //Kernel1 stuff
        //TODO Alex: redesign with ID Matrix
        //TODO Paul: setConstraint(true) for fixed nodes
        List<Integer> DOFnode1 = new LinkedList<>();
        List<Integer> DOFnode2 = new LinkedList<>();


        DOFnode1.add(0); //constraint
        DOFnode1.add(1);//constraint
        DOFnode1.add(2);//constraint

        DOFnode2.add(3);
        DOFnode2.add(4);
        DOFnode2.add(5);



        Node n1 = new Node(0, height, DOFnode1);
        Node n2 = new Node(0, height - half, DOFnode2);


        Beam b1 = new Beam(n1, n2, testMaterial,true);
        structure.addNodes(n1, n2);
        structure.addBeams(b1);

        List<Integer> condof= new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            condof.add(i);
        }

        structure.setConDOF(condof);

        Testing.cantiLeverStaticTest(structure);

        return structure;
    }

    public static Structure getSimpleEiffelTower() {
        double width = 8;
        double height = 16;

        double half = width * 0.5;
        double quarter = width * 0.25;
        double eighth = width * 0.125;
        double sixteenth = width * 1d/16;

        Structure structure = new Structure();

        Node tri00 = new Node(half, height);
        Node tri01 = new Node(eighth, height);
        Node tri02 = new Node((half + eighth)/2, height/2);
        structure.addBeams(BeamFactory.createTriangleShapedBeam(tri00, tri01, tri02));

        Node tri11 = new Node(width - eighth, height);
        Node tri12 = new Node(width - (half + eighth)/2, height/2);
        structure.addBeams(BeamFactory.createTriangleShapedBeam(tri00, tri11, tri12));

        Node tri22 = new Node(half, height/4);

        structure.addBeams(BeamFactory.createTriangleShapedBeam(tri02, tri12, tri22));

        structure.addNodes(tri00, tri01, tri11, tri12, tri02, tri22);
        return structure;
    }
}
