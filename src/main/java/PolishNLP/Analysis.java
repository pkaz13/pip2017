package PolishNLP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Analysis {
    static private final String nlprestURL="http://ws.clarin-pl.eu/nlprest2/base/";

    public static String nlpFileUpload(String fileName) throws IOException
    {
        return ClientBuilder.newClient().target(nlprestURL+"upload").request().
                post(Entity.entity(new File(fileName), MediaType.APPLICATION_OCTET_STREAM)).
                readEntity(String.class);
    }

    public static void nlpFileDownload(String id,String fileName) throws IOException
    {   URL url = new URL(nlprestURL+"download"+id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true); conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            Files.copy(is, Paths.get(fileName),StandardCopyOption.REPLACE_EXISTING);
        }
        else throw new IOException("Error downloading file");
    }

    private static String getRes(Response res) throws IOException
    {  if (res.getStatus()!=200)
        throw new IOException("Error in nlprest processing");
        return res.readEntity(String.class);
    }

    public static String nlpProcess(String toolname,String id,JSONObject options) throws IOException, InterruptedException, JSONException {   JSONObject request=new JSONObject();
        Client client = ClientBuilder.newClient();
        request.put("file", id);
        request.put("tool", toolname);

        request.put("options", options);

        String taskid= client.target(nlprestURL+"startTask").request().
                post(Entity.entity(request.toString(), MediaType.APPLICATION_JSON)).readEntity(String.class);

        String status="";
        JSONObject jsonres=new JSONObject();
        while (!status.equals("DONE"))
        { String res=getRes(client.target(nlprestURL+"getStatus/"+taskid).request().get());

            jsonres=new JSONObject(res);

            status=jsonres.getString("status");
            if (status.equals("ERROR")) throw new IOException("Error in processing");
            if (status.equals("PROCESSING")) { System.out.print(String.format( "%.2f", jsonres.getDouble("value")*100)+"%");
                System.out.print("\b\b\b\b\b\b\b\b\b\b\b");System.out.flush();}
            if (status.equals("DONE")) System.out.println("100%");
            Thread.sleep(500);
        }

        return jsonres.getJSONArray("value").getJSONObject(0).getString("fileID");

    }
    public static void analize() throws IOException, InterruptedException, JSONException {
        String id=nlpFileUpload("C:\\NLP_pl\\demo.txt");
        JSONObject liner2=new JSONObject();liner2.put("model", "top9");
        id=nlpProcess("liner2",id,liner2);
        System.out.println("Done");
        nlpFileDownload(id,"C:\\NLP_pl\\result.xml");
    }
}