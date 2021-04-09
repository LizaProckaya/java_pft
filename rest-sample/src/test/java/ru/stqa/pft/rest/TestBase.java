package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

public class TestBase {

  @BeforeClass
  public void init() {
    RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public String getStateNameIssue(int id) {
    String json = RestAssured.get(String.format("https://bugify.stqa.ru/api/issues/%s.json", id)).asString();
    JsonElement parsedJson = new JsonParser().parse(json);
    JsonElement parsedJsonIssues = parsedJson.getAsJsonObject().get("issues");
    Issue[] issues = new Gson().fromJson(parsedJsonIssues, new TypeToken<Issue[]>(){}.getType());
    return issues[0].getStateName();
  }

  public boolean isIssueOpen(int id) {
    String issueState = getStateNameIssue(id);
    if (issueState.equals("Resolved") || issueState.equals("Closed")) {
      return false;
    }
    return true;
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}
