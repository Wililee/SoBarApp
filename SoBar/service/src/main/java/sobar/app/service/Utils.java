package sobar.app.service;

public class Utils {
    public static double dist(double lat1, double lon1, double lat2, double lon2) {
        double phi1 = Math.toRadians(lat1), phi2 = Math.toRadians(lat2), delta = Math.toRadians(lon2-lon1), R = 6371e3; // gives d in metres
        return Math.acos( Math.sin(phi1)*Math.sin(phi2) + Math.cos(phi1)*Math.cos(phi2) * Math.cos(delta) ) * R;
    }
}
