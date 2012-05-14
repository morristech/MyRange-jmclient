/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.canvases;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import ru.myrange.MyRangeMIDlet;
import ru.myrange.stuff.StringConsts;
import ru.myrange.stuff.StaticActions;

/**
 * A text console
 * @author Oleg Ponfilenok
 */
public class Console extends EnterBox implements CommandListener
{
	public Command cmdBack = new Command(StringConsts.S_BACK, Command.BACK, 99);
    public Command cmdEnter = new Command(StringConsts.S_ENTER, Command.SCREEN, 1);
    
    public Console() throws Exception
    {
    	super();

        this.addCommand(cmdBack);
    	this.addCommand(cmdEnter);
    	this.setCommandListener(this);
    	
    	// Create the initial command history
		for (int i=0; i<StringConsts.I_CONSCDM_NUM; i++) {
			cmdHistory.addElement(StringConsts.S_CONSCDM[i]);   // All commands
            //cmdHistory.addElement(MyConsts.S_CONSCDM[i] + " " + MyConsts.S_ATR_HELP);   // All commands with help
		}
		cmdHystoryIndex = cmdHistory.size();
    }

    private String parseCommand() {		// This routine parse the last user command
		String command = "";
		int l = out.length();
		while (l>0)
        {
            if (out.substring(l-1,l).compareTo(">") == 0) {
            	break;
            } else {
            	command = out.substring(l-1,l) + command;
            }
            l--;
        }
		return command;
	}
	
	protected void pushFire() {
		commandAction(cmdEnter, this);
	}
	
	public void commandAction(Command c, Displayable d) {
    	if (c == cmdBack) {		// Hide the Sync console
    		MyRangeMIDlet.SCREENS[MyRangeMIDlet.MAINMENU_SCREEN].run(backCommand);
        }
    	else if (c == cmdEnter) {
            // Enter
            final String command = parseCommand();
            out += "\n";
            isActive = false;
            // Store the command in the history
            if (!command.equals("")) {
                cmdHistory.addElement(command);
            }
            cmdHystoryIndex = cmdHistory.size();
            // Start the command action
            Thread threadCommand = new Thread()
            {
                public void run(){
                    try {
                        //StaticActions.cmdAction(command);
                    } catch (Exception e) {
                        println("Error:" + e.getMessage());
                        startEnter();
                    }
                }
            };
            threadCommand.start();
    	}
    }

}
