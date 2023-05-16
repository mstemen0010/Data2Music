package wms.code2music;

public class Note {

    /**
     * @return the natValue
     */
    public String getNatValue() {
        return natValue;
    }

    /**
     * @return the midiEvent
     */
    public int getMidiEvent() {
        return midiEvent;
    }

    /**
     * @param midiEvent the midiEvent to set
     */
    public void setMidiEvent(int midiEvent) {
        this.midiEvent = midiEvent;
    }

    enum HalfStep
    {
        Undefined,
        Sharp,
        Flat;


    }

    public enum NoteValue {
        A(21),
        B(23),
        C(24),
        D(26),
        E(28),
        F(29),
        G(31);
        private int midiEventVal;
        private HalfStep halfStep = HalfStep.Undefined;

        NoteValue(int val) {
            this.midiEventVal = val;
        }

        public int getMidiNote() {
            return this.midiEventVal;
        }
        public void setHalfStep( HalfStep newHalfStep )
        {
            this.halfStep = newHalfStep;
        }

        public HalfStep getHalfStep()
        {
            return this.halfStep;
        }
    }

    // Note is A octave 0 (A0) on PianoRoll
    final private int baseMidiWholeNoteEvents[] = {21, 23, 24, 26, 28, 29, 31};
    final private int baseMidiHalfNoteEvent[] = {22, 25, 27, 30};

    private int myMidiNoteEventValue = -1;

    private NoteValue myNoteValue = NoteValue.A;
    private NoteValue myNatNoteValue = NoteValue.A;

    final static private int baseMidiNoteEventMult = 12;

    public Note(String value, int octave) {
        this.octave = octave;
        this.parseNote(value);

        this.value = value;
        if (value.contains("b") )
        {
            this.natValue = value.split("b")[0];
            this.setHalfNote();
        }
        else if (value.contains("#")) {
            this.natValue = value.split("#")[0];
            this.setHalfNote();
        } else {
            this.natValue = value;
            this.setWholeNote();
        }

    }

    public NoteValue parseNote(String noteToParse) {
        boolean isSharp = false;
        boolean isFlat = false;

        if (noteToParse.length() > 2) {
            return null;
        }
        if (noteToParse.contains("b")) {
            this.value = noteToParse.split("b")[0];
            isFlat = true;
            this.myNoteValue.setHalfStep(HalfStep.Flat);
            this.setHalfNote();
        } else if (noteToParse.contains("#")) {
            this.value = noteToParse.split("#")[0];
            this.myNoteValue.setHalfStep(HalfStep.Sharp);
            isSharp = true;
            this.setHalfNote();
        } else {
            this.value = noteToParse;
            this.setWholeNote();
        }

        if (this.value.toUpperCase().equals("A")) {
            myNoteValue = NoteValue.A;
        } else if (this.value.toUpperCase().equals("B")) {
            myNoteValue = NoteValue.B;
        } else if (this.value.toUpperCase().equals("C")) {
            myNoteValue = NoteValue.C;
        } else if (this.value.toUpperCase().equals("D")) {
            myNoteValue = NoteValue.D;
        } else if (this.value.toUpperCase().equals("E")) {
            myNoteValue = NoteValue.E;
        } else if (this.value.toUpperCase().equals("F")) {
            myNoteValue = NoteValue.F;
        } else if (this.value.toUpperCase().equals("G")) {
            myNoteValue = NoteValue.G;
        }
        this.myMidiNoteEventValue = myNoteValue.getMidiNote();
        if (isSharp) {
            this.myMidiNoteEventValue++;
        } else if (isFlat) {
            this.myMidiNoteEventValue--;
        }
        this.calcMidiEvent();
        return myNoteValue;
    }

    private boolean isWholeNote = false;
    private boolean isHalfNote = false;
    private String value;
    private String natValue;
    private int octave;
    private int midiBaseValue; // the midi value based octave 0 (i.e. 21 = A0, 22 = Bb0, etc)
    private int midiNote;
    private int midiEvent;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
        this.calcMidiEvent();
    }

    public NoteValue getNoteValue()
    {
        return this.myNoteValue;
    }

    public void setNoteValue() {
        this.parseNote(this.getValue());
        this.calcMidiEvent();
    }

    public int getMidiNoteEventNumber() {
        // return a midi number based on the current note
        int midiNoteEventNumber = -1;

        switch (myNoteValue) {
            case A:
                midiNoteEventNumber = NoteValue.A.getMidiNote();
                break;
            case B:
                midiNoteEventNumber = NoteValue.B.getMidiNote();
                break;
            case C:
                midiNoteEventNumber = NoteValue.C.getMidiNote();
                break;
            case D:
                midiNoteEventNumber = NoteValue.D.getMidiNote();
                break;
            case E:
                midiNoteEventNumber = NoteValue.E.getMidiNote();
                break;
            case F:
                midiNoteEventNumber = NoteValue.F.getMidiNote();
                break;
            case G:
                midiNoteEventNumber = NoteValue.G.getMidiNote();
                break;
        }
        return midiNoteEventNumber;
    }

    private int calcMidiEvent() {
        int octFactor = (12 * this.octave) - 12;
        if( myNoteValue == null )
            return -1;
        int midiNoteResult = -1;
        this.midiNote = getMidiNoteEventNumber();
        midiNoteResult = this.getMidiNoteEventNumber();
        if( isHalfNote && myNoteValue.getHalfStep().equals(HalfStep.Sharp))
        {
            midiNoteResult++;
        }
        else if( isHalfNote && myNoteValue.getHalfStep().equals(HalfStep.Flat))
        {
            midiNoteResult--;
        }

        midiNoteResult = midiNoteResult + octFactor;
        this.midiNote = midiNoteResult;
        this.setMidiEvent(midiNoteResult);
        return midiNoteResult;
    }

    public void setWholeNote() {
        isWholeNote = true;
        isHalfNote = false;
    }

    public boolean isHalfNote()
    {
        return this.isWholeNote;
    }
    public boolean isWholeNote()
    {
        return this.isWholeNote;
    }

    public void setHalfNote() {
        isWholeNote = false;
        isHalfNote = true;
    }

    /**
     * @return the octave
     */
    public int getOctave() {
        return octave;
    }

    /**
     * @param octave the octave to set
     */
    public void setOctave(int octave) {
        this.octave = octave;
    }

    /**
     * @return the midiNote
     */
    public int getMidiNote() {
        return midiNote;
    }

    /**
     * @param midiNote the midiNote to set
     */
    public void setMidiNote(int midiNote) {
        this.midiNote = midiNote;
        this.calcMidiEvent();
    }

}
