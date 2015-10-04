package ai.cogmission.fxmaps.xml;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;


public class GPXReader {
    /** {@link XmlParserCreator} creator */
    public static final XmlParserCreator PARSER_CREATOR = new XmlParserCreator() {
        @Override
        public XmlPullParser createParser() {
            try {
                final XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
                return parser;
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    };
    
    /** The reader used */
    private GsonXml reader;
    
    /**
     * Constructs a new GPXReader
     */
    public GPXReader() {
        reader = createGson(false);
    }

    /**
     * Returns a new {@link GsonXml} object for reading xml.
     * 
     * @param namespaces    flag indicating whether to use name spaces.
     * @return  a GsonXml object
     */
    public GsonXml createGson(final boolean namespaces) {
        return new GsonXmlBuilder().setSameNameLists(true).setXmlParserCreator(PARSER_CREATOR).setTreatNamespaces(namespaces).create();
    }
    
    /**
     * Returns an {@link GPXPersistentMap} object which models a 
     * given Route, Waypoint, Track
     * 
     * @param url   the location of the text source
     * @return      the persistent map
     */
    public GPXPersistentMap read(URL url) {
        try {
            return read(url.openStream());
        }catch(Exception e) { e.printStackTrace(); }
        
        return null;
    }
    
    /**
     * Returns an {@link GPXPersistentMap} object which models a 
     * given Route, Waypoint, Track
     * 
     * @param stream    the stream of text
     * @return          the persistent map
     */
    public GPXPersistentMap read(InputStream stream) {
        GPXPersistentMap map = null;
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(stream));
            map = reader.fromXml(buff, GPXPersistentMap.class);
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return map;
    }
}
