import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class FileUploadGoogleExample {

	private static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static HttpTransport httpTransport;

	/** Authorizes the installed application to access user's protected data. */
	private Credential authorize() throws Exception {

		java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".store/drive_sample");
		FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		// load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(FileUploadGoogleExample.class.getResourceAsStream("/client_secrets.json")));
		if (clientSecrets.getDetails().getClientId().startsWith("Enter")
				|| clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
			System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=drive "
					+ "into drive-cmdline-sample/src/main/resources/client_secrets.json");
			System.exit(1);
		}
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
				clientSecrets, Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(dataStoreFactory)
						.build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}

	public void upload() throws Exception  {
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Credential credential = authorize();

			Drive driveService = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
					.setApplicationName("Test App").build();

			String folderId = "1EMvxLfzgIbN5da73S9_qWFINXVo-kFVO";
			File fileMetadata = new File();
			fileMetadata.setName("bullsEye.jpg");
			fileMetadata.setParents(Collections.singletonList(folderId));
			java.io.File filePath = new java.io.File("c:/pics/bullsEye.jpg");
			FileContent mediaContent = new FileContent("image/jpeg", filePath);
			File file = driveService.files().create(fileMetadata, mediaContent).setFields("id,parents").execute();
			System.out.println("File ID: " + file.getId());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}

	public static void main(String[] args) {
		FileUploadGoogleExample example = new FileUploadGoogleExample();
		try {
			example.upload();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
