package wms.code2music;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class MusicalPhrase implements List<Note>
{
    Stack<Note> notes;

    public MusicalPhrase()
    {
        notes = new Stack<>();
    }

    public void parseFromString( String stringToParse, String delimiter )
    {
        String[] strArray = stringToParse.split(delimiter);
        Iterator<String> it = Arrays.asList(strArray).iterator();
        while( it.hasNext() )
        {
            String[] vals = it.next().split(":");
            String noteVal = vals[0];
            String noteOct = vals[1];
            Note newNote = new Note(noteVal, Integer.parseInt(noteOct));
            newNote.setNoteValue();
            this.add(newNote);
        }
    }

    public void printStack()
    {
        Iterator<Note> it = notes.iterator();
        while( it.hasNext())
        {
            Note note = it.next();
            System.out.println("orig Note= " + note.getValue() + " value=" + note.getNatValue() + " oct=" + note.getOctave() + " isNat=" + note.isWholeNote() + " midiEvent=" + note.getMidiEvent()+ " myNoteVal=" + note.getNoteValue() );
        }
    }
    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public boolean isEmpty() {
        return notes.empty();
    }

    @Override
    public boolean contains(Object o) {
        return notes.contains(o);
    }

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    @Override
    public Object[] toArray() {
        return notes.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection c) {
        return notes.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return notes.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        notes.clear();
    }

    @Override
    public Note get(int index) {
        return notes.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return notes.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(int index, Note element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(Note e) {
       return notes.add(e);
    }

    @Override
    public Note set(int index, Note element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Note remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object o) {
        return notes.remove(o);
    }
}
