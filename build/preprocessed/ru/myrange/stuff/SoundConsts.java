/*
 * myRange J2ME client application
 * Copyright © 2009 MyRange Group
 */

package ru.myrange.stuff;

import javax.microedition.media.control.ToneControl;

/**
 * Diclaration of the sound constants
 *
 * @author Oleg Ponfilenok
 */
public class SoundConsts {

    // *** MELODIES ***
    // tempo
    private static final byte tempo = 60; // set tempo to 120 bpm
    private static final byte d = 8;      // eighth-note
    private static final byte q = 4;      // quarter-note
    // notes
    private static final byte C4 = ToneControl.C4;
    private static final byte G3 = (byte)(C4 - 5);
    private static final byte H3 = (byte)(C4 - 1);
    private static final byte D4 = (byte)(C4 + 2);
    private static final byte G4 = (byte)(C4 + 7);
    private static final byte rest = ToneControl.SILENCE; // rest
    // melodies
    public static final byte[] NEW_MSG_MELODY = {
        ToneControl.VERSION, 1,     // version 1
        ToneControl.TEMPO, tempo,   // set tempo
        ToneControl.BLOCK_START, 0,
        D4,d, H3,d, G3,d, rest,d,
        ToneControl.BLOCK_END, 0,
        ToneControl.PLAY_BLOCK, 0,
    };
    public static final byte[] USER_ENCOUNTERED_MELODY = {
        ToneControl.VERSION, 1,     // version 1
        ToneControl.TEMPO, tempo,   // set tempo
        ToneControl.BLOCK_START, 0,
        C4,d, G4,q, rest,d,
        ToneControl.BLOCK_END, 0,
        ToneControl.PLAY_BLOCK, 0,
    };

}
