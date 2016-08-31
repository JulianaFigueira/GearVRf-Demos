package org.gearvrf.gvrbullet;

import java.io.IOException;

import org.gearvrf.FutureWrapper;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRMeshCollider;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.physics.GVRPhysicsWorld;
import org.gearvrf.physics.GVRRigidBody;
import org.gearvrf.utility.Log;
import org.siprop.bullet.Geometry;
import org.siprop.bullet.MotionState;
import org.siprop.bullet.RigidBody;
import org.siprop.bullet.Transform;
import org.siprop.bullet.shape.BoxShape;
import org.siprop.bullet.shape.SphereShape;
import org.siprop.bullet.shape.StaticPlaneShape;
import org.siprop.bullet.util.Point3;
import org.siprop.bullet.util.ShapeType;
import org.siprop.bullet.util.Vector3;

import android.graphics.Color;

public class BulletSampleMain extends GVRMain {

    private GVRContext mGVRContext = null;
    private static final float CUBE_MASS = 0.01f;
    private GVRScene mScene;

    @Override
    public void onInit(GVRContext gvrContext) throws Throwable {
        mGVRContext = gvrContext;

        mScene = mGVRContext.getNextMainScene();

        GVRCameraRig mainCameraRig = mScene.getMainCameraRig();
        mainCameraRig.getLeftCamera().setBackgroundColor(Color.BLACK);
        mainCameraRig.getRightCamera().setBackgroundColor(Color.BLACK);
        mainCameraRig.getTransform().setPosition(0.0f, 6.0f, 0.0f);

        /*
         * Create the ground. A simple textured quad. In bullet it will be a
         * plane shape with 0 mass
         */
        GVRSceneObject groundScene = quadWithTexture(100.0f, 100.0f, "floor.jpg");
        groundScene.getTransform().setRotationByAxis(-90.0f, 1.0f, 0.0f, 0.0f);
        groundScene.getTransform().setPosition(0.0f, -100.0f, 0.0f);
        groundScene.attachCollider(new GVRMeshCollider(mGVRContext, groundScene.getRenderData().getMesh()));
        groundScene.attachComponent(new GVRRigidBody(mGVRContext, 0, groundScene.getCollider(), groundScene.getTransform()));

        mScene.addSceneObject(groundScene);
        /*
         * Create Some cubes in Bullet world and hit it with a sphere
         */
        addCube(mScene, 0.0f, 10.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 0.0f, 10.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 0.0f, 10.0f, -11.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 10.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 10.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 10.0f, -11.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 10.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 10.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 10.0f, -11.0f, CUBE_MASS);

        addCube(mScene, 0.0f, 11.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 0.0f, 11.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 0.0f, 11.0f, -11.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 11.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 11.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 11.0f, -11.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 11.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 11.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 11.0f, -11.0f, CUBE_MASS);

        addCube(mScene, 0.0f, 12.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 0.0f, 12.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 0.0f, 12.0f, -11.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 12.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 12.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 1.0f, 12.0f, -11.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 12.0f, -9.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 12.0f, -10.0f, CUBE_MASS);
        addCube(mScene, 2.0f, 12.0f, -11.0f, CUBE_MASS);

        /*
         * Throw a sphere from top
         */
        addSphere(mScene, 1.0f, 0.0f, 10.0f, -9.0f, 0.01f);

    }

    @Override
    public void onStep() {
        GVRPhysicsWorld.step();
    }

    private GVRSceneObject quadWithTexture(float width, float height,
            String texture) {
        FutureWrapper<GVRMesh> futureMesh = new FutureWrapper<GVRMesh>(
                mGVRContext.createQuad(width, height));
        GVRSceneObject object = null;
        try {
            object = new GVRSceneObject(mGVRContext, futureMesh,
                    mGVRContext.loadFutureTexture(new GVRAndroidResource(
                            mGVRContext, texture)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    private GVRSceneObject meshWithTexture(String mesh, String texture) {
        GVRSceneObject object = null;
        try {
            object = new GVRSceneObject(mGVRContext, new GVRAndroidResource(
                    mGVRContext, mesh), new GVRAndroidResource(mGVRContext,
                    texture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    /*
     * Function to add a cube of unit size with mass at the specified position
     * in Bullet physics world and scene graph.
     */
    private void addCube(GVRScene scene, float x, float y, float z, float mass) {

        GVRSceneObject cubeObject = meshWithTexture("cube.obj", "cube.jpg");
        cubeObject.attachCollider(new GVRMeshCollider(mGVRContext, cubeObject.getRenderData().getMesh()));
        cubeObject.getTransform().setPosition(x, y, z);
        cubeObject.attachComponent(new GVRRigidBody(mGVRContext, mass, cubeObject.getCollider(), cubeObject.getTransform()));

        scene.addSceneObject(cubeObject);
    }

    /*
     * Function to add a sphere of dimension and position specified in the
     * Bullet physics world and scene graph
     */
    private void addSphere(GVRScene scene, float radius, float x, float y,
            float z, float mass) {

        GVRSceneObject sphereObject = meshWithTexture("sphere.obj", "sphere.jpg");
        sphereObject.attachCollider(new GVRMeshCollider(mGVRContext, sphereObject.getRenderData().getMesh()));
        sphereObject.getTransform().setPosition(x, y, z);
        sphereObject.attachComponent(new GVRRigidBody(mGVRContext, mass, sphereObject.getCollider(), sphereObject.getTransform()));

        scene.addSceneObject(sphereObject);
    }

}
