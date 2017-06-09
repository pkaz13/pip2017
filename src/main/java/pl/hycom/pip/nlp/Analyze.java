package pl.hycom.pip.nlp;

import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Log4j2
public class Analyze {

    static private final String nlprestURL = "http://ws.clarin-pl.eu/nlprest2/base/";

    // wysyłamy wiadomość do analizy
    public static String nlpStringSender(String messageToBeAnalyze) throws IOException {
        log.info("Method to analyzing text was called " + messageToBeAnalyze);
        return ClientBuilder.newClient().target(nlprestURL + "upload").request().post(Entity.entity(new String(messageToBeAnalyze), MediaType.TEXT_PLAIN)).readEntity(String.class);

    }


    public static ArrayList<Result> nlpGetOutput(String id) throws IOException {
        URL url = new URL(nlprestURL + "download" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        return NlpUtills.inputStreamToResultList(conn.getInputStream());


    }

    private static String getRes(Response res) throws IOException {
        if (res.getStatus() != 200)
            throw new IOException("Error in nlprest processing");
        return res.readEntity(String.class);

    }

    public static String nlpProcess(String toolname, String id, JSONObject options) throws IOException, InterruptedException, JSONException {
        JSONObject request = new JSONObject();
        Client client = ClientBuilder.newClient();
        request.put("file", id);
        request.put("tool", toolname);
        request.put("options", options);
        String taskid = client.target(nlprestURL + "startTask").request().
                post(Entity.entity(request.toString(), MediaType.APPLICATION_JSON)).readEntity(String.class);

        String status = "";
        JSONObject jsonres = new JSONObject();
        while (!("DONE").equals(status)) {
            String res = getRes(client.target(nlprestURL + "getStatus/" + taskid).request().get());
            jsonres = new JSONObject(res);
            status = jsonres.getString("status");
            if (("ERROR").equals(status)) throw new IOException("Error in processing");
            if (("DONE").equals(status))
                Thread.sleep(500);
        }

        return jsonres.getJSONArray("value").getJSONObject(0).getString("fileID");


    }

    public static ArrayList<Result> analyze(String message) throws IOException, InterruptedException, JSONException {
        String id = nlpStringSender(message);
        JSONObject liner2 = new JSONObject();
        liner2.put("model", "top9");
        id = nlpProcess("liner2", id, liner2);
        return nlpGetOutput(id);

    }

}