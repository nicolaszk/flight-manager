import java.util.Scanner;
public class App {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        DirectedWeightedGraph map = new DirectedWeightedGraph();
        int option = -1;
        System.out.println("Loading map!");
        map = JsonLoader.loadGraphFromJson("map.json");
        System.out.println("Map loaded!");
        while(true){
            System.out.println("Choose an option: 1. Add city 2. Add route 3. Remove route 4. Show full map 5. Save and exit");
            option = in.nextInt();
            in.nextLine();
            switch(option){
                case 1: 
                    System.out.println("Enter the name of the city: ");
                    String city = in.nextLine();
                    map.addCity(city);
                    break;
                case 2:
                    System.out.println("Enter the name of the city you want to add a route from: ");
                    String from_add = in.nextLine();
                    System.out.println("Enter the name of the city you want to add a route to: ");
                    String to_add = in.nextLine();
                    System.out.println("Enter the distance between the cities: ");
                    int distance = in.nextInt();
                    map.addRoute(from_add, to_add, distance);
                    break;
                case 3: 
                    System.out.println("Enter the name of the city you want to remove a route from: ");
                    String from_remove = in.nextLine();
                    System.out.println("Enter the name of the city you want to remove a route to: ");
                    String to_remove = in.nextLine();
                    map.removeRoute(from_remove, to_remove);
                    break;
                case 4: 
                    map.show();
                    break;
                case 5: 
                    System.out.println("Saving map to file..");
                    JsonSaver.saveGraphToJson(map, "map.json");
                    System.out.println("Map saved! Exiting");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
        
    }
}
