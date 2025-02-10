import java.util.HashMap;
import java.util.Scanner;

public class DirectedWeightedGraph{
    private HashMap<String, Node> cities; // all cities;

    public class Node{ // each city;
        private HashMap<String, Integer> neighbors = new HashMap<>(); // neighbors and distance to them;
        private String name;
        public Node(String name){ 
            this.name = name; // name of the city;
        }
        private boolean addRoute(String neighbor, int distance){
            if(!cities.containsKey(neighbor)){
                cities.put(neighbor, new Node(neighbor));
            }
            if(neighbors.containsKey(neighbor)){
                System.out.println("Oh no! that route already exists!");
                return false;
            }
            neighbors.put(neighbor, distance); // add a neighbor and distance to it;
            return true;
        }

        private boolean removeRoute(String neighbor){
            if(!neighbors.containsKey(neighbor)){
                System.out.println("Oh no! that route does not exist!");
                return false;
            }
            neighbors.remove(neighbor);
            return true;
        }

        public void setNeighbors(HashMap<String, Integer> neighbors){
            this.neighbors = neighbors;
        }
        public HashMap<String, Integer> getNeighbors(){
            return neighbors;
        }

    }

    public DirectedWeightedGraph(){
        cities = new HashMap<>();
    }
    public boolean removeRoute(String from, String to){
        Node city = searchCity(from);
        if(city == null){
            return false;
        }
        return city.removeRoute(to);
    }
    public boolean addRoute(String from, String to, int distance){
        Node city = searchCity(from);
        if(city == null){
            return false;
        }
        if(city.neighbors.containsKey(to)){
            System.out.println("Oh no! that route already exists!");
            return false;
        }
        return city.addRoute(to, distance);
    }
    

    public boolean addCity(String name){ // add a city;
        if(cities.containsKey(name)){ // if already contains..
            System.out.println("Oh no! that city already exists!");
            return false;
        } 
        Node city = new Node(name);
        cities.put(name, city);
        return true; 
    }

    private Node searchCity(String name){
        if(cities.containsKey(name)){ // if city exists..
            return cities.get(name); // return its node
        }
        System.out.println("Oh no! that city does not exist!");
        return null;
    }
    public HashMap<String, Node> getCities(){
        return cities;
    }
    public void setCities(HashMap<String, Node> cities){
        this.cities = cities;
    }

   public void show(){
    for(Node city: cities.values()){
        System.out.println(city.name + " -> ");
        for(String neighbor: city.neighbors.keySet()){
            System.out.println("    " + neighbor + " - Distance: " + city.neighbors.get(neighbor));
        }
    }
   }
}