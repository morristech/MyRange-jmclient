package ru.myrange.canvases;
/**
 * A enter text object
 * @author Oleg Ponfilenok
 */

import com.sun.lwuit.Command;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;

public abstract class EnterBox extends Canvas
{
    // *** COLORS ***
    // ScrollList colors
    public static final int I_COLOR_BACKGROUND = 0xd9eefd;
    public static final int I_COLOR_NORMAL = I_COLOR_BACKGROUND;
	public static final int I_COLOR_SELECT = 0x82c0db;
    public static final int I_COLOR_SCROLLBAR_TRACK = 0xcccccc;
    public static final int I_COLOR_SCROLLBAR_FACE = 0x666666;
    // ScrollItem colors
    public static final int I_COLOR_NULL_PIC = I_COLOR_BACKGROUND; //0xffff99;
	public static final int I_COLOR_NORMAL_TEXT = 0x000000;
    public static final int I_COLOR_SMALL_TEXT = 0x000000;
	public static final int I_COLOR_LINE = I_COLOR_SCROLLBAR_TRACK;
    // Label colors
    public static final int I_COLOR_LABEL = 0xffff00;
    public static final int I_COLOR_LABEL_TEXT = 0x000000;
    // Console
    public static final int I_COLOR_CURSOR = 0x8fc811;

    // *** FONTS ***
    public static final Font F_NORMAL = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    public static final Font F_SMALL = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    public static final Font F_SMALL_UNDERLINED = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_UNDERLINED, Font.SIZE_SMALL);
    public static final Font F_CONSOLE = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

    // Key codes
	public static final int KEY_NULL = 0;
	public static final int KEY_UP = -1;
	public static final int KEY_DOWN = -2;
	public static final int KEY_LEFT = -3;
	public static final int KEY_RIGHT = -4;
	public static final int KEY_CENTER = -5;
    public static final int KEY_BACKSPACE = -8;
	public static final int KEY_0 = 48;
	public static final int KEY_1 = 49;
	public static final int KEY_2 = 50;
	public static final int KEY_3 = 51;
	public static final int KEY_4 = 52;
	public static final int KEY_5 = 53;
	public static final int KEY_6 = 54;
	public static final int KEY_7 = 55;
	public static final int KEY_8 = 56;
	public static final int KEY_9 = 57;
	public static final int KEY_ASTERISK = 42;
	public static final int KEY_POUND = 35;


	public final int MAX_SIZE = 3000; // Max length of string

    private boolean isFirstRun=true;	// Флаг первого запуска.
    private MultiLineText mlt;

    protected String out = "";			// String to show
    protected boolean isActive=false;	// Should user type?
	protected Vector cmdHistory = new Vector();	// Stores the commands in this vector;
	protected int cmdHystoryIndex = 0;

	private Timer actionTimer = new Timer();
	private int keyCode = 0;		// What key has been pressed last time
	private int keyLastCode = 0;	// What key was pressed in the last time
	private int numPress = 0;		// The number of press shows what letter coresponds for the key
	private long idleTime = 0;		// How mach time (in ms) there is no any key press

    protected Command backCommand;


    class KeyTask extends TimerTask		// This class starts new key event
	{
        public void run()
        {
        	idleTime += StringConsts.I_KEY_TIME;
        	//keyPressed(keyCode);
        	if ( (keyCode != KEY_NULL) && (keyCode != KEY_LEFT) && (keyCode != KEY_RIGHT) ) {
    			isFirstRun = true;
    		}
    		repaint();
        }
    }

    public EnterBox() {
        startEnter();
    }

    public EnterBox(String text) {
        startEnter();
        print(text);
    }

    public void startEnter() {
		out += ">";
		isActive = true;
		isFirstRun = true;
		repaint();
	}

	public void eraseAll() {	// Erase all text
		out = "";
		//startEnter();
	}

	public void eraseLine() {	// Erase 1 current line
		int l = out.length();
		while (l>0)
        {
            if (out.substring(l-1,l).compareTo(">") == 0) {
            	break;
            }
            l--;
        }
		out = out.substring(0, l-1);
		startEnter();
	}

	public void print(String s) {
		out += s;
        if ( out.length() > MAX_SIZE ) out = out.substring(out.length() - MAX_SIZE);
		isFirstRun = true;
		repaint();
	}

	public void println(String s) {
        print(s + "\n");
	}

	public void show(Command backCommand) {
        this.backCommand = backCommand;
		isFirstRun = true;
		if ( out.length() > StringConsts.I_TEXT_MAX_LENGTH ) {
			out = out.substring(out.length() - StringConsts.I_TEXT_MAX_LENGTH);
		}
		MyRangeMIDlet.setDisplayable(this);
	}

	protected void paint( Graphics g ) {

		// x и y - координаты верхнего левого угла игрового экрана.
	    int x = g.getClipX();
	    int y = g.getClipY();
	    // w и h ширина и высота зоны экрана.
	    int w = g.getClipWidth();
	    int h = g.getClipHeight();

	    if (isFirstRun) {
            //При первом запуске программы создаем объект MultiLineText
            //и задаем параметры текста.
            mlt = new MultiLineText();
            mlt.SetTextPar(x, y, w, h, StringConsts.I_TEXT_SPEED, F_CONSOLE, g, out);
            isFirstRun = false;
        }

	    g.setColor(I_COLOR_BACKGROUND);
	    g.fillRect(0, 0, w, h);
	    // Draw text
	    g.setColor(I_COLOR_SMALL_TEXT);
	    mlt.DrawBottomMultStr();
	    // Daw cursor
	    int fontH = g.getFont().getHeight();
	    int fontW = g.getFont().charWidth('0');
	    if (isActive) {
		    g.setColor(I_COLOR_CURSOR);
		    g.fillRect(mlt.getLastStringWidth() + 1, h - fontH - 1, fontW, fontH);
	    }

	}

	protected void keyPressed(int keyCode) {
		if (!isActive) return;
		//if (keyCode != MyConsts.KEY_NULL){
		if ( (this.keyLastCode != keyCode) || (idleTime > StringConsts.I_KEY_RELEASE_TIME) ) {
			numPress = 0;
		}
		idleTime = 0;
		//}

		switch (keyCode) {
			case KEY_CENTER :	// Fire button
				pushFire();
				break;
			case KEY_UP :		// Show the previous command
				if (cmdHystoryIndex > 0) {
					eraseLine();
					cmdHystoryIndex--;
					print(cmdHistory.elementAt(cmdHystoryIndex).toString());
				}
				break;
			case KEY_DOWN :		// Show the next command
				eraseLine();
				if (cmdHystoryIndex < cmdHistory.size()) cmdHystoryIndex++;
				if ( (cmdHystoryIndex > 0) && (cmdHystoryIndex < cmdHistory.size()) ) {
					print(cmdHistory.elementAt(cmdHystoryIndex).toString());
				}
				break;
			case KEY_LEFT :		// Erase the last symbol if it is not ">"
				mlt.PageUp();
				break;
			case KEY_RIGHT :		// Add the spacer
				mlt.PageDown();
				break;
			case KEY_0 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_0_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_0_SYMB.length) {
					numPress = 0;
				}
				break;
			case KEY_1 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_1_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_1_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_2 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_2_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_2_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_3 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_3_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_3_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_4 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_4_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_4_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_5 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_5_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_5_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_6 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_6_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_6_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_7 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_7_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_7_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_8 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_8_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_8_SYMB.length) {
					numPress = 1;
				}
				break;
			case KEY_9 :
				if(numPress != 0) {
					out = out.substring(0, out.length()-1);
				}
				out += StringConsts.C_KEY_9_SYMB[numPress];
				numPress++;
				if(numPress == StringConsts.C_KEY_9_SYMB.length) {
					numPress = 1;
				}
				break;
            case KEY_BACKSPACE :	// Erase 1 symbol
				if (out.substring(out.length()-1, out.length()).compareTo(">") != 0) {
					out = out.substring(0, out.length()-1);
	            }
				break;
			case KEY_ASTERISK :	// Erase 1 symbol
				if (out.substring(out.length()-1, out.length()).compareTo(">") != 0) {
					out = out.substring(0, out.length()-1);
	            }
				break;
			case KEY_POUND :		// Add spacer
				out += " ";
				break;
			case KEY_NULL :
				// There is nothing to do
				break;
			default :
				//print(Integer.toString(keyCode));
				//println(getKeyName(keyCode));
				break;
		}

		if ( (keyCode != KEY_NULL) && (keyCode != KEY_LEFT) && (keyCode != KEY_RIGHT) ) {
			keyLastCode = keyCode;
			isFirstRun = true;
		}
		this.keyCode = keyCode;
		repaint();
	}

	public void keyReleased(int keyCode){
		//this.keyCode = MyConsts.KEY_NULL;
	}

	protected abstract void pushFire();

    protected void showNotify() {
        KeyTask task = new KeyTask();
		actionTimer = new Timer();
		actionTimer.schedule(task, 0, StringConsts.I_KEY_TIME); // start the key press timer
	}

	protected void hideNotify() {
		actionTimer.cancel();       // stop the key press timer
		//MyMIDlet.mainmenu.options.show();
	}

}
