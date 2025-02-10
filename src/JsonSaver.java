import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class JsonSaver {

    public static void saveGraphToJson(DirectedWeightedGraph graph, String filename) {
        StringBuilder jsonBuilder = new StringBuilder();

        // Start JSON structure
        jsonBuilder.append("{\n  \"cities\": [\n");

        // Map each city to its JSON representation
        String citiesJson = graph.getCities().entrySet()
            .stream()
            .map(JsonSaver::buildCityJson)
            .reduce((a, b) -> a + ",\n" + b)
            .orElse("");

        jsonBuilder.append(citiesJson);
        jsonBuilder.append("\n  ]\n}");

        // Write JSON to file
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(jsonBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to build JSON for a single city
    private static String buildCityJson(Map.Entry<String, DirectedWeightedGraph.Node> cityEntry) {
        String city = cityEntry.getKey();
        DirectedWeightedGraph.Node node = cityEntry.getValue();

        StringBuilder cityJson = new StringBuilder();
        cityJson.append("    {\n      \"name\": \"").append(city).append("\",\n");
        cityJson.append("      \"neighbors\": [\n");

        String neighborsJson = node.getNeighbors().entrySet()
            .stream()
            .map(JsonSaver::buildNeighborJson)
            .reduce((a, b) -> a + ",\n" + b)
            .orElse("");

        cityJson.append(neighborsJson);
        cityJson.append("\n      ]\n    }");

        return cityJson.toString();
    }

    // Helper method to build JSON for a single neighbor
    private static String buildNeighborJson(Map.Entry<String, Integer> neighborEntry) {
        return "        { \"name\": \"" + neighborEntry.getKey() + "\", \"distance\": " + neighborEntry.getValue() + " }";
    }
}
