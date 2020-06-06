package materials

import geometry.Point3F
import scene.lights.Light
import kotlin.math.max
import kotlin.math.pow

class UniformMaterial(
    private val diffuse: Point3F,
    private val ambient: Point3F = diffuse * 0.1f,
    private val specular: Point3F = Point3F.ones(),
    private val shininess: Float = 100f,
    private val reflectivity: Float = 0.5f
): Material {
    private val color = Point3F()

    override fun getColor(point: Point3F, normal: Point3F, toLight: Point3F, toCamera: Point3F, light: Light): Point3F {
        color.set(0f, 0f, 0f)
        val diffuseFactor = toLight.dot(normal)
        if (diffuseFactor < 0)
            return color

        // ambient
        color.set(color + ambient * light.ambientIntensity)

        // diffuse
        color.set(color + diffuse * light.diffuseIntensity * max(0f, diffuseFactor))

        // specular
        val h = (toCamera + toLight).normalized()
        color.set(color + specular * light.specularIntensity * max(0f, h.dot(normal).pow(shininess / 4f)))
        return color
    }

    override fun getReflectivity(point: Point3F): Float {
        return reflectivity
    }
}
