package edu.ucdavis.coeitss;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UCDIAMWorker {
    
    public String wrkStatus;
    public String iamURL;
    public String iamKey;


    public UCDIAMWorker()
    {
        iamURL = "https://iet-ws.ucdavis.edu/api/iam/";
        iamKey = System.getenv("UCDIAM");
    }

    public HashSet<String> Get_Campus_Addresses_for_Students_In_MajorCode(String ucdMajorCode) throws Exception
    {

        //HashSet of Email Addresses Returned from IAM API Call
        HashSet<String> hstEmailAddrs = new HashSet<>();

        //Check for API Call Required Values
        if(iamURL.isEmpty() == false && iamKey.isEmpty() == false)
        {

            //HttpClient for API Call to IAM API
            HttpClient client = HttpClient.newHttpClient();

            //HttpRequest 
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(iamURL + "/associations/sis/search?key=" + iamKey + "&v=1.0&retType=people&majorCode=" + ucdMajorCode))
                    .GET()
                    .build();

            //Make API Call to IAM Endpoint
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Initiate Object Mapper to Parse Returned Json
            ObjectMapper mapper = new ObjectMapper();

            //Create Json Object of Read in Returned Json
            JsonNode root = mapper.readTree(response.body());
            
            //Loop Through Response Data Results
            for (JsonNode uResultJN : root.get("responseData").get("results")) 
            {

                //Null Check on Campus Email 
                if(uResultJN.get("campusEmail") != null)
                {

                    //Var for Pulled Campus Email Address
                    String usrEml = uResultJN.get("campusEmail").asText();

                    //Check to Make Sure Only Unique Values are Added
                    if(hstEmailAddrs.contains(usrEml.toLowerCase()) == false && usrEml.equalsIgnoreCase("null") == false)
                    {
                        hstEmailAddrs.add(usrEml.toLowerCase());
                    }
                    
                }//End of Null Check on Campus Email Value
                
            }//End of responseData results foreach

        }
        else
        {
            System.out.println("Required API call values are missing");
        }//End of IAM URL and Key Checks

        return hstEmailAddrs;
    }

}
