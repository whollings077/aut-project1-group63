/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuizProgram;

/**
 *
 * @author GGPC
 */
class Player {
    private String name;
    private int winnings;
    private int lifelineCount;
    private String difficulty;
    
    Player(String name, int winnings, int lifelineCount, String difficulty) {
        this.name = name;
        this.winnings = winnings;
        this.lifelineCount = lifelineCount;
        this.difficulty = difficulty;
    }
    
    public String getName(){
        return name;
    }
    
    public int getWinnings(){
        return winnings;
    }
    
    public int getLifelineCount(){
        return lifelineCount;
    }
    
    public String getDifficulty(){
        return difficulty;
    }
}
