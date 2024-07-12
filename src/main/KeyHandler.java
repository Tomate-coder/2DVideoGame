package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, upRightPressed, upLeftPressed, downPressed, downLeftPressed, downRightPressed,
            leftPressed, rightPressed, shiftPressed, spacePressed, enterPressed;
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // Se pide que se añadan estos métodos no implementados a pesar que no se ocupen

    public void keyTyped(KeyEvent e) {
        // Este no se ocupa para nada pero aún así debe estar
    }

    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }

            }
        }

        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W && code == KeyEvent.VK_D) {
                // upRightPressed = true;
                upPressed = true;
                rightPressed = true;

            }
            if (code == KeyEvent.VK_A && code == KeyEvent.VK_W) {
                // upLeftPressed = true;
                upPressed = true;
                leftPressed = true;
            }
            if (code == KeyEvent.VK_S && code == KeyEvent.VK_A) {
                // downLeftPressed = true;
                downPressed = true;
                leftPressed = true;

            }

            if (code == KeyEvent.VK_D && code == KeyEvent.VK_S) {
                // downRightPressed = true;
                downPressed = true;
                rightPressed = true;

            }

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }

            if (code == KeyEvent.VK_S) {
                downPressed = true;

            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;

            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;

            }
            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;

            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;

            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }

            // DEBUG
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                } else if (checkDrawTime == true) {
                    checkDrawTime = false;
                }
            }
        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                // gp.playMusic(0);

            }
        }
        if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.playState;
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W && code == KeyEvent.VK_D) {
            upRightPressed = false;
        }

        if (code == KeyEvent.VK_S && code == KeyEvent.VK_A) {
            downLeftPressed = false;

        }
        if (code == KeyEvent.VK_A && code == KeyEvent.VK_W) {
            upLeftPressed = false;

        }
        if (code == KeyEvent.VK_D && code == KeyEvent.VK_S) {
            downRightPressed = false;

        }

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;

        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;

        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;

        }
    }

}
