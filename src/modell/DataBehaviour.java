package modell;

import java.util.ArrayList;
import java.util.List;

public class DataBehaviour {

    //TODO: Take Behaviour away from Db and Hard Code it here
    //TODO: Make a list for articles with behaviour because we take behaviourID away from DB maybe with ini file

    private String name;
    private int id;
    private List<Integer> multiplicators;
    private static List<DataBehaviour> behaviourList = new ArrayList<>();

    public DataBehaviour(String name, int id, List<Integer> multiplicators){
        this.name = name;
        this.id = id;
        this.multiplicators = multiplicators;
        behaviourList.add(this);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getMultiplicators() {
        return multiplicators;
    }

    public static List<DataBehaviour> getBehaviourList() {
        return behaviourList;
    }
}
