package ru.com.notnull.mygame.listeners;

import com.badlogic.gdx.physics.box2d.*;
import ru.com.notnull.mygame.common.Const;
import ru.com.notnull.mygame.model.GameObject;
import ru.com.notnull.mygame.screens.GameScreen;

public class SimpleContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            GameObject tmpB = (GameObject) b.getUserData();
            GameObject tmpA = (GameObject) a.getUserData();

            if (tmpA.getName() != null && tmpB.getName() != null) {
                if (tmpA.getName().equals("box") && tmpB.getName().equals("hero")) {
                    GameScreen.bodies.add(a.getBody());
                    tmpA.setDestroyed(true);
                }
                if (tmpA.getName().equals("hero") && tmpB.getName().equals("box")) {
                    GameScreen.bodies.add(b.getBody());
                    tmpB.setDestroyed(true);
                }

                if (tmpA.getName().equals("bottom") && tmpB.getName().equals("hero")) {
                    tmpB.setDie();
                }
                if (tmpA.getName().equals("hero") && tmpB.getName().equals("bottom")) {
                    tmpA.setDie();
                }
                if ((tmpA.getName().equals("exitDoor") && tmpB.getName().equals("hero")) || (tmpA.getName().equals("hero") && tmpB.getName().equals("exitDoor"))) {
                    if (!Const.mapNUmbChanged) {
//                        TODO дополнительное условие для проверки наличия карт
                        if (Const.mapOfGameMaps.containsKey(Const.mapNumb + 1)) {
                            Const.mapNumb += 1;
                        } else {
                            Const.mapNumb = 1;

                        }
                        Const.mapNUmbChanged = true;
                    }
                }


            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if (a.getUserData() != null && b.getUserData() != null) {
            GameObject tmpB = (GameObject) b.getUserData();
            GameObject tmpA = (GameObject) a.getUserData();

            if (tmpA.getName() != null && tmpB.getName() != null) {
                if (tmpA.getName().equals("ground") && !tmpB.getName().equals("ground")) {
                    GameObject tmp = tmpA;
                    tmpA = tmpB;
                    tmpB = tmp;
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
