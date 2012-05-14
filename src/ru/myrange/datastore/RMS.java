package ru.myrange.datastore;

import java.io.UnsupportedEncodingException;
import javax.microedition.rms.*;

import ru.myrange.stuff.StringConsts;

/**
* Class for work with RecordStore (store and load data).
* @author Oleg Ponfilenok
*/
public class RMS {

    /**
     * To split differend data in one recodr in RMS
     */
    public static final String SPACER = "<*\u0004\u0019%>";

    /**
	 * The locateme record store name
	 */
	private String name;
    
    /**
	 * The instance of the record store
	 */
	private RecordStore rec;
    
    /**
	 * A flag to indicate whether the record store is currently open
	 */
	protected static boolean isOpen = false;

    /**
     * Simple constructor.
     *
     * @param name name of RecordStore.
     */
    public RMS(String name)
	{
		this.name = name;
	}

    /**
     * Opens the record store for access.
     *
     * @throws javax.microedition.rms.RecordStoreException
     */
	public synchronized void open() throws RecordStoreException {
		try {
			rec = RecordStore.openRecordStore(name, true);
            isOpen = true;
		}
		catch( RecordStoreException e ){
			throw new RecordStoreException("[RMS] Open: " + name + " ...FAIL: " + e.toString());
		}
	}

    /**
     * Closes the record store
     */
	public synchronized void close() {
        if (!isOpen) return;
        try {
			rec.closeRecordStore();
            isOpen = false;
		}
		catch( RecordStoreException ignored ) {}
	}

    /**
	 * Returns the record store's open state
	 *
	 * @return <code>true</code> if the record store open
	 */
	public static boolean isOpen() {
		return isOpen;
	}

    /**
     * Create the empty record store with specified size
     *
     * @param size
     * @throws java.lang.Exception
     */
	public synchronized void createEmpty(int size) throws Exception {
		eraseRecord();
		byte[] data = {0x00};
		for(int i = 1; i <= size; ++i){
			addByteData(data);
		}
	}

    /**
     * Delete all records.
     *
     * @throws javax.microedition.rms.RecordStoreException
     */
	public synchronized void eraseRecord() throws RecordStoreException {
		try {
            rec.closeRecordStore();
			RecordStore.deleteRecordStore(name);
			rec = RecordStore.openRecordStore(name, true);
		}
		catch( RecordStoreException e ){
			throw new RecordStoreException("[RMS] Error: " + e.toString());
		}
	}

    /**
     * Delete the concrete records: [begin..end], begin >= 1
     *
     * @param begin first record field to delete (include). <code>begin</code> >= 1
     * @param end last record field to delete (include)
     * @throws java.lang.Exception
     */
	public synchronized void eraseRecords(int begin, int end) throws Exception {
		try {
			int size = getNumRec();
			if ( (begin > end) || (end > size) ) {
                throw new Exception("[RMS] Error: " + "begin=" + begin + " end=" + end + " size=" + size + " " + StringConsts.S_RMS_OUT_OF_BOUNDS_EXCEPTION);
			}
			byte[][] oldData = new byte[size][];
			for(int i = 0; i < size; ++i){
				oldData[i] = readByteData(i+1);
			}
			eraseRecord();

			for(int i = 1; i <= size; ++i){
				if ( (i >= begin) && (i <= end) ) {
					// nothing to do
				} else {
					addByteData(oldData[i-1]);
				}
			}
		}
		catch( Exception e ){
			throw new Exception("[RMS] Error: " + e.toString());
		}
	}

    /**
     * Delete the concrete record string field.
     *
     * @param id index of first record field to delete.
     * @throws java.lang.Exception
     */
	public synchronized void eraseStringField(int id) throws Exception {
		addStringData("", id);
	}

    /**
     * Add data to the end of record store.
     *
     * @param data data to add.
     * @return index of new record store field (end field).
     * @throws javax.microedition.rms.RecordStoreException
     */
	public synchronized int addByteData(byte[] data) throws RecordStoreException {
		try {
            /*
            System.out.println("=====RMS=====START====");
            System.out.println("Size = " + getSize());
            System.out.println("Available Size = " + getSizeAvailable());
            System.out.println("Try to put " + data.length + " bytes to RMS.");
			*/
            int res = rec.addRecord(data, 0, data.length);
            /*
            System.out.println("OK res = " + res);
            System.out.println("=====RMS=====END====");
            */
            return res;
		}
		catch( RecordStoreFullException e ){
			// data do not fit in memory
			throw new RecordStoreFullException("[RMS] Add record FAIL: " + StringConsts.S_RMS_RECORD_STORE_FULL_EXCEPTION + " " + e.toString());
		}
		catch( RecordStoreNotOpenException e ){
			// record store is closed
			throw new RecordStoreNotOpenException("[RMS] Add record FAIL: " + StringConsts.S_RMS_RECORD_STORE_NOT_OPEN_EXCEPTION + " " + e.toString());
		}
		catch(RecordStoreException e){
			// other errors
			throw new RecordStoreException("[RMS] Add record FAIL: " + e.toString());
		}
	}

    /**
     * Add data to the target field of record store.
     *
     * @param data data to add.
     * @param id index of record field to add data.
     * @throws java.lang.Exception
     */
	public synchronized void addByteData(byte[] data, int id) throws Exception {
		int size = getNumRec();
		if (id > size) {
			throw new Exception("[RMS] Error: " + id + StringConsts.S_RMS_OUT_OF_BOUNDS_EXCEPTION);
		}
		byte[][] oldData = new byte[size][];
		for(int i = 1; i <= size; ++i){
			oldData[i-1] = readByteData(i);
		}
		eraseRecord();
		for(int i = 1; i <= size; ++i){
			if ( i == id) {
				addByteData(data);
			} else {
				addByteData(oldData[i-1]);
			}
		}
	}
	
	public void addStringData(String sData, int id) throws Exception {
		addByteData(sData.getBytes("UTF-8"), id);
	}

    /**
     * Add int to the end of record store.
     *
     * @param sData
     * @throws java.lang.Exception
     */
    public void addIntData(int iData) throws Exception {
		addByteData(Integer.toString(iData).getBytes());
	}

    public void addIntData(int iData, int id) throws Exception {
		addStringData(Integer.toString(iData), id);
	}

    public void addLongData(long lData, int id) throws Exception {
		addStringData(Long.toString(lData), id);
	}

    public void addBooleanData(boolean b, int id) throws Exception {
		if (b) addStringData("true", id);
        else addStringData("false", id);
	}

    /**
     * Read data from the target field of record store
     *
     * @param id undex of target field in the record store.
     * @return data
     * @throws javax.microedition.rms.RecordStoreException
     */
    public synchronized byte[] readByteData(int id) throws RecordStoreException { // id >= 1 (first record has index 1)
		byte[] data;
		try {
			data = new byte[rec.getRecordSize(id)];
			rec.getRecord(id, data, 0);
            return data;
		}
		catch( RecordStoreNotOpenException e ){
			throw new RecordStoreNotOpenException("[RMS] Error: " + e.toString());
		}
        catch( InvalidRecordIDException e ){
			throw new InvalidRecordIDException("[RMS] Error: " + e.toString());
		}
	}
    /*
    public static byte[] readByteData(int id) throws Exception { // id >= 1 (the first record has index 1)
		byte[] data;
		try {
			data = new byte[rec.getRecordSize(id)];
			rec.getRecord(id, data, 0);
            return data;
		}
		catch( Exception e ){
			throw new Exception("[RMS:] Error: " + e.toString());
		}
	}
    */

    /*
	public static String readHexData(int id) {
		byte[] data = readByteData(id);
		String str = MyRoutines.bytesToHex(data);
		return str;
	}

	*/
	public String readStringData(int id) throws RecordStoreException {
		byte[] data = readByteData(id);
		if (data != null) {
            try {
                return new String(data, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
                return new String(data);
            }
        }
        else return "";
	}

    public int readIntData(int id) throws RecordStoreException {
        return Integer.parseInt(readStringData(id));
	}

    public long readLongData(int id) throws RecordStoreException {
		return Long.parseLong(readStringData(id));
	}

    public boolean readBooleanData(int id) throws Exception {
		if (readStringData(id).equals("true")) return true;
        else if (readStringData(id).equals("false")) return false;
        else throw new Exception("Can't read data from RMS");
	}

    /**
     * Get number of record in the record store.
     *
     * @return number of record (record store size).
     * @throws javax.microedition.rms.RecordStoreException
     */
	public synchronized int getNumRec() throws RecordStoreException {
		try {
			return rec.getNumRecords();
		}
		catch( RecordStoreException e ){
			throw new RecordStoreException("[RMS] Error: " + e.toString());
		}
	}

    /**
     * Get size of the record store.
     *
     * @return size of the record store.
     * @throws javax.microedition.rms.RecordStoreNotOpenException
     */
    public synchronized int getSize() throws RecordStoreNotOpenException {
        try {
			return rec.getSize();
		}
		catch( RecordStoreNotOpenException e ){
			throw new RecordStoreNotOpenException("[RMS] Error: " + e.toString());
		}
	}

    /**
     * Get available size of the record store.
     *
     * @return available size of the record store.
     * @throws javax.microedition.rms.RecordStoreNotOpenException
     */
    public synchronized int getSizeAvailable() throws RecordStoreNotOpenException {
        try {
			return rec.getSizeAvailable();
		}
		catch( RecordStoreNotOpenException e ){
			throw new RecordStoreNotOpenException("[RMS] Error: " + e.toString());
		}
	}

}
