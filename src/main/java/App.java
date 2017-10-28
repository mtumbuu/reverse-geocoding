import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your api key : ");
        Geo geo = new Geo(scanner.next());
        while (true) {
            System.out.print("Enter latitude and longitude (comma separated) : ");
            String line = scanner.hasNextLine() ? scanner.nextLine() : null;
            if (line == null || line.equals("q")) {
                break;
            }
            double lat = Double.parseDouble(line.split(",")[0]);
            double lng = Double.parseDouble(line.split(",")[1]);
            System.out.print("Address : " + geo.getAddress(lat, lng));
            System.out.println(". Press 'q' to exit...");
        }
        scanner.close();
    }
}
