package ru.com.notnull.mygame.beans;

public class Avatar {
    private Anim attack;
    private Anim die;
    private Anim hurt;
    private Anim idle;
    private Anim jump;
    private Anim walk;
    private Anim run;


    private int positionX;
    private int positionY;
    private int moveSpeed;
    private boolean lookForward;
    private float time;





    public void dispose() {
        attack.dispose();
        die.dispose();
        hurt.dispose();
        idle.dispose();
        jump.dispose();
        walk.dispose();
        run.dispose();
    }
}
