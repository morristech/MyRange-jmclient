/*
 * myRange J2ME client application
 * Copyright © 2009-2010 MyRange Group
 */
package ru.myrange.stuff;

/**
 * Diclaration of the string constants
 *
 * @author Oleg Ponfilenok, Yegorov Nickolay
 */
public class StringConsts {

    /*** #### APP CONSTANTS #### ***/
    //public static final String S_CHARSET_NAME = "ISO-8859-5";
    //public static final String S_CHARSET_NAME = "UTF-8";
    /*** #### CONSTANTS #### ***/
    /*
    public javax.microedition.lcdui.Image loadImage(String location) {
    try {
    return javax.microedition.lcdui.Image.createImage(location);
    } catch (java.io.IOException e) {
    throw new RuntimeException("Unable to load Image: " + e);
    }
    }
     */
    // *** ABOUT ***
    public static final String S_RELEASE_VERSION = "0.3.0";
//#if FP2009 == "true"
//#     public static final String S_CLIENT_COMPONENT = "jmclient-fp2009";
//#else
    public static final String S_CLIENT_COMPONENT = "jmclient";
//#endif
    public static final String S_CLIENT_VERSION = S_CLIENT_COMPONENT + " " + S_RELEASE_VERSION;
//#if RELEASE == "true"
//#     // RELEASE VERSION
//#     public static final String S_COPYRIGHT =
//#             "myRange " + S_CLIENT_VERSION + " \n"
//#             + "Created by myRange Group, 2009. \n"
//#             + "Please, visit our website: myrange.ru";
//#             //+"Copyright (c) 2009 Oleg Ponfilenok. All rights reserved.\n";
//# 
//#      public static final String S_ABOUT_INFO = "MyRange - мобильный сервис знакомств и общения.";
//# 
//#     // Release version of WSDL URL
//#     public static final String S_SCROBBLER_URL = "http://myrange.ru/scrobbler/services/Scrobbler";
//#else
    // TEST (DEVELOPMENT) VERSION
    public static final String S_COPYRIGHT =
            "!THIS IS A TEST VERSION! \n"
            + "MyRange " + S_CLIENT_VERSION + " \n"
            + "Created by myRange Group, 2009. \n";
    //+ "Please, visit our website: dev.myrange.ru/site";
    //+"Copyright (c) 2009 Oleg Ponfilenok. All rights reserved.\n";
    public static final String S_ABOUT_INFO = "MyRange - сервис деловых знакомств и общения на профессиональных мероприятиях.";
    // Test (development) version of WSDL URL
    public static final String S_SCROBBLER_URL = "http://myrange.ru:8180/scrobbler/services/Scrobbler";
//#endif
    public static final String S_ABOUT_VERSION = "Версия: " + S_RELEASE_VERSION;
    public static final String S_OUR_GOAL = "Наша цель - сделать общение в рамках конференций и форумов легким, удобным и эффективным.\n Вы всегда можете воспользоваться веб-версией системы по адресу www.myrange.ru.";
    public static final String S_ABOUT_CONTACTS = "По всем вопросам, связанным с работой сервиса обращайтесь на стол регистрации (во время конференции) или на support@myrange.ru.";
    public static final String S_ABOUT_FEEDBACK = "Нам интересно ваше мнение о работе сервиса! Оставить свой отзыв можно из этой формы:";
    /*** Conferences Id ***/
//#if RELEASE == "true"
//#     public static final Integer I_GLOBAL_CONF_ID = new Integer(415);
//#else
    public static final Integer I_GLOBAL_CONF_ID = new Integer(428);
//#endif
//#if FP2009 == "true"
//#     // Forum of winners conference id
    //#if RELEASE == "true"
//#         public static final Integer I_CONF_ID = new Integer(418);
    //#else
//#         public static final Integer I_CONF_ID = new Integer(456);
    //#endif
//#endif
    public static final String S_FEEDBACK_SUBJECT = "Feedback " + S_CLIENT_VERSION;
    // *** SMS ***
    public static final int I_SMS_LENGTH = 70;              // Max length of 1 sms message
    public static final int I_SMS_REAL_TEXT_LENGTH = 50;    // Max length of usefull text in 1 sms message
    // *** STRING CONSTS ***
    //public static final String S_MY_RANGE = "myRange";
    public static final String S_GO_TO_EVENT_URL = "перейти на сайт мероприятия";
    public static final String S_MENU = "Menu";
    public static final String S_OK = "OK";
    public static final String S_SAVE = "Сохранить";
    public static final String S_YES = "Да";
    public static final String S_FAIL = "Fail";
    public static final String S_CANCEL = "Отмена";
    public static final String S_EXIT = "Выйти";
    public static final String S_CLOSE = "Закрыть";
    public static final String S_EXIT_ALERT = "Вы точно хотите выйти?";
    public static final String S_LOGOUT_ALERT = "Вы точно хотите разлогиниться?";
    public static final String S_LOGOUT = "Разлогиниться";
    public static final String S_SYNC_ALERT = "Вы точно хотите отправить все логи на сайт?";
    public static final String S_HIDE = "Свернуть";
    public static final String S_HIDE_DIALOG = "Скрыть";
    public static final String S_BACK = "Назад";
    public static final String S_NEXT = "Далее";
    public static final String S_ENTERBOX = "Консольный ввод";
    public static final String S_CALENDAR = "Календарь";
    public static final String S_ENTER = "Enter";
    public static final String S_ABOUT = "О программе";
    public static final String S_TRAFFIC_LABEL = "Потребление траффика:";
    public static final String S_TRAFFIC_TODAY = "Сегодня: ";
    public static final String S_TRAFFIC_AVERAGE = "В среднем за сутки: ";
    public static final String S_TRAFFIC_TOTAL = "Всего с ";
    public static final String S_TRAFFIC_UNIT = " Kb";
    public static final String S_TRAFFIC_UNIT_AVERAGE = " Kb/сутки";
    public static final String S_SCAN = "Сканировать";
    public static final String S_SCANNIGN = "Сканирование";
    public static final String S_SCANNIGN_FIN = "Сканирование закончено";
    public static final String S_START_SCAN_FAIL = "Новое сканирование не может быть начато!";
    public static final String S_SHOW_DEV = "Показать устройства";
    public static final String S_DEVICES = "Bluetooth-устройства";
    public static final String S_DEVICE_INFO = "Информация об устройстве";
    public static final String S_DEVICE_FNAME = "Имя:";
    public static final String S_DEVICE_BTID = "Адрес:";
    public static final String S_BT_LOGS = "BT логи";
    public static final String S_GET_DATE = "Get date:";
    public static final String S_FNAME_UKNOWN = "Unknown";
    public static final String S_LOG_ITSELF_NAME = "Сканирование";
    public static final String S_SEARCH_SERV = "Поиск сервиса";
    public static final String S_INITIALIZATION = "Инициализация";
    public static final String S_LOGGINGOUT = "Завершение работы";
    public static final String S_EXITING = "Завершение работы";
    public static final String S_LOADING = "Загрузка...";
    public static final String S_DOWNLOAD = "Загрузка";
    public static final String S_WAIT = "Подождите...";
    public static final String S_SEND_BT_MSG = "Bluetooth сообщение";
    public static final String S_ENTER_MSG = "Введите сообщение";
    public static final String S_SEND = "Отправить";
    public static final String S_SENDING = "Отправка";
    public static final String S_CONNECTING_TO_THE_SERVER = "Соединение с сервером";
    public static final String S_REFRESH = "Обновить";
    public static final String S_SEND_PRIVATE_MSG = "Отправить сообщение";
    public static final String S_READ = "Прочитать сообщение";
    public static final String S_REPLY = "Ответить";
    public static final String S_MORE = "Еще одно";
    public static final String S_MSG_NEXT = "Следующие 20";
    public static final String S_MSG_PREV = "Предыдущие 20";
    public static final String S_PRIVATE_MSG = "Cообщение";
    public static final String S_FROM = "От кого:";
    public static final String S_TO = "Кому:";
    public static final String S_TIME = "Время отправки:";
    public static final String S_SUBJECT = "Тема:";
    public static final String S_BODY = "Сообщение:";
    public static final String S_SEND_SMS = "Отрпавить СМС";
    public static final String S_SMS_TO_USERNAME = "СМС для ";
    public static final String S_BT_MSG_TO_USERNAME = "BTMsg для ";
    public static final String S_PRIVATE_MSG_TO_USERNAME = "ЛС для ";
    public static final String S_NO_USER_PRIVATE_MSG_FAIL_ALERT = "Пользователь не найден!";
    public static final String S_PRIVATE_NO_MESSAGES = "Нет сообщений";
    public static final String S_INBOX_PRIVATE_NO_MESSAGES = "Нет входящих сообщений";
    public static final String S_NO_INBOX_MESSAGES = "У Вас нет входящих сообщений";
    public static final String S_NO_OUTBOX_MESSAGES = "У Вас нет исходящих сообщений";
    public static final String S_SMS_NO_MESSAGES = "У Вас нет полученных СМС";
    public static final String S_DEVICELIST_NO_DEVICES = "Нет устройств поблизости";
    public static final String S_MEET_NO_USERS = "Вы еще не встречали других пользователей";
    public static final String S_FAVOURITE_NO_USERS = "У вас нет избранных пользователей";
    public static final String S_USER_NOT_FOUND = "Пользователь не найден";
    public static final String S_USER_NO_ENTERPRISE_USERS = "Вы еще не встречали других компаний";
    public static final String S_SMS_USER_ID_UNKNOWN = "Не возможно отправить СМС. ID пользователя неизвестен.";
    public static final String S_SMS_NO_USER_FAIL_ALERT = "Пользователь не найден!";
    public static final String S_SMS_SERVICE_MUST_UPDATE = "Данные устарели. Перед отправкой SMS необходимо выполнить синхронизицию!";
    public static final String S_SMS_COST_WARNING_1 = "Вы хотите отправить SMS? Стоимость sms-сообшения ";
    public static final String S_SMS_COST_WARNING_2 = " рублей в зависимости от вашего оператора.";
    public static final String S_PROFILE = "Профиль";
    public static final String S_SHOW_ALL = "   Все     ";
    public static final String S_SHOW_MEETS = "   Встречи   ";
    public static final String S_EVENT_CLICK = "События";
    public static final String S_MY_PROFILE = "Мой профиль";
    public static final String S_PROFILE_PERSONAL = "Профиль пользователя";
    public static final String S_PROFILE_ENTERPRISE = "Профиль компании";
    public static final String S_FROM_FILE = "Загрузить";
    public static final String S_CAPTURE = "Камера";
    public static final String S_SNAPSHOT = "Сфоткать";
    public static final String S_DOING_SNAPSHOT = "Делается снимок";
    public static final String S_SNAPSHOT_TRY_AGAIN = "Попробуйте еще раз!";
    public static final String S_PROFILE_CHANGED_OK = "Изменения в профайле успешно загружены на сервер";
    public static final String S_PROFILE_CHANGED_FAIL = "Связь с сервером временно недоступна. Изменения в профайле будут загружены на сервер позже.";
    public static final String S_ADD_TO_PHONEBOOK = "Добавить в адресную книгу";
    public static final String S_ADD_TO_PHONEBOOK_ASK = "Вы хотите добавить контакты этого пользователя в адресную книгу вашего телефона?";
    public static final String S_ADD_TO_PHONEBOOK_SUCCESS = " успешно добавлен в адресную книгу";
    public static final String S_ADD_TO_PHONEBOOK_PIM_EXCEPTION = "Не удалось найти адресную книгу";
    public static final String S_ADD_TO_PHONEBOOK_SECURITY_EXCEPTION = "Нет доступа к адресной книге";
    public static final String S_PHONE_CHANGED_OK = "Номер телефона успешно изменен";
    public static final String S_PHONE_CHANGED_FAIL = "Номер телефона не удалось передать на сервер";
    public static final String S_PHONECALL_TO = "Позвонить ";
    public static final String S_ERASE_USERLIST = "Очистить";
    public static final String S_ERASE_USERLIST_ALERT = "Вы действительно хотите очистить весь список пользователей?";
    public static final String S_IS_NEAR_ME_NOW = "Рядом!";
    public static final String S_NEVER_MEET = "Никогда не встречал";
    public static final String S_ADD_TO_FAVORITE = "Добавить в закладки";
    public static final String S_SEND_MSG_OK_ALERT = "Сообщение отправленно";
    public static final String S_SEND_MSG_NO_TEXT = "Введите текст сообщения";
    public static final String S_SEND_MSG_FAIL_ALERT = "Не удается отправить сообщение";
    public static final String S_NEW_MSG_ALERT = "У вас новое непрочитанное сообщение!";
    public static final String S_NEW_PERSONAL_USER_ALERT = "Вы встретили пользователя ";
    public static final String S_NEW_ENTERPRISE_USER_ALERT = "Вы встретили компанию ";
    public static final String S_SEARCH_SERV_FAIL_ALERT = "Сервис не найден!";
    public static final String S_NO_DEVICE_FAIL_ALERT = "Устройства нет поблизости!";
    public static final String S_SEND_BTLOGS_OK_ALERT_1 = "Информация о ";
    public static final String S_SEND_BTLOGS_OK_ALERT_2 = " встречах отправлена на сервер";
    //public static final String S_SEND_BTLOGS_FAIL_ALERT = "Не удается отправить информацию о встречах";
    public static final String S_SERVER_UNAVAILABLE_EXIT = "Извините, но приложение не может установить связь с сервером, поэтому вынужденно завершить работу.";
    public static final String S_DATA_WRONG_NUM = "Enter the numbers of the logs to send";
    public static final String S_SETTINGS_CHANGED_OK = "Настройки успешно изменены";
    public static final String S_SYNCHRONIZING = "Синхронизация";
    public static final String S_SYNCHRONIZING_OK = "Синхронизация прошла успешно";
    public static final String S_USER_SEARCH_NEW = "Новый поиск";
    public static final String S_USER_SEARCH_CONF = "Область поиска";
    public static final String S_USER_SEARCH_CONF_GLOBAL = "все";
    public static final String S_USER_SEARCH_CONF_LOCAL = "люди ";
    public static final String S_USERS_NEAR_HERE = "Люди рядом";
    public static final String S_USER_SEARCH_START = "Найти";
    public static final String S_USER_SEARCH_CONF_ALL_TITLE = "Все участники мероприятия";
    public static final String S_USER_SEARCH_GLOBAL_ALL_TITLE = "Все люди";
    public static final String S_PIC_PICTURE = "Выберите фотографию";
    public static final String S_ONLINE = "ОНЛАЙН";
    public static final String S_OFFLINE = "ОФФЛАЙН С ";
    public static final String S_DONT_USE_MYRANGE = "НЕ ИСПОЛЬЗУЕТ MYRANGE";
    public static final String S_SEND_FEEDBACK = "Отправить отзыв";
    public static final String S_SEND_FEEDBACK_OK = "Ваш отзыв отправлен. Спасибо!";
    public static final String S_SEND_FEEDBACK_FAIL = "Некому отправить отзыв :(";
    public static final String S_ALL_RECS = "all";
    public static final String S_MONTHS[] = {
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "аргуста",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    };
    public static final String S_DAY_OF_WEEKS[] = {
        "понедельник",
        "вторник",
        "среда",
        "четверг",
        "пятница",
        "суббота",
        "воскресенье"
    };
    public static final String S_NEW_VERSION_AVAILABLE = "Доступна новая версия клиента ";
    public static final String S_NEW_VERSION_CHANGES = "В ней сприсутствуют следующие обновления: \n";
    public static final long L_WRONG_VERSION_EXIT_TIMEOUT = 30000;      // If there are critical changes in new version exit in 10 seconds
    public static final String S_NEW_VERSION_CRITICAL_CHANGES = "В новой версии присутствуют критические обновления. Дальнейшая работа этого клиента не возможна. ";
    //+ "Завершение работы через " + Long.toString(L_WRONG_VERSION_EXIT_TIMEOUT/1000) + " секунд. ";
    public static final String S_NEW_VERSION_URL = "Скачать новую версию можно с ";
    public static final String S_NEW_VERSION_DOWNLOAD = "Скачать";
    // User's profile
    public static final String S_PIC = "Фото";
    public static final String S_NAME = "Имя:";
    public static final String S_CONTACT_INFORMATION = "контактная информация:";
    public static final String S_USER_OF_COMPANY = "представители компании:";
    public static final String S_ENTERPRISE_NAME = "Компания:";
    public static final String S_FIRST_NAME = "Имя:";
    public static final String S_LAST_NAME = "Фамилия:";
    public static final String S_PERSONAL_STATUS = "Статус:";
    public static final String S_ENTERPRISE_STATUS = "Слоган:";
    public static final String S_GENDER = "Пол:";
    public static final String S_AGE = "Возраст";
    public static final String S_AGES = " лет";
    public static final String S_PERSONAL_GOALS = "Цели на мероприятии:";
    public static final String S_PERSONAL_ABOUT_MSG = "О себе:";
    public static final String S_ENTERPRISE_ABOUT_MSG = "Описание:";
    public static final String S_CONTACTS = "Email и номер телефона доступны только организаторам мероприятия.";
    public static final int I_SET_CONTACT_PHONE_LEN_MAX = 20;
    public static final int I_SET_CONTACT_EMAIL_LEN_MAX = 255;
    public static final int I_SET_CONTACT_ICQ_LEN_MAX = 9;
    public static final int I_SET_CONTACT_SKYPE_LEN_MAX = 32;
    public static final String S_LINKS = "Ссылки:";
    public static final String S_RESIDENTCITY = "Город:";
    public static final String S_EDUCATION = "Место учебы:";
    public static final String S_WORK_PLACE = "Место работы:";
    public static final String S_WORK_POST = "Должность:";
    public static final String S_BIRTHDAY = "День рождения:";
    public static final String S_REGION = "Регион:";
    public static final String S_FP2009_NOMINATION = "Номинация:";
    public static final String S_ONLY_FOR_PERSONAL = "Сожалеем, но опция просмотра и редактирования профайла доступна только для пользователей!";
    public static final String S_SET_GENDER[] = {
        "Парень",
        "Девушка"
    };
    public static final String S_SET_PROFILE_INSTRUCTIONS = "Сфотографируйте себя на камеру и заполните информацию в профайле";
    public static final String S_BIRTHDAY_DAYS[] = {
        "День",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23",
        "24",
        "25",
        "26",
        "27",
        "28",
        "29",
        "30",
        "31",};
    public static final String S_BIRTHDAY_MONTHS[] = {
        "Месяц",
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "аргуста",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    };
    public static final String S_BIRTHDAY_YEARS[] = {
        "Год",
        "2009",
        "2008",
        "2007",
        "2006",
        "2005",
        "2004",
        "2003",
        "2002",
        "2001",
        "2000",
        "1999",
        "1998",
        "1997",
        "1996",
        "1995",
        "1994",
        "1993",
        "1992",
        "1991",
        "1990",
        "1989",
        "1988",
        "1987",
        "1986",
        "1985",
        "1984",
        "1983",
        "1982",
        "1981",
        "1980",
        "1979",
        "1978",
        "1977",
        "1976",
        "1975",
        "1974",
        "1973",
        "1972",
        "1971",
        "1970",};
    public static final String S_DEVICE_CLASS = "Device class:";
    public static final String S_BT_INIT_ERROR = "Ошибка инициализации Bluetooth. Включите Bluetooth на телефоне!";
    public static final String S_DEVICE_NOT_REGISTERED = "Bluetooth-адрес этого устройства зарегистрирован на другого пользователя. \n\"Встречи\" работать не будут!";
    public static final String S_NOT_DISCOVERABLE = "Not Discoverable";
    public static final String S_GIAC = "General";
    public static final String S_LIAC = "Limited";
    //public static final String S_RECORD_INFO = "Информация о BT логах";
    public static final String S_RECORD_INFO_S1 = " записей занимают ";
    public static final String S_RECORD_INFO_S2 = " байт. Еще доступно ";
    public static final String S_RECORD_INFO_S3 = " байт.";
    //public static final String S_RMS_DEL_ALERT = "Вы точно хотите удалить все записи?";
    //public static final String S_RMS_DEL_OK = "Записи удалены";
    public static final String S_LOGS_NO = "There are no logs.";
    public static final String S_WRONG_NUM = "Enter the number.";
    public static final String S_TOO_BIG_NUM = "The number is too big.";
    public static final String S_NO_SYMBOLS = "No symbols";
    public static final String S_SCAN_TO_CHANGED = "The scan time out has been changed";
    public static final String S_MERGE_INTERVAL_CHANGED = "The merge interval has been changed";
    public static final String S_LOGIN_CHANGED = "The login has been changed";
    public static final String S_PASSWORD_CHANGED = "The password has been changed";
    public static final String S_LOGIN_PLZ = "Enter your login and password before connect.";
    public static final String S_RMS_RECORD_STORE_FULL_EXCEPTION = "Данные не умещаются в памяти!";
    public static final String S_RMS_RECORD_STORE_NOT_OPEN_EXCEPTION = "Record Store была закрыта!";
    public static final String S_RMS_OUT_OF_BOUNDS_EXCEPTION = " " + "выходит за границы record store.";
    public static final String S_ERASE = "Очистить";
    public static final String S_SYS_OK = "...OK";
    public static final String S_SYS_FAIL = "...FAIL";
    public static final String S_ERROR = "Ошибка";
    // Bletooth messages temlates
    public static final String S_BTMSG_TEMPLATES = "Шаблоны сообщений";
    public static final String S_BTMSG_TEMPLATES_TITLE[] = {
        "1. Ссылка на myRange",
        "2. Давай знакомиться",
        "3. Сообщение в имени телефона"
    };
    public static final String S_BTMSG_TEMPLATES_BODY[] = {
        "Офигенная программа для обмена контактами и знакомств по блютус - myRange: http://m.myrange.ru/",
        "Привет! Давай знакомиться!! ;)",
        "Напиши ответное сообщение в имени своего телефона!"
    };
    // *** START FORM ***
    // FOR MAMBA
    /*
    public static final String S_MAMBA_WELCOME = "Мамба";
    public static final String S_MAMBA_WELCOME_INFO = "Для продолжения работы Вам необходимо зарегистрироваться в сервисе знакомств \"Мамба\"";
    public static final String S_MAMBA_LOGIN_INFO = "Я уже зарегистрирован";
    public static final String S_MAMBA_REG_INFO = "Зарегистрироваться";
    public static final String S_MAMBA_LOGIN = "Проверка пользователя Мамбы";
    public static final String S_MAMBA_LOGIN_TF_INFO = "Логин";
    public static final String S_MAMBA_LOGIN_BT_INFO = "Проверить";
    public static final String S_MAMBA_EMPTY_LOGIN = "Введите логин.";
    public static final String S_MAMBA_EMPTY_PASSWORD = "Введите пароль.";
    public static final String S_MAMBA_LOGIN_OK = "Аккаунт в Мамбе успешно подтвержден.";
    public static final String S_MAMBA_LOGIN_WRONG_USER_OR_PASSWORD = "Пользователя с таким логином и паролем в Мамбе не существует.";
    public static final String S_MAMBA_LOGIN_DELETED_USER = "Этот аккаунт в Мамбе удален.";
    public static final String S_MAMBA_REG = "Регистрайция в Мамба";
    public static final String S_MAMBA_REG_LOGIN_LIM = "Длина логина должна быть от 3 до 15 символов. Логин может состоять только из латинских букв, цифр, дефиса и подчеркивания";
    public static final String S_MAMBA_REG_LOGIN_INFO = "Придумайте логин для входа на сайт (" + S_MAMBA_REG_LOGIN_LIM + "):";
    public static final int I_MAMBA_LOGIN_LEN_MAX = 15;
    public static final String S_MAMBA_REG_PASSWORD_LIM = "Длина пароля должна быть от 6 до 12 символов. Пароль может состоять только из латинских букв, цифр, дефиса и подчеркивания";
    public static final String S_MAMBA_REG_PASSWORD_INFO = "Придумайте пароль (" + S_MAMBA_REG_PASSWORD_LIM + "):";
    public static final int I_MAMBA_PASSWORD_LEN_MAX = 12;
    public static final String S_MAMBA_REG_NAME_LIM = "Длина имени должна быть от 3 до 50 символов. Имя не должно содержать недопустимые символы";
    public static final String S_MAMBA_REG_NAME_INFO = "Имя, которое будут видеть пользователи (" + S_MAMBA_REG_NAME_LIM + "):";
    public static final int I_MAMBA_NAME_LEN_MAX = 50;
    public static final String S_MAMBA_REG_LOOKFOR_INFO = "Кого вы ищете:";
    public static final String[] S_MAMBA_LOOKFOR_CASES = {
    "парня",
    "девушку"
    };
    public static final String S_MAMBA_BIRTHDAY = "Дата рождения (Мамба - это сервис для лиц старше 16 лет):";
    public static final String S_MAMBA_RESIDENT_CITY = "Место жительства:";
    public static final String[] S_MAMBA_RESIDENT_CITY_CASES = {
    "Москва"
    };
    public static final String S_MAMBA_SECRET_QUESTION = "Контрольный вопрос (пригодится при восстановлении пароля):";
    public static final String[] S_MAMBA_SECRET_QUESTION_CASES = {
    "выберите вопрос:",
    "Девичья фамилия матери",
    "Любимое блюдо",
    "День рождения бабушки",
    "Номер паспорта",
    "Любимый город",
    "Имя лучшего друга"
    };
    public static final String S_MAMBA_SECRET_QUESTION_ALT = "или придумайте свой вопрос:";
    public static final String S_MAMBA_SECRET_ANSWER = "ответ на контрольный вопрос:";
    public static final String S_MAMBA_CAPTCHA = "Введите код на картинке";
    public static final String S_MAMBA_CAPTCHA_CODE = "Код подтверждения:";
    public static final String[] S_MAMBA_AGREEMENT_CASES = {
    "Я принимаю условия, изложенные в"
    };
    public static final String S_AGREEMENT_LINK = "Соглашении о предоставлении услуг";
    public static final String S_EMPTY_BIRTHDAY = "Укажите свою дату рождения.";
    public static final String S_EMPTY_SECRET_QUESTION = "Веберите из списка или придумайте свой контрольный вопрос.";
    public static final String S_EMPTY_SECRET_ANSWER = "Введите ответ на контрольный вопрос.";
    public static final String S_EMPTY_CAPTCHA_CODE = "Введите код подтверждения с картинки.";
    public static final String S_MAMBA_NOT_AGREEMENT = "Для регистрации в Мамбе Вам необходимо принять условия соглашения о предоставлении услуг.";
    public static final String S_MAMBA_REG_SMS = "Завершение регистрации";
    public static final String S_MAMBA_REG_SMS_INFO = "Для завершения регистрации необходимо отправить СМС";
    public static final String S_MAMBA_REG_SMS_SEND = "Отправить СМС";
    public static final String S_MAMBA_REG_SMS_COST_1 = "Стоимость СМС ";
    public static final String S_MAMBA_REG_SMS_COST_2 = " (без НДС)";
    public static final String S_MAMBA_REG_SMS_BONUS = "Также вы получите бонус в размере 2$ на счет своей анкеты";
    public static final String S_MAMBA_REG_SMS_WARNING = "Если вы не отправите СМС в течении трех суток, вам придется регистрироваться заново";
    public static final String S_MAMBA_REG_SMS_SKIP = "Пропустить";
    public static final String S_MAMBA_REG_SMS_SEND_OK = "СМС успешно отправлено.";
     */
    // END MAMBA
    public static final String S_LOGIN = "Вход";
    public static final String S_ENTER_BY_LOGIN = "Вход по номеру телефона/email";
    public static final String S_ENTER_BY_CODE = "Вход по коду";
    public static final String S_LOGIN_INFO = "Телефон или email";
    public static final String S_LOGIN_INFO_EXAMPLE_1 = "Например: ABC4";
    public static final String S_LOGIN_INFO_EXAMPLE_2 = "user@domain.com";
    public static final String S_PINCODE = "Ввести код";
//#if CONF == "true"
//#     public static final String S_INFO = "MyRange Conf ориентирован на рынок профессиональных мероприятий типа конференций и форумов и призван упростить процесс знакомства и общения участников друг с другом.";
//#     public static final String S_LOGIN_PINCODE_INFO = "Введите код доступа, который вы получили от организаторов мероприятия:";
//#else
    public static final String S_INFO = "Система деловых контактов для профессиональных мероприятий. Расписание, список участников, звонки, встречи, переписка.";
    public static final String S_LOGIN_PINCODE_INFO = "Введите код доступа к вашему профилю, который вы получили от организаторов.";
//#endif
    public static final String S_LOGIN_PINCODE_HELP = "Если по какой-то причине у вас нет кода - обратитесь на стойку регистрации или по адресу support@myrange.ru";
    public static final String S_PASSWORD_INFO = "Пароль";
    public static final String S_PASSWORD_RECOVERY = "Забыл пароль";
    public static final String S_PASSWORD_RECOVERY_HELP = "Новый пароль будет отправлен на этот номер телефона или email . После этого старый пароль больше не будет действовать.";
    public static final String S_SEND_NEW_PASSWORD = "Отправить пароль";
    public static final String S_PASSWORD_RECOVERY_DONE = "Новый пароль был отправлен на ";
    public static final String S_EMPTY_LOGIN = "Введите код";
    public static final String S_EMPTY_PINCODE = "Введите код доступа";
    public static final String S_SHOW_PINCODE = "Код доступа - ";
    public static final String S_EMPTY_PASSWORD = "Введите пароль";
    public static final String S_LOGIN_OK = "Вход выполнен";
    public static final String S_SET_EMAIL = "E-mail";
    public static final String S_SET_FIRST_NAME = "Имя";
    //public static final int I_SET_FIRST_NAME_LEN_MAX = 255;
    public static final String S_SET_LAST_NAME = "Фамилия";
    //public static final int I_SET_LAST_NAME_LEN_MAX = 255;
    public static final String S_EMPTY_FIRST_NAME = "Введите имя";
    public static final String S_EMPTY_LAST_NAME = "Введите фамилию";
    public static final String S_REG = "Регистрация";
    public static final String S_REG_NEXT = "Продолжить";
    public static final String S_REG_OK = "Вы зарегистрированы";
    public static final String S_SET_PHONE = "Номер телефона";
    public static final String S_PHONE_INFO = "Телефон";
    public static final String S_PHONE_UNREGISTRED_ALERT = "отсутствует зарегистрированный телефонный номер";
    public static final String S_PHONE_INFO_EXAMPLE = "Например 89261234567";
    public static final String S_REGISTER_HELP = "Номер телефона будет использоваться только для входа и восстановления пароля. Сейчас мы придумаем вам надёжный пароль и отправим по SMS на этот номер.";
    //public static final int I_SET_PHONE_LEN_MAX = 32;
    public static final String S_WRONG_PHONE = "Некорректный номер телефона";
    public static final String S_WRONG_EMAIL = "Некорректный e-mail";
    public static final String S_CODE_SEND_INFO = "На ваш номер было отослано СМС с проверочным кодом. Введите этот код.";
    public static final String S_CODE_HIDE_OR_EXIT = "Чтобы посмотреть СМС с кодом, сверните или закройте приложение";
    public static final String S_CODE_BACK = "Если Вы хотите зайти или зарегистрироваться заново, то нажмите \"Назад\"";
    public static final String S_SET_CODE = "Код:";
    public static final String S_WRONG_CODE = "Не правильный код доступа";
    public static final String S_PASSWORD_SEND_INFO = "На ваш номер было отослано СМС с вашим паролем от myRange. Введите этот пароль.";
    public static final String S_SET_PASSWORD = "Пароль:";
    //public static final int I_SET_CODE_LEN_MAX = 10;
    public static final String S_WRONG_PASSWORD = "Неверный пароль";
    public static final String S_FIRST_GUIDE = "Здравствуйте!\nПеред началом работы мы рекомендуем зайти в \"ваш профиль\", что бы проверить свои персональные данные.";
    public static final String S_FIRST_MY_PROFILE = "Ваш профиль";
    public static final String S_FIRST_MAIN_MENU = "Начать работу";
    public static final String S_MESSAGES_NEW = " новых сообщений";
    public static final String S_NO_EVENTS = "нет событий";
    // *** USERS MENU ***
    public static final String S_USERS = " пользователей";
    public static final String S_USERS_NEAR = "рядом ";
    public static final String S_DEVICES_NEAR = "рядом ";
    public static final String S_ALL_USERS_ON_CONFERENCE = "Все люди";
    public static final String S_ALL = "Всего: ";
    public static final String S_USERS_MENU[] = {
        "Поиск",
        "Избранные",
        "Встречи",
        "Новый поиск"
    };
    public static final byte CMD_M_SEARCH = 0;
    public static final byte CMD_M_FAVOURITES = 1;
    public static final byte CMD_M_NEAR_USERS = 2;
    public static final byte CMD_M_NEW_SEARCH = 3;
    // *** MESSAGES MENU ***
    public static final String S_PRIVATEMSG_NEWINBOX = " новых сообщений";
    public static final String S_PRIVATE_INBOX_MESSAGES = " входящих";
    public static final String S_SMS_MESSAGES = " входящих СМС";
    public static final String S_MESSAGES_MENU[] = {
        "Оргкомитет",
        "ЛС",
        "Входящие СМС"
    };
    public static final byte CMD_M_ORG = 0;
    public static final byte CMD_M_PRIVATE_MSG = 1;
    public static final byte CMD_M_SMS = 2;
    // *** OPTIONS MENU ***
    public static final String S_OPTIONS_MENU[] = {
        "Синхронизироваться",
        "Трафик",
        "Лог программы",
        "Разлогиниться",};
    public static final byte CMD_M_SYNC = 0;
    public static final byte CMD_M_TRAFFIC = 1;
    public static final byte CMD_M_PROGRAMM_LOG = 2;
    public static final byte CMD_M_LOGOUT = 3;
    // *** SETTINGS MENU ***
    // Scanning
    public static final String S_SET_SCAN_TITLE = "Сканирование";
    public static final String S_SET_SCAN[] = {
        "Постоянно",
        "1 мин",
        "2 мин",
        "5 мин",
        "10 мин"
    };
    public static final long L_SET_SCANTO[] = {
        1000,
        60000,
        120000,
        300000,
        600000
    };
    public static final long L_SET_MERGEINT[] = {
        240000,
        300000,
        360000,
        540000,
        840000
    };
    // Internet connection
    public static final String S_SET_INET_CONNECTION_TITLE = "Доступ в интернет";
    public static final String S_SET_INET_CONNECTION[] = {
        "Постоянно",
        "По запросу",};
    public static final int I_INET_CONNECTION_ALWAYS = 0;
    public static final int I_INET_CONNECTION_ONDEMAND = 1;
    // Download pictures
    public static final String S_SET_DOWNLOAD_PIC_TITLE = "Загрузка картинок";
    public static final String S_SET_DOWNLOAD_PIC[] = {
        "Загружать",
        "Не надо",};
    public static final int I_DOWNLOAD_PIC_YES = 0;
    public static final int I_DOWNLOAD_PIC_NO = 1;
    // Discoverable
    public static final String S_SET_DISCOV_TITLE = "Режим обнаружени";
    public static final String S_SET_DISCOV[] = {
        "Общий доступ",
        "Ограниченный доступ",
        "Не обнаруживается"
    };
    public static final byte CMD_M_SET_DISCOV_GIAC = 0;
    public static final byte CMD_M_SET_DISCOV_LIAC = 1;
    public static final byte CMD_M_SET_DISCOV_NOT = 2;
    public static final String S_SET_BTADDRESS = "Bluetooth адрес";
    public static final int I_SET_BTADDRESS_LEN_MAX = 12;
    //public static final int I_SET_EMAIL_LEN_MAX = 255;
    //public static final int I_SET_PASSWORD_LEN_MAX = 255;
    public static final int I_LOGS_SHOW_MAX = 100;		// Max number of BT logs for show in console
    // Some settings constants
    public static final int I_MAX_BTMEETS_FOR_SOAP = 100;	// Maxinun btMeeets in one soap pocket for snding
    public static final long L_SMS_SERVICE_MUST_UPDATE_MS = 604800000;	// 7 day - Actual sms service information time
    public static final long L_SCAN_TO = 120000;		// Scan time out
    //public static final long L_MERGE_INTERVAL_MIN = 60000;	// Merge interval depends on the L_SCAN_TO, but it can't be less than L_MERGE_TO_MIN
    //public static final long L_MERGE_INTERVAL_MAX = 600000;	// Merge interval depends on the L_SCAN_TO, but it can't be more than L_MERGE_TO_MAX
    public static final long L_HOUR_MS = 3600000;		// Number of miliseconds in the hour
    public static final long L_DAY_MS = 86400000;		// Number of miliseconds in the day
    //public static final int I_BT_ERROR_ALERT_TIMEOUT = 2000;
    // Public static final int I_DEL_OK_ALERT_TIMEOUT = 1000;
    public static final int I_SCREEN_SPEED = 25;	// How fast will the screen shift in bt logs show
    public static final int I_SHORT_NAME_SPACE = 50;// Widht of devices short names column (in show logs)
    public static final int I_PIX_TO_MS = 60000;	// How much miliseconds represent 1 pixel
    public static final int I_TEXT_SPEED = 20;
    public static final int I_KEY_TIME = 50;
    public static final int I_KEY_RELEASE_TIME = 500;
    public static final int I_TEXT_MAX_LENGTH = 5000;	// Max length of text in console
    //public static final int I_CMD_HISTORY_SIZE = 30;
    public static final int I_SPACER_WIDTH = 10000; // Width of spacer item betweens buttons
    public static final int I_SPACER_HEIGHT = 5;	// Height of spacer item betweens buttons
    public static final String S_SMS_U2U_CODENAME = "U2U"; // Codename for the U2U sms service
    // *** CONSOLE COMMANDS ***
    public static final int I_CONSCDM_NUM = 11;
    public static final int I_CONSCDM_HELP = 0;
    public static final int I_CONSCDM_BTINFO = 1;
    public static final int I_CONSCDM_BTLOGS = 2;
    public static final int I_CONSCDM_CLEAR = 3;
    public static final int I_CONSCDM_SCAN_TO = 4;
    public static final int I_CONSCDM_MERGE_INTERVAL = 5;
    public static final int I_CONSCDM_MD5 = 6;
    public static final int I_CONSCDM_TESTUSER = 7;
    public static final int I_CONSCDM_ECHO = 8;
    public static final int I_CONSCDM_UPLOAD_LOGS = 9;
    public static final int I_CONSCDM_OUT_SHOW = 10;
    public static final String[] S_CONSCDM = {
        "help",
        "btinfo",
        "btlogs",
        "clear",
        "scanto",
        "mergeint",
        "md5",
        "testuser",
        "echo",
        "uploadlogs",
        "out"
    };
    // help
    public static final String S_ATR_HELP = "-h";
    public static final String[] S_HELP = {
        "help :\n"
        + "    Выводит хэлп по командам.",
        "btinfo :\n"
        + "    Выводит число логов и занимаемое ими место в памяти.",
        "btlogs numLogs :\n"
        + "    numLogs <= " + I_LOGS_SHOW_MAX + " – Число.\n"
        + "    Выводит последние numLogs логов.",
        "сlear :\n"
        + "    Очищает консоль.",
        "scanto [timeout] :\n"
        + "    timeOut – Число.\n"
        + "    Показывает/устанавливает задержку timeOut (ms) между двумя Bluetooth сканированиями.",
        "mergeint [time] :\n"
        + "    Показывает/устанавливает максимальный интервал объединения логов встреч (ms).",
        "md5 string :\n"
        + "    string – Произвольная строка.\n"
        + "    Выдает md5  хэш произвольной строки.",
        "testuser :\n",
        "echo string :\n"
        + "    string – Произвольная строка.\n"
        + "    Операция эхо.",
        "uploadlogs :\n"
        + "    Загружает отладочную информацию на сервер.",
        "out [num] :\n"
        + "    Показывает num последних программных логов."
    };
    // Unknown command
    public static final String S_CONSCDM_UNKNOWN_1 = "Command '";
    public static final String S_CONSCDM_UNKNOWN_2 = "' not found.";
    // *** ICONS ***
    public static final int I_ICON_NUM = 27;	// Total number of icons
    public static final int I_ICON_SIZE = 32;	// Each icon has size 16x16 pixels
    public static final int I_ICON_BT_OFF = 0;
    public static final int I_ICON_BT_ON = 1;
    public static final int I_ICON_USERS = 2;
    public static final int I_ICON_DEVICES = 3;
    public static final int I_ICON_PRIVATEMSG = 4;
    public static final int I_ICON_OPTIONS = 5;
    public static final int I_ICON_ABOUT = 6;
    public static final int I_ICON_CONSOLE = 7;
    public static final int I_ICON_SYNC = 8;
    public static final int I_ICON_PROFILE = 9;
    public static final int I_ICON_SETTINGS = 10;
    public static final int I_ICON_LOGOUT = 11;
    public static final int I_ICON_COMPUTER = 12;
    public static final int I_ICON_DESKTOP = 13;
    public static final int I_ICON_LAPTOP = 14;
    public static final int I_ICON_PDA = 15;
    public static final int I_ICON_CORDLESS = 16;
    public static final int I_ICON_CELLULAR = 17;
    public static final int I_ICON_SMARTPHONE = 18;
    public static final int I_ICON_HANDSFREE = 19;
    public static final int I_ICON_BTGPS = 20;
    public static final int I_ICON_UNKNOWN_DEVICE = 21;
    public static final int I_ICON_SMS = 22;
    public static final int I_ICON_PRIVATEMSG_INBOX = 23;
    public static final int I_ICON_PRIVATEMSG_OUTBOX = 24;
    public static final int I_ICON_TRAFFIC = 25;
    public static final int I_ICON_ENTERPRISE = 26;
    public static final String S_ICONS_ERROR = "Иконки не могут быть загружены";
    // Key symbol
    public static final char[] C_KEY_0_SYMB = {'0', ' '};
    public static final char[] C_KEY_1_SYMB = {'1', '.', ',', '@', '-', '_', ':', ';', '\\', '/', '(', ')', '*', '#', '<', '>'};
    public static final char[] C_KEY_2_SYMB = {'a', 'b', 'c', '2', 'A', 'B', 'C', 'a', 'а', 'б', 'в', 'г', 'А', 'Б', 'В', 'Г'};
    public static final char[] C_KEY_3_SYMB = {'d', 'e', 'f', '3', 'D', 'E', 'F', 'd', 'д', 'е', 'ж', 'з', 'Д', 'Е', 'Ж', 'З'};
    public static final char[] C_KEY_4_SYMB = {'g', 'h', 'i', '4', 'G', 'H', 'I', 'g', 'и', 'й', 'к', 'л', 'И', 'Й', 'К', 'Л'};
    public static final char[] C_KEY_5_SYMB = {'j', 'k', 'l', '5', 'J', 'K', 'L', 'j', 'м', 'н', 'о', 'п', 'М', 'Н', 'О', 'П'};
    public static final char[] C_KEY_6_SYMB = {'m', 'n', 'o', '6', 'M', 'N', 'O', 'n', 'р', 'с', 'т', 'у', 'Р', 'С', 'Т', 'У'};
    public static final char[] C_KEY_7_SYMB = {'p', 'q', 'r', 's', '7', 'P', 'Q', 'R', 'S', 'ф', 'х', 'ц', 'ч', 'Ф', 'Х', 'Ц', 'Ч'};
    public static final char[] C_KEY_8_SYMB = {'t', 'u', 'v', '8', 'T', 'U', 'V', 't', 'ш', 'щ', 'ъ', 'ы', 'Ш', 'Щ', 'Ъ', 'Ы'};
    public static final char[] C_KEY_9_SYMB = {'w', 'x', 'y', 'z', '9', 'W', 'X', 'Y', 'Z', 'w', 'ь', 'э', 'ю', 'я', 'Ь', 'Э', 'Ю', 'Я'};
    //Text size constants
    public static final int CH_SMALL_MAW_WIDTH = 8;
    public static final String CALL_SUCCESS = "Запрос отправлен успешно. Подождите, в течение двух минут вам поступит входящий звонок!";
}
