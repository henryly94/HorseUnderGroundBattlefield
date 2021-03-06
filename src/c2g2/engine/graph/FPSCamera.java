package c2g2.engine.graph;

import c2g2.engine.FPSGameItem;
import c2g2.engine.GameItem;
import c2g2.game.FPSGame;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.PrimitiveIterator;

public class FPSCamera extends Camera {


    private float yaw;

    private float pitch;

    public enum DIRECTION {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    public FPSCamera(){
        super();
    }

    public void move(DIRECTION direction, float offset){
        float sinYaw = (float)Math.sin(Math.toRadians(yaw));
        float cosYaw = (float)Math.cos(Math.toRadians(yaw));
        float cosPitch = (float)Math.cos(Math.toRadians(pitch));

        Vector3f forward = new Vector3f();
        forward.x = -sinYaw * cosPitch;
        forward.y = 0;
        forward.z = -cosYaw * cosPitch;
        forward.normalize();

        Vector3f side = new Vector3f();
        side.x = cosYaw;
        side.y = 0;
        side.z = -sinYaw;
        side.normalize();
        float deltaX=0, deltaZ=0;
        switch (direction){
            case FORWARD:
                deltaX = forward.x * offset;
                deltaZ = forward.z * offset;
                break;
            case BACKWARD:
                deltaX = -forward.x * offset;
                deltaZ = - forward.z * offset;
                break;
            case RIGHT:
                deltaX = side.x * offset;
                deltaZ = side.z * offset;
                break;
            case LEFT:
                deltaX = -side.x * offset;
                deltaZ= -side.z * offset;
                break;
        }
        position.x += deltaX;
        position.z += deltaZ;

//        Vector3f itemPos = new Vector3f(fpsGameItem.getPosition());
//        itemPos.add(deltaX, 0, deltaZ);
//        fpsGameItem.setPosition(itemPos.x, itemPos.y, itemPos.z);
    }

    public void recoil(){
        double rec_ver = 3.5;
        double rec_hor = 3;
        double ver = Math.random();
        double hor = Math.random();

        rotateTarget((float)(rec_hor * (hor - 0.5)), (float)(rec_ver * ver), 0.7f);
    }


    public void rotateTarget(float yaw_X,  float pitch_Y, float step){

        yaw = (yaw + yaw_X *step + 360) % 360;

        if (pitch_Y*step + pitch > 89){
            pitch = 89;
        } else if (pitch_Y*step + pitch < -30){
            pitch = -30;
        } else {
            pitch += pitch_Y *step;
        }

        float sinYaw = (float)Math.sin(Math.toRadians(yaw));
        float sinPitch = (float)Math.sin(Math.toRadians(pitch));
        float cosYaw = (float)Math.cos(Math.toRadians(yaw));
        float cosPitch = (float)Math.cos(Math.toRadians(pitch));
        target.x = -sinYaw * cosPitch;
        target.y = sinPitch;
        target.z = -cosYaw * cosPitch;
        target.normalize();
    }
}
