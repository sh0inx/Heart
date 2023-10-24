package io.github.sh0inx.heart.managers.versioncheck;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.MessageType;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UpdateCheck {

    private final Heart heart;

    public UpdateCheck(Heart heart) {
        this.heart = heart;
    }

    //TODO: Update method to load async
    public String getCurrentVersion() {
        Gson gson = new Gson();
        StringBuilder response = new StringBuilder();

        int responseCode = 0;

        try {
            URL url = new URL("https://api.modrinth.com/v2/project/" + heart.getModrinthPluginId + "/version");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            responseCode = connection.getResponseCode();
            heart.logMessage(MessageType.TRACE, "RESPONSE: " + responseCode);

            if (responseCode == 200) {
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
            } else {
                heart.logMessage(MessageType.TRACE, "NO CONNECTION ESTABLISHED.");
            }

            connection.disconnect();

            jsonModel[] mapVersion = gson.fromJson(response.toString(), jsonModel[].class);
            return mapVersion[0].currentVersion;

        } catch (Exception e) {
            heart.logMessage(MessageType.WARNING, "Unable to check for current version. (" + responseCode + ")");
            heart.logMessage(MessageType.WARNING,  "" + e);
            return "404";
        }
    }
}