package entity;

public abstract class NPC extends Entity {
    String [] dialogue;
    public int dialogueLength;
    public int currentDialogue;

  
    void loadDialogue (String[] s) {
        this.dialogue = new String [s.length];
        System.arraycopy(s, 0, this.dialogue, 0, s.length);
        this.currentDialogue = 0;
    }

    Boolean checkDialogue(){
        if(currentDialogue == dialogueLength-1) return true;
        return false;
    }

    void updateDialogue(){
        if(currentDialogue < dialogueLength) currentDialogue++;
    }

    public String speak(){
    
        if(currentDialogue == dialogueLength){
            return dialogue[dialogueLength-1];
        }
        String text = dialogue[currentDialogue];
        return text;
    }
}
