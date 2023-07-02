package cz.cvut.fel.pjv.mashkvla.ui;

import cz.cvut.fel.pjv.mashkvla.entities.Player;
import cz.cvut.fel.pjv.mashkvla.utils.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.mashkvla.utils.Constants.UI.Buttons.*;
import static cz.cvut.fel.pjv.mashkvla.utils.ImageManager.s_getSpriteAtlas;

/**
 * Represents an upgrade button used in the shop.
 */
public class UpgradeButton {
    private int xPos, yPos, rowIndex = 4, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private BufferedImage images[];
    private boolean mouseOver, mousePressed, shotSpeedCanBeUpdated, healthCanBeUpdated, enoughMoney;
    private Rectangle bounds;
    private int updateType;
    private int updateCost;

    /**
     * Initializes the UpgradeButton with the specified position, update type, and update cost.
     *
     * @param xPos       The x-coordinate of the button.
     * @param yPos       The y-coordinate of the button.
     * @param updateType The type of update the button represents.
     * @param updateCost The cost of the update.
     */
    public UpgradeButton(int xPos, int yPos, int updateType, int updateCost) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.updateType = updateType;
        this.updateCost = updateCost;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImages() {
            BufferedImage temp = s_getSpriteAtlas(ImageManager.MENU_BUTTONS);
            this.images = new BufferedImage[3];
            for (int i = 0; i < images.length; i++)
                this.images[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, this.rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    /**
     * Draws the upgrade button on the screen.
     *
     * @param g The graphics context.
     */
    public void draw(Graphics g){
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    /**
     * Updates the state of the upgrade button based on user interactions.
     */
    public void update(){
        this.index = 0;
        if(this.mouseOver)
            this.index = 1;
        if(this.mousePressed)
            this.index = 2;
    }

    /**
     * Checks if the mouse is currently over the upgrade button.
     *
     * @return True if the mouse is over the upgrade button, false otherwise.
     */
    public boolean isMouseOver() {
        return this.mouseOver;
    }

    /**
     * Sets the state of mouse over for the upgrade button.
     *
     * @param mouseOver The state of mouse over to set.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Checks if the upgrade button is currently being pressed.
     *
     * @return True if the upgrade button is being pressed, false otherwise.
     */
    public boolean isMousePressed() {
        return this.mousePressed;
    }

    /**
     * Sets the state of mouse pressed for the upgrade button.
     *
     * @param mousePressed The state of mouse pressed to set.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Returns the bounds of the upgrade button.
     *
     * @return The bounds of the upgrade button.
     */
    public Rectangle getBounds() {
        return bounds;
    }


    /**
     * Resets the mouse over and mouse pressed states of the upgrade button.
     */
    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    /**
     * Checks the upgrades based on the update type and applies the updates to the player if possible.
     *
     * @param player The player object to apply the upgrades to.
     */
    public void checkUpgrades(Player player) {
        if(mousePressed){
            enoughMoney = false;
            if(updateType == SHOOTING_SPEED_UPDATE){
                shotSpeedCanBeUpdated = updateCost <= player.getCollectedMoney();
                if(shotSpeedCanBeUpdated){
                    enoughMoney = true;
                    player.setPlayerShotDelay(player.getPlayerShotDelay() - 2);
                    player.setShipLvl(player.getShipLvl() + 1);
                    player.setCollectedMoney(player.getCollectedMoney() - updateCost);
                } else enoughMoney = false;
            }
            else if(updateType == HEALTH_UPDATE){
                healthCanBeUpdated = updateCost <= player.getCollectedMoney();
                if(healthCanBeUpdated){
                    enoughMoney = true;
                    player.setMaxAmountOfHearts(player.getMaxAmountOfHearts() + 1);
                    player.setShipLvl(player.getShipLvl() + 1);
                    player.setCollectedMoney(player.getCollectedMoney() - updateCost);
                } else enoughMoney = false;
            }
        }
    }

    /**
     * Checks if the player has enough money to perform the upgrade.
     *
     * @return True if the player has enough money, false otherwise.
     */
    public boolean isEnoughMoney() {
        return enoughMoney;
    }
}
