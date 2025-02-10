import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JsonLoader {

    public static DirectedWeightedGraph loadGraphFromJson(String filename) {
        DirectedWeightedGraph graph = new DirectedWeightedGraph();

        try (FileReader reader = new FileReader(filename);
             Scanner scanner = new Scanner(reader)) {

            // Read entire file content into a String
            StringBuilder jsonContent = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonContent.append(scanner.nextLine().trim());
            }

            // Check if the JSON file is empty
            if (jsonContent.length() == 0 || jsonContent.toString().equals("{}")) {
                System.out.println("The JSON file is empty or invalid.");
                return graph;
            }

            String json = jsonContent.toString()
                    .replaceAll("\\s+", "")
                    .replace("{\"cities\":\\[", "")
                    .replace("]}", "");

            if (json.isEmpty()) {
                System.out.println("No cities found in the JSON.");
                return graph;
            }

            // Handle city entries properly
            String[] cityEntries = json.split("\\},\\{");
            for (String cityEntry : cityEntries) {
                cityEntry = cityEntry.replace("{", "").replace("}", "");

                if (cityEntry.isEmpty()) continue;

                // Extract city name
                String cityName = cityEntry.split("\"name\":\"")[1].split("\",\"neighbors\":")[0];
                graph.addCity(cityName);

                // Extract neighbors if they exist
                if (cityEntry.contains("\"neighbors\":[")) {
                    String neighborsPart = cityEntry.split("\"neighbors\":\\[")[1].replace("]", "");
                    if (!neighborsPart.isEmpty()) {
                        String[] neighbors = neighborsPart.split("\\},\\{");
                        for (String neighborEntry : neighbors) {
                            neighborEntry = neighborEntry.replace("{", "").replace("}", "");
                            if (neighborEntry.isEmpty()) continue;

                            String[] neighborParts = neighborEntry.split(",");
                            String neighborName = neighborParts[0].split(":")[1].replace("\"", "");
                            int distance = Integer.parseInt(neighborParts[1].split(":")[1]);

                            graph.addRoute(cityName, neighborName, distance);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing distance value in neighbors.");
        }

        return graph;
    }
}
