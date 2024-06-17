import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import okhttp3.*;

import org.json.JSONException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;


public class MyStepdefs {
    private static final String BASE_URL = "http://localhost:5252";
    private static final String PATH = "/clients";
    private static OkHttpClient client = new OkHttpClient();
    private Response response;
    private JSONArray clientList;
    @When("I'm trying to get all bank clients")
    public void iMTryingToGetAllBankClients() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + PATH).build();

        Call call = client.newCall(request);
        response = call.execute();
    }


    @Then("The response contains the list of expected bank clients")
    public void theResponseContainsTheListOfExpectedBankClients() throws IOException, ParseException, JSONException {
        JSONParser parser = new JSONParser();
        JSONObject rootObject = (JSONObject) parser.parse(new StringReader(response.body().string()));
        JSONObject embedded = (JSONObject)rootObject.get("_embedded");
        JSONArray clientList = (JSONArray)embedded.get("clientList");
        this.clientList = clientList;
    
        assertThat(clientList)
                .as("Response is not expected")
                .isNotNull();


    }

    @And("The size of the list of bank clients is {int}")
    public void theSizeOfTheListOfBankClientsIs(int size) {
        assertThat(clientList.size())
                .as("Not expected")
                .isEqualTo(size);
    }
}
