package starter.postcodes;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestBuilder {

public static void main(String args[])
{
//Create Objects
   JSONObject requestpayload = new JSONObject();
   JSONArray proposalSearchCriteriaRequest = new JSONArray();
   JSONObject Proposal1 = new JSONObject();
   JSONObject Proposal2 = new JSONObject();
//Create Payload
   try {
      Proposal1.put("proposalId", "IProposalDTO_227120");
      Proposal2.put("proposalId", "IProposalDTO_403213759");
      Proposal2.put("version", "1");
      proposalSearchCriteriaRequest.put(Proposal1);
      proposalSearchCriteriaRequest.put(Proposal2);
      requestpayload.put("proposalSearchCriteriaRequest", proposalSearchCriteriaRequest);
   }
   catch (Exception e)
   {
      e.printStackTrace();
   }
System.out.println(requestpayload);
}
}
