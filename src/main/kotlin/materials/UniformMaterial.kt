package materials

import geometry.Point3F
import scene.lights.Light
import kotlin.math.max
import kotlin.math.pow

class UniformMaterial(
    private val diffuse: Point3F,
    private val ambient: Point3F = diffuse * 0.1f,
    private val specular: Point3F = Point3F.ones(),
    private val shininess: Float = 100f
): Material {
    override fun getColor(point: Point3F, normal: Point3F, toLight: Point3F, toCamera: Point3F, light: Light): Point3F {
        var color = Point3F()
        val diffuseFactor = toLight.dot(normal)
        if (diffuseFactor < 0)
            return color

        // ambient
        color += ambient * light.ambientIntensity

        // diffuse
        color += diffuse * light.diffuseIntensity * max(0f, diffuseFactor)

        // specular
        color += specular * light.specularIntensity * max(0f, (toCamera + toLight).normalized().dot(normal).pow(shininess / 4f))
        return color
    }
}
