/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */

package ru.myrange.stuff;
import com.sun.lwuit.Dialog;
import java.util.Vector;
import ru.myrange.soap.*;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.bluetooth.Bluetooth;
import ru.myrange.datastore.Records;

/**
 * Different actions with console and network
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class StaticActions {

    private static Dialog pls = null;

    public static void showWaitDialog(){
        (new Thread() {
            public void run() {
                pls = MyRangeMIDlet.showWaitingAlert(StringConsts.S_CONNECTING_TO_THE_SERVER);
                 }
        }).start();
    }

    public static void hideWaitDialog(){
        if(pls != null)pls.dispose();
    }

    /*
	public static synchronized void cmdAction(String command) throws Exception {
		
		int l = command.length();
		int n = command.indexOf(" ", 0);
		n=( n > 0 ) ? n : l;
		String cmd = command.substring(0,n);
		String arg = "";
		if (n<l) {
			arg = command.substring(n+1,l);
		}
		
		if (arg.equals(StringConsts.S_ATR_HELP)) {		// Show help to this command
			int i=0;
			while (i<StringConsts.I_CONSCDM_NUM) {
				if (cmd.equals(StringConsts.S_CONSCDM[i])) {
					if (i < StringConsts.S_HELP.length) MyRangeMIDlet.console.println(StringConsts.S_HELP[i]);
                    else MyRangeMIDlet.console.println("Help not found");
					break;
				}
				i++;
			}
			if (i == StringConsts.I_CONSCDM_NUM) {		// Command not found
				MyRangeMIDlet.console.println(StringConsts.S_CONSCDM_UNKNOWN_1 + cmd + StringConsts.S_CONSCDM_UNKNOWN_2);
			}
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_HELP])) {		// Show help to all commands
			for (int i=0; i<StringConsts.S_HELP.length; i++) {
				MyRangeMIDlet.console.println(StringConsts.S_HELP[i]);
			}
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_BTINFO])) {		// Show bluetooth logs information
			// Get number of current BT logs and availible space
            String strBTLogsInfo = Records.recBtMeets.getNumRec() + StringConsts.S_RECORD_INFO_S1 +
                    Records.recBtMeets.getSize() + StringConsts.S_RECORD_INFO_S2 +
                    Records.recBtMeets.getSizeAvailable() + StringConsts.S_RECORD_INFO_S3;
            MyRangeMIDlet.console.println(strBTLogsInfo);
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_CLEAR])) {		// Clear the screen
			MyRangeMIDlet.console.eraseAll();
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_SCAN_TO])) {	// Show/Change the scan time out
			if (arg.length() > 0) {
				long scanTimeOut = MyRoutines.strToLong(arg);
				if (scanTimeOut == -1) {
					MyRangeMIDlet.console.println(StringConsts.S_WRONG_NUM);
				} else if (scanTimeOut == -2) {
					MyRangeMIDlet.console.println(StringConsts.S_TOO_BIG_NUM);
				} else {
					Records.recSettings.addLongData(scanTimeOut, Records.REC_SCAN_TO);
					MyRangeMIDlet.console.println(StringConsts.S_SCAN_TO_CHANGED);
				}
			} else {
				MyRangeMIDlet.console.println(Long.toString( Records.recSettings.readLongData(Records.REC_SCAN_TO)) );
            }
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_MERGE_INTERVAL])) {	// Show/Change the merge interval
			if (arg.length() > 0) {
				long mergeInt = MyRoutines.strToLong(arg);
				if (mergeInt == -1) {
					MyRangeMIDlet.console.println(StringConsts.S_WRONG_NUM);
				} else if (mergeInt == -2) {
					MyRangeMIDlet.console.println(StringConsts.S_TOO_BIG_NUM);
				} else {
					Records.recSettings.addLongData(mergeInt, Records.REC_MERGE_INTERVAL);
					MyRangeMIDlet.mergeIntervalVar = mergeInt;							// ! Set the merge intervel
					MyRangeMIDlet.console.println(StringConsts.S_MERGE_INTERVAL_CHANGED);
				}
			} else {
				MyRangeMIDlet.console.println(Long.toString(MyRangeMIDlet.mergeIntervalVar));
			}
			
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_BTLOGS])) {		// Show btlogs

			int num = MyRoutines.strToInt(arg);
			int max = Records.recBtMeets.getNumRec();

			if (num == -1) {
				MyRangeMIDlet.console.println(StringConsts.S_WRONG_NUM);
			} else if ( (num == -2) || (num > max) || (num > StringConsts.I_LOGS_SHOW_MAX) ) {
				MyRangeMIDlet.console.println(StringConsts.S_TOO_BIG_NUM);
			} else {
				int first = max - num + 1;
				BtMeet nextBtMeet;
				String sData;

				for(int i = first; i <= max; ++i){
					nextBtMeet = new BtMeet(Records.recBtMeets.readByteData(i));
					sData = nextBtMeet.getBtAddress() + " " + nextBtMeet.getStartTime() + " " + nextBtMeet.getEndTime() + " " + nextBtMeet.getDevClass()
					        + " " + nextBtMeet.getFriendlyName();
					MyRangeMIDlet.console.println(sData);

				}
				if (max == 0) {
					MyRangeMIDlet.console.println(StringConsts.S_LOGS_NO);
				}
			}
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_MD5])) {		// MD5 encryption
            String hashResult = (Password.fromPlainText(arg)).toString();
			MyRangeMIDlet.console.println(hashResult);
        }

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_TESTUSER])) {	// SOAP getUserInfo TEST
            // TODO remove this test routine
            int id = MyRoutines.strToInt(arg);
			if (id == -1) id = 1;

            HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
            Vector param = new Vector();
            param.addElement("accountType");
            param.addElement("firstName");
            param.addElement("lastName");
            param.addElement("textStatus");
            param.addElement("pic");
            Integer picSizeInteger = new Integer(UserPicCellRenderer.bestPicSize);
            Vector userIDVector = new Vector();
            userIDVector.addElement(new Integer(id));
            //userID.addElement(new Integer(62));

            GetUserInfoRequestElement getUserInfoRequest = new GetUserInfoRequestElement(header, param, picSizeInteger, userIDVector);

            // SOAP getUserInfo
            GetUserInfoResponseElement userInfoResponse = Scrobbler.getUserInfo(getUserInfoRequest);
            Vector users = userInfoResponse.getUsersInfo();
            MyRangeMIDlet.console.println("[SOAP-ECHO:] Server returned:" + users.toString());

            UserInfo newProfile = null;
            if (users.size() > 0) {
                newProfile = (UserInfo) users.elementAt(0);
            }
            //MeetUsers.addTest(newProfile);
        }

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_ECHO])) {		// SOAP echo TEST
            HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
            EchoStringInElement stringIn = new EchoStringInElement(header, arg);

            // SOAP echo
            EchoStringOutElement stringOut = Scrobbler.echo(stringIn);
            MyRangeMIDlet.console.println("[SOAP-ECHO:] Server returned:" + stringOut.getStringOut());
        }

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_UPLOAD_LOGS])) {		// Upload all logs
            uploadSimpleLog(MyRangeMIDlet.out.getAll());
		}

        else if (cmd.equals(StringConsts.S_CONSCDM[StringConsts.I_CONSCDM_OUT_SHOW])) {		    // Show programm out
			
			int num = MyRoutines.strToInt(arg);
			int max = MyRangeMIDlet.out.numLines();
			if (num > max) num = max;
			
			if (num == -1) {		// All logs
				MyRangeMIDlet.console.println(MyRangeMIDlet.out.getAll());
			} else {				// Only last num logs
				int first = max - num + 1;
				MyRangeMIDlet.console.println(MyRangeMIDlet.out.getLines(first, max));
			}
		}

        else if (command.equals("")) {	// Just a new line
			
		}

        else {	// Unknown command
			MyRangeMIDlet.console.println(StringConsts.S_CONSCDM_UNKNOWN_1 + cmd + StringConsts.S_CONSCDM_UNKNOWN_2);
		}
		
		MyRangeMIDlet.console.startEnter();
		//MyMIDlet.console.show();
	}
    */


    // SOAP authorize
    public static synchronized Integer auth(String login, String hash) throws Exception {
        HeaderType header = new HeaderType(login, hash);
        AuthRequestElement auchRequest = new AuthRequestElement(header);
        AuthResponseElement authResponse = Scrobbler.auth(auchRequest);
        return authResponse.getResponse();
    }

    /**
     * SOAP register.disabled
     *
     * @return user password
     */
//    public static synchronized String register(String login, String firstName, String lastName, Vector confIds) throws Exception {
//        RegisterRequestElement registerRequest = new RegisterRequestElement(login, firstName, lastName, confIds);
//        RegisterResponseElement registerResponse = Scrobbler.register(registerRequest);
//        return registerResponse.getPassword();
//    }

    // SOAP validatePhoneNumber
    public static synchronized String validatePhoneNumber(String phoneNumber) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        ValidatePhoneNumberRequestElement validatePhoneNumberRequest = new ValidatePhoneNumberRequestElement(header, phoneNumber);
        ValidatePhoneNumberResponseElement validatePhoneNumberResponse = Scrobbler.validatePhoneNumber(validatePhoneNumberRequest);
        return validatePhoneNumberResponse.getValidationCode();
    }

    // SOAP setDeviceInfo
    public static synchronized Integer setDeviceInfo() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        //String phoneNumber = Records.recSettings.readStringData(Records.REC_PHONE);

        SetDeviceInfoRequestElement setDeviceInfoRequest = new SetDeviceInfoRequestElement(header, StringConsts.S_CLIENT_VERSION,
                MyRangeMIDlet.bluetooth.getBtAddress(), MyRangeMIDlet.bluetooth.getDeviceClass(), MyRangeMIDlet.bluetooth.getFriendlyName(),
                MyRangeMIDlet.imei, MyRangeMIDlet.platform, MyRangeMIDlet.platform, MyRangeMIDlet.platform);
        SetDeviceInfoResponseElement setDeviceInfoResponse = Scrobbler.setDeviceInfo(setDeviceInfoRequest);
        return setDeviceInfoResponse.getResponse();
    }

    // Get phone number from server. Use SOAP getUserInfo
    /*
    public static synchronized String getPhoneNumber() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        Vector param = new Vector();
        param.addElement("phoneNumber");

        UserInfo myProfile = new UserInfo(Records.recSettings.readByteData(Records.REC_PROFILE));

        Vector userIdVector = new Vector();
        userIdVector.addElement(myProfile.getUserId());
        GetUserInfoRequestElement getUserInfoRequest =
                new GetUserInfoRequestElement(header, param, null, userIdVector, null);

        // SOAP getUserInfo
        GetUserInfoResponseElement userInfoResponse = Scrobbler.getUserInfo(getUserInfoRequest);
        Vector users = userInfoResponse.getUsersInfo();
        UserInfo userProfile = null;
        if (users.size() > 0) {
            userProfile = (UserInfo) users.elementAt(0);
        }
        if (userProfile == null) return null;
        return userProfile.getPhoneNumber();
    }
    */

    // SOAP setUserInfo
    public static synchronized Integer setMyUserInfo() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        if (Users.getMyProfile() == null) throw new IllegalStateException("User not found!");

        SetUserInfoRequestElement setUserInfoRequest = new SetUserInfoRequestElement(header, Users.getMyProfile());
        SetUserInfoResponseElement setUserInfoResponse = Scrobbler.setUserInfo(setUserInfoRequest);
        return setUserInfoResponse.getResponse();
    }

    // SOAP sendBtMeets: send all btMeets to the server
    public static synchronized Integer sendBtMeets() throws Exception {
        if (Records.recBtMeets.getNumRec()<1) { return new Integer(0); }

        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        // Send by MyConsts.I_MAX_BTMEETS_FOR_SOAP (~ 100) btMeets
        int numSend;
        int response = 0;
        while (Records.recBtMeets.getNumRec() > 0) {
            if (Records.recBtMeets.getNumRec() > StringConsts.I_MAX_BTMEETS_FOR_SOAP) {
                numSend = StringConsts.I_MAX_BTMEETS_FOR_SOAP;
            } else {
                numSend = Records.recBtMeets.getNumRec();
            }

            // Create vector of bmMeets to send
            Vector btMeetVector = new Vector();
            for (int i=1; i<=numSend; i++) {
                BtMeet btMeet = new BtMeet(Records.recBtMeets.readByteData(i));
                btMeetVector.addElement(btMeet);
            }

            // SOAP sending
            SendBtMeetsRequestElement sendBtMeetsRequest = new SendBtMeetsRequestElement(header, MyRangeMIDlet.bluetooth.getBtAddress(),
                    MyRangeMIDlet.bluetooth.getDeviceClass(), MyRangeMIDlet.bluetooth.getFriendlyName(), btMeetVector);
            SendBtMeetsResponseElement sendBtMeetsResponse = Scrobbler.sendBtMeets(sendBtMeetsRequest);

            // Summarize total response
            response += sendBtMeetsResponse.getResponse().intValue();

            // Delete records
            int oldNumRec = Records.recBtMeets.getNumRec();
            Records.recBtMeets.eraseRecords(1, numSend);
            if (oldNumRec <= Records.recBtMeets.getNumRec()) return null; // if return if infinite loop
       }

       return new Integer(response);    // Return number of send btMeets
    }

    // SOAP getSmsServiceInfo
    public static synchronized SmsServiceInfo getSmsServiceInfo(String codeName) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        GetSmsServiceInfoRequestElement getSmsServiceInfoRequest = new GetSmsServiceInfoRequestElement(header, codeName, MyRangeMIDlet.bluetooth.getBtAddress());
        return Scrobbler.getSmsServiceInfo(getSmsServiceInfoRequest);
    }

    // SOAP getSmsMessages
    public static synchronized Vector getSmsMessages(Long startTime, Long endTime) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        GetSmsMessagesRequestElement getSmsMessagesRequest = new GetSmsMessagesRequestElement(header, startTime, endTime);
        GetSmsMessagesResponseElement getSmsMessagesResponse = Scrobbler.getSmsMessages(getSmsMessagesRequest);
        return getSmsMessagesResponse.getSmsMessages();
    }

    // SOAP getPrivateMessages. Get number new messages in preview mode.
    public static synchronized Vector getPrivateMessages(Integer userId, long lastUpdatedTime, Integer number) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        GetPrivateMessagesRequestElement getPrivateMessagesRequest = new GetPrivateMessagesRequestElement(header, userId, new Boolean(true), lastUpdatedTime, number);
        GetPrivateMessagesResponseElement getPrivateMessagesResponse = Scrobbler.getPrivateMessages(getPrivateMessagesRequest);
        return getPrivateMessagesResponse.getPrivateMessages();
    }

    // SOAP getPrivateMessages. Read target messages.
    public static synchronized Vector getPrivateMessage(Vector privateMsgId, Boolean preview) throws Exception {
        if (privateMsgId == null || privateMsgId.size() < 1) return new Vector();
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        GetPrivateMessagesRequestElement getPrivateMessagesRequest = new GetPrivateMessagesRequestElement(header, preview, privateMsgId);
        GetPrivateMessagesResponseElement getPrivateMessagesResponse = Scrobbler.getPrivateMessages(getPrivateMessagesRequest);
        return getPrivateMessagesResponse.getPrivateMessages();
    }

    // SOAP sendPrivateMessage
    public static synchronized Integer sendPrivateMessage(Integer to, String subject, String body) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        SendPrivateMessageRequestElement sendPrivateMessageRequest = new SendPrivateMessageRequestElement(header, to, subject, body);
        SendPrivateMessageResponseElement sendPrivateMessageResponse = Scrobbler.sendPrivateMessage(sendPrivateMessageRequest);
        return sendPrivateMessageResponse.getResponse();
    }

    // SOAP getLatestClientVersion
    public static synchronized GetLatestClientVersionResponseElement getLatestClientVersion() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        GetLatestClientVersionRequestElement getLatestClientVersionRequest =
                new GetLatestClientVersionRequestElement(header, StringConsts.S_CLIENT_VERSION);
        return Scrobbler.getLatestClientVersion(getLatestClientVersionRequest);
    }

    /* SOAP ConferenceInfo.
     *
     */
    public static synchronized Vector getConferenceInfo() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        GetConferenceInfoRequest getConferenceInfoRequest = new GetConferenceInfoRequest(header);
        GetConferenceInfoResponse getConferenceInfoResponse = Scrobbler.getConferenceInfo(getConferenceInfoRequest);
        return getConferenceInfoResponse.getConfInfo();
    }

    /* Events
     *
     */
    public static synchronized Vector getEvents() throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        //TODO: delete this hardcode!
        Integer confId = MyConferences.getActualConf().getConfId();

        GetEventsRequest getEventsRequest = new GetEventsRequest(header, confId);
        GetEventsResponse getEventsResponse = Scrobbler.getEvents(getEventsRequest);
        return getEventsResponse.getEvents();
    }

    /*
     * Work with Companies
     */
    public static synchronized Integer[] searchCompaniesIds() throws Exception{
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        Integer confId = MyConferences.getActualConf().getConfId();

        SearchCompanyRequest searchCompanyRequest = new SearchCompanyRequest(header, confId);
        SearchCompanyResponse searchCompanyResponse = Scrobbler.searchCompanies(searchCompanyRequest);

        Vector ids = searchCompanyResponse.getCompanyIDs();
        Integer[] IDs = new Integer[ids.size()];
        for(int i = 0; i < ids.size(); i++){
            IDs[i] = (Integer)ids.elementAt(i);
        }

        return IDs;
    }


   public static synchronized Vector getCompanies(Integer[] compIDs, Integer picSize) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        //TODO: delete hardcode 48!
        GetCompanyInfoRequest getCompanyInfoRequest = new GetCompanyInfoRequest(header, compIDs, picSize);
        GetCompanyInfoResponse getCompanyInfoResponse = Scrobbler.getCompanies(getCompanyInfoRequest);
        return getCompanyInfoResponse.getCompanies();
    }

    public static synchronized Integer callUser(Integer userId) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        ConferenceCallRequest conferenceCallRequest = new ConferenceCallRequest(header, userId);
        //System.err.println("Created request");
        //TODO: add processing status of call
        ConferenceCallResponse callResponse = Scrobbler.initConferenceCall(conferenceCallRequest);
       
        System.err.println("[Call status: ]" + callResponse.getCallStatus());

        if(callResponse.getCallStatus().intValue() == 1)
            MyRangeMIDlet.showInfoAlert(StringConsts.CALL_SUCCESS);

        return callResponse.getCallStatus();
    }

    public static synchronized Integer callCompany(Integer compId) throws Exception {
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);

        ConferenceCallRequest conferenceCallRequest = new ConferenceCallRequest(header, null, compId);
        //TODO: add processing status of call
        ConferenceCallResponse callResponse = Scrobbler.initConferenceCall(conferenceCallRequest);
        System.err.println("[Call status: ]" + callResponse.getCallStatus());

       if(callResponse.getCallStatus().intValue() == 1)
            MyRangeMIDlet.showInfoAlert(StringConsts.CALL_SUCCESS);

         return callResponse.getCallStatus();
    }
    /*
     * End work with companies
     */

    /*
     * Business Meets
     * 
     */
    static Vector getBusinessMeets() throws Exception {
         HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        //TODO: delete this hardcode!
        Integer confId = MyConferences.getActualConf().getConfId();

        GetBusinessMeetsRequest getBusinessMeetsRequest = new GetBusinessMeetsRequest(header, confId);
        GetBusinessMeetsResponse getBusinessMeetsResponse = Scrobbler.getBusinessMeets(getBusinessMeetsRequest);
        return getBusinessMeetsResponse.getBusinessMeets();
    }

    // SOAP recoverPassword. Recover the password.
    public static synchronized Integer recoverPassword(String login) throws Exception {
        RecoverPasswordRequest recoverPasswordRequest = new RecoverPasswordRequest(login);
        RecoverPasswordResponse recoverPasswordResponse = Scrobbler.recoverPassword(recoverPasswordRequest);
        return recoverPasswordResponse.getResult();
    }

    // SOAP uploadSimpleLog
    public static synchronized void uploadSimpleLog(String entry) throws Exception {

        UploadSimpleLogRequestElement uploadSimpleLogRequest = new UploadSimpleLogRequestElement(MyRangeMIDlet.bluetooth.getBtAddress(), entry);
        Scrobbler.uploadSimpleLog(uploadSimpleLogRequest);

        // Erase all logs
        MyRangeMIDlet.out.erase();
    }


    /* START mamba services */
    /*
    // SOAP mamba authorize
    public static synchronized Integer mambaAuth(String login, String password) throws Exception {
        HeaderType header = new HeaderType(MyRange.login, MyRange.pass);
        MambaAuthRequestElement mambaAuthRequest = new MambaAuthRequestElement(header, login, password);
        MambaAuthResponseElement mambaAuthResponse = Scrobbler.mambaAuth(mambaAuthRequest);
        return mambaAuthResponse.getResponse();
    }

    // SOAP mamba register start
    public static synchronized MambaRegisterStartResponseElement mambaRegisterStart() throws Exception {
        HeaderType header = new HeaderType(MyRange.login, MyRange.pass);
        MambaRegisterStartRequestElement mambaRegisterStartRequest = new MambaRegisterStartRequestElement(header);
        MambaRegisterStartResponseElement mambaRegisterStartResponse = Scrobbler.mambaRegisterStart(mambaRegisterStartRequest);
        return mambaRegisterStartResponse;
    }

    // SOAP mamba register finish
    public static synchronized MambaRegisterFinishResponseElement mambaRegisterFinish(String sid,
            String secureCode, String mambaLogin, String mambaPassword, String name,
            String gender, String[]lookFor, Long birthday, String residentCity,
            String secretQuestion, String secretAnswer, String captchaCode) throws Exception {
        HeaderType header = new HeaderType(MyRange.login, MyRange.pass);
        MambaRegisterFinishRequestElement mambaRegisterFinishRequest =
                new MambaRegisterFinishRequestElement(header, sid, secureCode,
                mambaLogin, mambaPassword, name, gender, lookFor, birthday,
                residentCity, secretQuestion, secretAnswer, captchaCode);
        MambaRegisterFinishResponseElement mambaRegisterFinishResponse = Scrobbler.mambaRegisterFinish(mambaRegisterFinishRequest);
        return mambaRegisterFinishResponse;
    }
    */
    /* END mamba services */
    

    /**
     * Check authorize;
     * Save user profile;
     * Save my conferences info from the server;
     * Set device info, set this device to the registered state;
     * Start the main menu
     *
     * (ONLY FOR CALL FROM A SEPARATE THREAD)
     *
     * @param login
     * @param hash
     * @throws java.lang.Exception
     */
    public static void authAndStart(String login, String hash, final Dialog pleaseWait) throws Exception {
        // Check authorize
        Integer myUserId = null;
        try{
             myUserId = auth(login, hash);
        }catch(Exception e){
            throw new Exception("[Auth:]" + e.getMessage());
        }
        // Login Ok
        // Set login and pass
        MyRangeMIDlet.login = login;
        MyRangeMIDlet.pass = hash;
        MyRangeMIDlet.myUserId = myUserId;
        // Save login, password and ID
        try{
            Records.recSettings.addStringData(MyRangeMIDlet.login, Records.REC_LOGIN);
            Records.recSettings.addStringData(MyRangeMIDlet.pass, Records.REC_PASSWORD);
            Records.recSettings.addIntData(MyRangeMIDlet.myUserId.intValue(), Records.REC_MY_ID);
        }catch(Exception e){
            throw new Exception("[RMS:]" + e.getMessage());
        }

        // Get my profile from server
        if (!Users.downloadMyProfile()) {
            throw new Exception(StringConsts.S_USER_NOT_FOUND);
        }

        // Get and save my conferences info from server
        try{
            MyConferences.getMyConfs();
        }catch(Exception e){
            throw new Exception("[MyConferences:]" + e.getMessage());
        }

        // Set device info
        try {
            if (setDeviceInfo() != null) {
                // Set this device to the registered state
                Bluetooth.deviceRegistered = Records.recSettings.readBooleanData(Records.REC_DEVICE_REGISTERED);
                Records.recSettings.addBooleanData(true, Records.REC_DEVICE_REGISTERED);
            } else {
                // Set this device to the unregistered state
                Records.recSettings.addBooleanData(false, Records.REC_DEVICE_REGISTERED);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            System.out.println("[SetDeviceInfo] Error: " + ex.toString());
            MyRangeMIDlet.out.println("[SetDeviceInfo] Error: " + ex.getMessage());
            // TODO: change to error code
            if (!ex.getMessage().equals("Device already registered by sombody")) {
                // If another exception
                Exception e = new Exception("[Device ex:]" + ex.toString());
                throw e;
            }
        }

        //  Start main menu
        MyRangeMIDlet.staticMidlet.init(true, pleaseWait);
    }

    /**
     * Update (download) my infos:
     * <ol>
	 * <li>Download profile from the server (Update profile);</li>
	 * <li>Download my conferences info from the server (Update my conferences);</li>
     * <li>Get and save favourite users from server (Update favourites);</li>
     * <li>Get and save signals users from server (Update signals);</li>
     * <li>Load private messages and smses from RMS.</li>
	 * </ol>
     * Should to call one time at the start.
     */
    public static void initMyInfos() {
        (new Thread() {
            public void run() {
                // Try to update my profile
                try {
                    // Get my profile from the server
                    if (Users.downloadMyProfile()) {
                        throw new Exception(StringConsts.S_USER_NOT_FOUND);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MyRangeMIDlet.out.println("[MyProfile] Error: Cannot download my profile. " + e.getMessage());
                }

//                // Get and save my conferences info from server
//                try {
//                    MyConferences.getMyConfs();
//                } catch (Exception e){
//                    e.printStackTrace();
//                    MyRangeMIDlet.out.println("[MyConferences] Error: " + e.getMessage());
//                }

                // Get and save favourite users info from server
                try {
                    Favourites.download();
                } catch (Exception e){
                    e.printStackTrace();
                    MyRangeMIDlet.out.println("[Favourites] Error: " + e.getMessage());
                }

                // Get and save signals users info from server
                try {
                    Signals.download();
                } catch (Exception ex){
                    ex.printStackTrace();
                    MyRangeMIDlet.out.println("[Signals] Error: " + ex.getMessage());
                }

                try {
                    Companies.getCompanies();
                } catch (Exception ex){
                    ex.printStackTrace();
                    MyRangeMIDlet.out.println("[Company] Error: " + ex.getMessage());
                }

                try{
                    SearchUsers.newSearch(MyConferences.getActualConf().getConfId());
                } catch (Exception ex){
                    ex.printStackTrace();
                    MyRangeMIDlet.out.println("[SearchConferenceUsers] Error: " + ex.getMessage());
                }
                
            }
        }).start();

        (new Thread() {
            public void run() {                
                    Events.getMyEvents();
                    BusinessMeets.getBusinessMeets();
            }}).start();
    }


    /**
     * Check and upload changes in my infos to the server:
     * <ol>
	 * <li>Upload my profile to the server (Update profile);</li>
     * <li>Upload favourite users to server (Update favourites);</li>
     * <li>Upload signals users to server (Update signals);</li>
     * </ol>
     */
    public static void uploadMyInfos() {
        (new Thread() {
            public void run() {
                // Try to upload my profile
                try {
                    if (Records.recSettings.readIntData(Records.REC_PROFILE_CHANGED) == Records.I_CHANGED_YES) {
                        Integer setUserInfoResponse = StaticActions.setMyUserInfo();
                        Records.recSettings.addIntData(Records.I_CHANGED_NOT, Records.REC_PROFILE_CHANGED);
                        MyRangeMIDlet.out.println("[MyProfile] " + setUserInfoResponse.toString() + " upload my user info ...OK");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MyRangeMIDlet.out.println("[MyProfile] Error: Cannot upload my profile. " + e.getMessage());
                }

                // Try to upload favourite users. disabled for time
//                try {
//                    if (Records.recSettings.readIntData(Records.REC_FAVOURITES_CHANGED) == Records.I_CHANGED_YES) {
//                        Favourites.upload();
//                        Records.recSettings.addIntData(Records.I_CHANGED_NOT, Records.REC_FAVOURITES_CHANGED);
//                        MyRangeMIDlet.out.println("[Favourites]  upload favourites ...OK");
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                    MyRangeMIDlet.out.println("[Favourites] Error: " + e.getMessage());
//                }
            }
        }).start();
    }

    /**
     * Get one actor from the remote server.
     *
     * @param btAddress
     * @return UserInfo - profile of user or null
     * @throws java.lang.Exception
     */
    public static synchronized Actor getActor(String btAddress) throws Exception
    {
        MyRangeMIDlet.out.println("[getActor] Getting actor with BtID=" + btAddress);
        if (btAddress == null) return null;
        HeaderType header = new HeaderType(MyRangeMIDlet.login, MyRangeMIDlet.pass);
        Vector btAddressVector = new Vector();
        btAddressVector.addElement(btAddress);
        GetActorInfoRequest actorInfoRequest = new GetActorInfoRequest(header, btAddressVector);
        // SOAP getActorInfo
        GetActorInfoResponse actorInfoResponse = Scrobbler.getActorInfo(actorInfoRequest);
        Vector actors = actorInfoResponse.getUserActors();
        Actor res = null;
        // return the first actor from vector
        if (actors.size() > 0) {
            res = (Actor) actors.elementAt(0);
        }
        return res;
    }

}
