
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/**
 *
 * @author arkadiusz.gac
 */
public class DictExport {
    
    private String fileName = "dict.txt";
    private String dbName = "dict";
    private String selectedColl = "";
    private MongoClient mongoClient;
    private DB db;
    private DBCollection selectedCollection;
    private String l = "";
    
    public DictExport(String dictFile, String dbName) {
        if(dictFile != null) {
            this.fileName = dictFile;
        }

        if(dbName != null) {
            this.dbName = dbName;
        }

        try {
            this.mongoClient = new MongoClient( "localhost" , 27017 );
        } catch (UnknownHostException ex) {
            System.err.println(ex);
            System.exit(1);
        }
            this.db = mongoClient.getDB(this.dbName);
            this.db.dropDatabase();
        try {
            this.importByLine();
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(2);
        }
    }
    
    private void importByLine() throws FileNotFoundException, IOException {
        Charset ch = Charset.forName("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                      new FileInputStream(this.fileName), ch));
        String line;
        String[] splitedLine;
        Integer i = 0;
        while ((line = br.readLine()) != null) {
            line = line.toLowerCase();
            this.l = line;
            splitedLine = line.split("\t");
            try {
                this.saveToDatabase(splitedLine[0], splitedLine[1]);
            } catch(MongoException e) {
                System.err.println(e);
            }
            if(i%1000 == 0) {
                System.out.println(i);
            }
            i++;
        }
        br.close();
    }
    
    private void saveToDatabase(String first, String second) {
        String useCol = String.valueOf(first.toCharArray()[0]);
        if(this.selectedCollection == null || !useCol.equalsIgnoreCase(this.selectedCollection.getName())) {
            if(!this.db.collectionExists(useCol)) {
                DBObject options = BasicDBObjectBuilder.start().add("capped", true).add("size", 200000000l).get();
                this.db.createCollection(useCol, options);
            }
            this.selectedCollection = this.db.getCollection(useCol);
            this.selectedCollection.createIndex(new BasicDBObject("word", 1));
        }
        
        BasicDBObject doc = new BasicDBObject("word", first).
            append("core", second);
        this.selectedCollection.insert(doc);
        
    }
    
    public static void main( String args[] ){
        String dictFile = null;
        String dbName = null;
        if(args.length > 0 && args.length == 1) {
            dictFile = args[0];
        }

        if(args.length > 0 && args.length == 2) {
            dbName = args[1];
        }



        DictExport dictObj = new DictExport(dictFile, dbName);
   }
}
