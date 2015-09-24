package ai.cogmission.fxmaps.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

/**
 * Utility which can be used to obtain the current location of the client
 * application making a call to this library. This can be used to "center"
 * the map or other location services.
 * 
 * @author cogmission
 *
 */
public class Locator {
    
    /**
     * Returns a {@link Location} object with location information which may
     * not have very strictly accurate information.
     * 
     * @param ipStr         the IP Address for which a {@link Location} will be obtained.
     * @return
     * @throws Exception
     */
    public static Location getIPLocation(String ipStr) throws Exception {
        System.out.println("gres = " + Locator.class.getClassLoader().getResource("GeoLite2-City.mmdb"));
        InputStream is = Locator.class.getClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder(is).build();
        CityResponse response = reader.city(InetAddress.getByName(ipStr));

        System.out.println("City " +response.getCity());
        System.out.println("ZIP Code " +response.getPostal().getCode());
        System.out.println("Country " +response.getCountry());
        System.out.println("Location " +response.getLocation());
        
        return new Location(response.getCity().toString(), response.getPostal().getCode(), 
            response.getCountry().toString(), response.getLocation().getTimeZone(), response.getLocation().getLatitude(), 
                response.getLocation().getLongitude(), response.getPostal().getConfidence(),
                    response.getLocation().getAccuracyRadius(), response.getLocation().getPopulationDensity(),
                        response.getLocation().getAverageIncome());
    }

    /**
     * Uses a webservice to obtain the local IP address of the caller.
     * @return
     * @throws Exception
     */
    public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {}
            }
        }
    }
}
