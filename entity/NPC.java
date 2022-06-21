package entity;

public abstract class NPC extends Entity {
    String [] dialogue;
    public int dialogueLength;
    public int currentDialogue;
    public int nextDialogue;

    void loadDialogue (String[] s) {
        this.dialogue = new String [s.length];
        System.arraycopy(s, 0, this.dialogue, 0, s.length);
    }

    public String speak(){

        if(currentDialogue == dialogueLength){
            currentDialogue = dialogueLength-1;
            return ""; 
        }
        currentDialogue = nextDialogue;
        String text = dialogue[currentDialogue];
        return text;
    }
}
