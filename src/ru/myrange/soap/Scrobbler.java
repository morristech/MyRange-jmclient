package ru.myrange.soap;

import java.util.Calendar;
import java.util.Date;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;
import org.ksoap2.serialization.KvmSerializable;
import ru.myrange.datastore.Records;
import ru.myrange.stuff.StringConsts;

//import ru.myrange.soap.mamba.*;
public class Scrobbler {

    /**
     * SOAP Namespace
     */
    public static final String namespace = "http://soap.myrange.ru";
    /**
     * Total number of bytes has been send
     */
    private static int numSendBytes = 0;
    /**
     * Total number has been read
     */
    private static int numReadBytes = 0;

    private static Object invoke(String operationName, String elementName, Class clazz, KvmSerializable obj) throws Exception {
        HttpTransport httpt = new HttpTransport(StringConsts.S_SCROBBLER_URL);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.addMapping(namespace, elementName, clazz);
        envelope.setOutputSoapObject(obj);

        try {
            // Set debug mode
            //httpt.debug = true;
            // Call
            httpt.call(operationName, envelope);
            numSendBytes += httpt.getNumSendBytes();
            numReadBytes += httpt.getNumReadBytes();
            /*
            MyRange.out.println("[HttpTransport] " + Integer.toString(httpt.getNumSendBytes()) +
            "[" + Integer.toString(numSendBytes) + "] bytes has been send");
            MyRange.out.println("[HttpTransport] " + Integer.toString(httpt.getNumReadBytes()) +
            "[" + Integer.toString(numReadBytes) + "] bytes has been read");
             */
        } catch (SoapFault fault) {
            String string = null;
            try {
                //String code = fault.detail.getElement(0).getElement(0).getElement(0).getText(0);
                string = fault.detail.getElement(0).getElement(0).getElement(1).getText(0);
            } catch (Exception e) {         // Unexpected error
                if(fault.faultstring.startsWith("Argument \'numberAsString\' cannot be null or empty"))
                    throw new Exception("У этого пользователя нет зарегестрированного номера");
                else
                    throw new Exception(fault.faultstring);
            }
            throw new Exception(string);    // Normal fault
        }

        return envelope.bodyIn;
    }

    /* For http://alexbobkov.studenthost.ru/LogService.asmx */
    // TODO remove this.
    private static Object invoke2(String operationName, String elementName, Class clazz, KvmSerializable obj) throws Exception {
        HttpTransport httpt = new HttpTransport("http://alexbobkov.studenthost.ru/LogService.asmx");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.addMapping("http://alexbobkov.studenthost.ru/", elementName, clazz);
        envelope.setOutputSoapObject(obj);

        try {
            httpt.call(operationName, envelope);
        } catch (SoapFault fault) {
            String string = null;
            try {
                //String code = fault.detail.getElement(0).getElement(0).getElement(0).getText(0);
                string = fault.detail.getElement(0).getElement(0).getElement(1).getText(0);
            } catch (Exception e) {         // Unexpected error
                throw new Exception(fault.faultstring);
            }
            throw new Exception(string);    // Normal fault
        }

        return envelope.bodyIn;
    }

    /* WSDL-declared methods implementation */
    // echo
    public static EchoStringOutElement echo(EchoStringInElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/echo";
        String elementName = "echoStringInElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new EchoStringOutElement((SoapObject) response);
    }

    // register
    public static RegisterResponseElement register(RegisterRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/register";
        String elementName = "registerRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new RegisterResponseElement((SoapObject) response);
    }

    // authorize
    public static AuthResponseElement auth(AuthRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/auth";
        String elementName = "authRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new AuthResponseElement((SoapObject) response);
    }

    /**
     * getActorInfo
     */
    public static GetActorInfoResponse getActorInfo(GetActorInfoRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getActorInfo";
        String elementName = "getActorInfoRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetActorInfoResponse((SoapObject) response);
    }

    /**
     * getUserInfo
     */
    public static GetUserInfoResponseElement getUserInfo(GetUserInfoRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getUserInfo";
        String elementName = "getUserInfoRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetUserInfoResponseElement((SoapObject) response);
    }

    // setUserInfo
    public static SetUserInfoResponseElement setUserInfo(SetUserInfoRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/setUserInfo";
        String elementName = "setUserInfoRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SetUserInfoResponseElement((SoapObject) response);
    }

    // searchUsers
    public static SearchUserResponseElement searchUsers(SearchUserRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/searchUser";
        String elementName = "searchUserRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SearchUserResponseElement((SoapObject) response);
    }

    // getDeviceInfo
    public static GetDeviceInfoResponseElement getDeviceInfo(GetDeviceInfoRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getDeviceInfo";
        String elementName = "getDeviceInfoRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetDeviceInfoResponseElement((SoapObject) response);
    }

    // setDeviceInfo
    public static SetDeviceInfoResponseElement setDeviceInfo(SetDeviceInfoRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/setDeviceInfo";
        String elementName = "setDeviceInfoRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SetDeviceInfoResponseElement((SoapObject) response);
    }

    // sendBtMeets
    public static SendBtMeetsResponseElement sendBtMeets(SendBtMeetsRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/sendBtMeets";
        String elementName = "sendBtMeetsRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SendBtMeetsResponseElement((SoapObject) response);
    }

    // getSmsServiceInfo
    public static SmsServiceInfo getSmsServiceInfo(GetSmsServiceInfoRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getSmsServiceInfo";
        String elementName = "getSmsServiceInfoRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SmsServiceInfo((SoapObject) response);
    }

    // getSmsMessages
    public static GetSmsMessagesResponseElement getSmsMessages(GetSmsMessagesRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getSmsMessages";
        String elementName = "getSmsMessagesRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetSmsMessagesResponseElement((SoapObject) response);
    }

    // getPrivateMessages
    public static GetPrivateMessagesResponseElement getPrivateMessages(GetPrivateMessagesRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getPrivateMessages";
        String elementName = "getPrivateMessagesRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetPrivateMessagesResponseElement((SoapObject) response, obj.getPreview());
    }

    // sendPrivateMessage
    public static SendPrivateMessageResponseElement sendPrivateMessage(SendPrivateMessageRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/sendPrivateMessage";
        String elementName = "sendPrivateMessageRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SendPrivateMessageResponseElement((SoapObject) response);
    }

    // getLatestClientVersion
    public static GetLatestClientVersionResponseElement getLatestClientVersion(GetLatestClientVersionRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getLatestClientVersion";
        String elementName = "getLatestClientVersionRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetLatestClientVersionResponseElement((SoapObject) response);
    }

    // validatePhoneNumber
    public static ValidatePhoneNumberResponseElement validatePhoneNumber(ValidatePhoneNumberRequestElement obj) throws Exception {
        String operationName = "http://soap.myrange.ru/validatePhoneNumber";
        String elementName = "validatePhoneNumberRequestElement";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new ValidatePhoneNumberResponseElement((SoapObject) response);
    }

    /**
     * getConferenceInfo
     */
    public static GetConferenceInfoResponse getConferenceInfo(GetConferenceInfoRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getConferenceInfo";
        String elementName = "getConferenceInfoRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetConferenceInfoResponse((SoapObject) response);
    }

    /**
     * getFavorites
     */
    public static GetFavoritesResponse getFavorites(GetFavoritesRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getFavorites";
        String elementName = "getFavoritesRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetFavoritesResponse((SoapObject) response);
    }

    /**
     * editFavorites
     */
    public static EditFavoritesResponse editFavorites(EditFavoritesRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/editFavorites";
        String elementName = "editFavoritesRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new EditFavoritesResponse((SoapObject) response);
    }

    /**
     * getSignals
     */
    public static GetSignalsResponse getSignals(GetSignalsRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getSignals";
        String elementName = "getSignalsRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetSignalsResponse((SoapObject) response);
    }

    /**
     * editSignals
     */
    public static EditSignalsResponse editSignals(EditSignalsRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/editSignals";
        String elementName = "editSignalsRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new EditSignalsResponse((SoapObject) response);
    }

    /**
     * recoverPassword
     */
    public static RecoverPasswordResponse recoverPassword(RecoverPasswordRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/recoverPassword";
        String elementName = "recoverPasswordRequest";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new RecoverPasswordResponse((SoapObject) response);
    }

    // uploadSimpleLog
    public static void uploadSimpleLog(UploadSimpleLogRequestElement obj) throws Exception {
        String operationName = "http://alexbobkov.studenthost.ru/UploadSimpleLog";
        String elementName = "UploadSimpleLog";

        obj.pack(); //Pack element
        Object response = invoke2(operationName, elementName, obj.getClass(), obj); //Send element
    }

    /* START mamba services */
    /*
    // mambaAuth
    public static MambaAuthResponseElement mambaAuth(MambaAuthRequestElement obj) throws Exception {
    String operationName = "http://soap.myrange.ru/mambaAuth";
    String elementName = "mambaAuthRequestElement";

    obj.pack(); //Pack element
    Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
    if (response == null) return null;
    return new MambaAuthResponseElement((SoapObject) response);
    }

    // mambaRegisterStart
    public static MambaRegisterStartResponseElement mambaRegisterStart(MambaRegisterStartRequestElement obj) throws Exception {
    String operationName = "http://soap.myrange.ru/mambaRegisterStart";
    String elementName = "mambaRegisterStartRequestElement";

    obj.pack(); //Pack element
    Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
    if (response == null) return null;
    return new MambaRegisterStartResponseElement((SoapObject) response);
    }

    // mambaRegisterFinish
    public static MambaRegisterFinishResponseElement mambaRegisterFinish(MambaRegisterFinishRequestElement obj) throws Exception {
    String operationName = "http://soap.myrange.ru/mambaRegisterFinish";
    String elementName = "mambaRegisterFinishRequestElement";

    obj.pack(); //Pack element
    Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
    if (response == null) return null;
    return new MambaRegisterFinishResponseElement((SoapObject) response);
    }
     */
    /* END mamba services */
    /**
     * Getting number of send bytes
     *
     * @return
     */
    public static int getNumSendBytes() {
        return numSendBytes;
    }

    /**
     * Getting number of read bytes
     *
     * @return
     */
    public static int getNumReadBytes() {
        return numReadBytes;
    }

    public static GetEventsResponse getEvents(GetEventsRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getEvents";
        String elementName = "getEventsRequest";//recoverPasswordRequest getEventsRequest
        //System.out.println(obj.getHeader().getLogin());

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetEventsResponse((SoapObject) response);
    }

    public static GetBusinessMeetsResponse getBusinessMeets(GetBusinessMeetsRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getBusinessMeets";
        String elementName = "getBusinessMeetsRequest";
        //System.out.println(obj.getHeader().getLogin());

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetBusinessMeetsResponse((SoapObject) response);
    }

    public static SearchCompanyResponse searchCompanies(SearchCompanyRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/searchCompany";
        String elementName = "searchCompany";

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new SearchCompanyResponse((SoapObject) response);
    }

    public static GetCompanyInfoResponse getCompanies(GetCompanyInfoRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/getCompanyInfo";
        String elementName = "getCompanyInfoRequest";//recoverPasswordRequest getEventsRequest

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new GetCompanyInfoResponse((SoapObject) response);
    }

    /*
     * Conference calls
     */
    public static ConferenceCallResponse initConferenceCall(ConferenceCallRequest obj) throws Exception {
        String operationName = "http://soap.myrange.ru/conferenceCall";
        String elementName = "conferenceCallRequest";//recoverPasswordRequest getEventsRequest

        obj.pack(); //Pack element
        Object response = invoke(operationName, elementName, obj.getClass(), obj); //Send element
        if (response == null) {
            return null;
        }
        return new ConferenceCallResponse((SoapObject) response);
    }

    /**
     * Update traffic meters in Record Store
     *
     * @throws java.lang.Exception
     */
    public synchronized static void dumpTrafficToRMS() throws Exception {
        int curTraf = (getNumReadBytes() + getNumSendBytes()) / 1024;
        int todayTraf = Records.recSettings.readIntData(Records.REC_TRAFFIC_TODAY) + curTraf;
        int totalTraf = Records.recSettings.readIntData(Records.REC_TRAFFIC_TOTAL) + curTraf;

        if ((System.currentTimeMillis() - Records.recSettings.readLongData(Records.REC_TRAFFIC_TODAY_SINCE)) / 86400000 > 0) {
            // Dump today traffic
            todayTraf = curTraf;
            // Renew the day of taday in RMS
            Calendar cur = Calendar.getInstance();
            cur.setTime(new Date(System.currentTimeMillis()));
            Calendar beginOfDay = Calendar.getInstance();
            beginOfDay.set(Calendar.YEAR, cur.get(Calendar.YEAR));
            beginOfDay.set(Calendar.MONTH, cur.get(Calendar.MONTH));
            beginOfDay.set(Calendar.DAY_OF_MONTH, cur.get(Calendar.DAY_OF_MONTH));
            Records.recSettings.addLongData(beginOfDay.getTime().getTime(), Records.REC_TRAFFIC_TODAY_SINCE);
        }

        // Update traffic meter in RMS
        Records.recSettings.addIntData(todayTraf, Records.REC_TRAFFIC_TODAY);
        Records.recSettings.addIntData(totalTraf, Records.REC_TRAFFIC_TOTAL);

        // Dump meters
        numSendBytes = 0;
        numReadBytes = 0;
    }
}
