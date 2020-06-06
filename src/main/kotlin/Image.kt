import geometry.Point3F
import materials.CheckeredMaterial
import materials.UniformMaterial
import objects.Plane
import objects.Sphere
import scene.Camera
import scene.Scene
import scene.lights.LightPoint

fun main() {
    createScene().renderToFile("output/images/${System.currentTimeMillis()}.png") {
        println("$it%")
    }
}

fun createScene(): Scene {
    return Scene(
        camera = Camera(
            position = Point3F(-1f, 1f, 1f),
            lookAt = Point3F(0f, 0.3f, -1f),
            planeNormal = Point3F.yUnit(),
            width = 1280,
            height = 720
        ),
        lights = listOf(
            LightPoint(Point3F(-5f, 5f, -1.5f)),
            LightPoint(Point3F(5f, 5f, 5f), diffuseIntensity = Point3F.ones() * 0.5f)
        ),
        objects = listOf(
            Sphere(Point3F(0.6f, 0.35f, -0.6f), 0.35f, UniformMaterial(Point3F(0.5f, 0f, 0.5f))),
            Sphere(Point3F(-0.8f, 0.2f, -0.7f), 0.2f, UniformMaterial(Point3F(0f, 0.6f, 0f))),
            Sphere(Point3F(0f, 0.3f, -1f), 0.3f, UniformMaterial(Point3F(0.5f, 0f, 0f))),
            Plane(Point3F.origin(), Point3F.yUnit(), CheckeredMaterial())
        )
    )
}