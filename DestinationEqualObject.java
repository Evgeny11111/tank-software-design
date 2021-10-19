package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.objects.Tree;
import ru.mipt.bit.platformer.player.move.NewDestination;

import java.util.ArrayList;

public class DestinationEqualObject {
    static ArrayList<Tree> trees = Creater.getTrees();

    public static boolean compare(){
        for (Tree arrayTree : trees) {
            if(arrayTree.getCoordinates().equals(NewDestination.newCoordinates())){
                return true;
            }
        }
        return false;
    }
}
