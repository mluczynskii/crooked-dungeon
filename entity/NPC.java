package entity;

public abstract class NPC extends Entity {
    String [] dialogue;
    String dialogueEnd;
    int currentDialogue = 0;

    void loadDialogue (String[] s) {
        this.dialogue = new String [s.length];
        System.arraycopy(s, 0, this.dialogue, 0, s.length);
    }

    public String speak(){
        String text = dialogue[currentDialogue];
        return text;
    }
}
