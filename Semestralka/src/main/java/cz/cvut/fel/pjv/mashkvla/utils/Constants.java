package cz.cvut.fel.pjv.mashkvla.utils;

import cz.cvut.fel.pjv.mashkvla.starter.Game;

/**
 * Contains constants used in the game.
 */
public class Constants {

    public static int ANIMATION_SPEED = 25;

    public static class ObjectConstants{
        public static final int COIN = 0;

        public static final int COIN_SPAWN_WIDTH = Game.GAME_WIDTH;
        public static final int COIN_FALL_SPEED = 1;

        public static final int COIN_SIZE_DEFAULT = 20;
        public static final int COIN_SIZE = (int)(COIN_SIZE_DEFAULT * Game.SCALE);

        public static final int COIN_SPRITE_AMOUNT = 9;
    }

    public static class Bullet{
        public static final int PLAYER_SHOOTER = 0;
        public static final int ENEMY_SHOOTER = 1;

        public static final int BULLET_WIDTH_DEFAULT = 5;
        public static final int BULLET_HEIGHT_DEFAULT = 12;
        public static final int BULLET_WIDTH = (int)(BULLET_WIDTH_DEFAULT * Game.SCALE);
        public static final int BULLET_HEIGHT = (int)(BULLET_HEIGHT_DEFAULT * Game.SCALE);
        public static final float BULLET_SPEED = 0.6f * Game.SCALE;

    }

    public static class EnemyConstants{
        public static final int UFO = 0;
        public static final int ALIEN = 1;

        public static final int DEFAULT_DAMAGE = 1;
        public static final int DEFAULT_HEALTH = 1;

        public static final int IDLE = 0;
        public static final int TURNING = 1;
        public static final int BOOM = 2;

        public static final int ENEMY_SIZE_DEFAULT = 32;
        public static final int ENEMY_SIZE = (int)(ENEMY_SIZE_DEFAULT * Game.SCALE);

        public static final int UFO_DRAW_OFFSET_Y = (int)(8 * Game.SCALE);

        public static final int ALIEN_DRAW_OFFSET_X = (int)(3 * Game.SCALE);
        public static final int ALIEN_DRAW_OFFSET_Y = (int)(2 * Game.SCALE);

        public static int s_getHealth(){
            return DEFAULT_HEALTH;
        }

        public static int s_getDamage(){
            return DEFAULT_DAMAGE;
        }

        public static int  s_getSpriteAmount(int enemyType, int enemyState){
            switch (enemyType){
                case UFO:
                    switch (enemyState) {
                        case IDLE -> {
                            return 8;
                        }
                        case TURNING -> {
                            return 4;
                        }
                        case BOOM -> {
                            return 9;
                        }
                    }
                case ALIEN:
                    switch (enemyState) {
                        case IDLE -> {
                            return 6;
                        }
                        case TURNING -> {
                            return 3;
                        }
                        case BOOM -> {
                            return 7;
                        }
                    }
            }
                return 0;
        }
    }

    public static class Environment{
        public static final int STARS_WIDTH_DEFAULT = 272;
        public static final int STARS_HEIGHT_DEFAULT = 160;
        public static final int STARS_WIDTH = (int)(STARS_WIDTH_DEFAULT * Game.SCALE);
        public static final int STARS_HEIGHT = (int)(STARS_HEIGHT_DEFAULT * Game.SCALE);

        public static final int RING_PLANET_WIDTH_DEFAULT = 51;
        public static final int RING_PLANET_HEIGHT_DEFAULT = 115;
        public static final int RING_PLANET_WIDTH = (int)(RING_PLANET_WIDTH_DEFAULT * Game.SCALE);
        public static final int RING_PLANET_HEIGHT = (int)(RING_PLANET_HEIGHT_DEFAULT * Game.SCALE);

        public static final int FAR_PLANETS_WIDTH_DEFAULT = 272;
        public static final int FAR_PLANETS_HEIGHT_DEFAULT = 160;
        public static final int FAR_PLANETS_WIDTH = (int)(FAR_PLANETS_WIDTH_DEFAULT * Game.SCALE);
        public static final int FAR_PLANETS_HEIGHT = (int)(FAR_PLANETS_HEIGHT_DEFAULT * Game.SCALE);

        public static final int BIG_PLANET_WIDTH_DEFAULT = 88;
        public static final int BIG_PLANET_HEIGHT_DEFAULT = 87;
        public static final int BIG_PLANET_WIDTH = (int)(BIG_PLANET_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_PLANET_HEIGHT = (int)(BIG_PLANET_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class UI{
        public static class Buttons{
            public static final int SHOOTING_SPEED_UPDATE = 1;
            public static final int HEALTH_UPDATE = 2;

            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class VolumeButtons{
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int)(VOLUME_WIDTH_DEFAULT * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_HEIGHT_DEFAULT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }

        public static class PauseButtons{
            public static final int SOUND_B_SIZE_DEFAULT = 42;
            public static final int SOUND_B_SIZE = (int)(SOUND_B_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons{
            public static final int URM_B_SIZE_DEFAULT = 56;
            public static final int URM_B_SIZE = (int)(URM_B_SIZE_DEFAULT * Game.SCALE);
        }
    }


    public static class Directions{
        public static final int LEFT = 1;
        public static final int RIGHT = 2;
    }

    public static class PlayerConstants{

        public static final int IDLE = 0;
        public static final int TURNING = 1;
        public static final int BOOST = 2;
        public static final int SLOWING_DOWN = 3;
        public static final int BOOM = 4;
        public static final int DAMAGE = 5;

        public static int s_getSpriteAmount(int player_action){
            return switch (player_action) {
                case IDLE, BOOST -> 10;
                case BOOM -> 9;
                case TURNING -> 4;
                case SLOWING_DOWN -> 5;
                case DAMAGE -> 3;
                default -> 1;
            };
        }
    }
}
