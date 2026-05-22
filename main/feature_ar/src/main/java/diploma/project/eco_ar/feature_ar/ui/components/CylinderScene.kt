package diploma.project.eco_ar.feature_ar.ui.components

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.google.android.filament.utils.KTX1Loader
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.feature_ar.domain.InfoLayer
import io.github.sceneview.SceneView
import io.github.sceneview.SurfaceType
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.createEnvironment
import io.github.sceneview.createView
import io.github.sceneview.math.Position
import io.github.sceneview.node.CylinderNode
import io.github.sceneview.node.TextNode
import io.github.sceneview.rememberARView
import io.github.sceneview.rememberCameraManipulator
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironment
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberRenderer
import io.github.sceneview.utils.readBuffer

@Composable
fun CylinderScene(
    modifier: Modifier = Modifier,
    layer: InfoLayer,
    value: Float?
) {
    val context = LocalContext.current
    val colorTheme = LocalColorTheme.current

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val cameraNode = rememberARCameraNode(engine)
    val environment = rememberEnvironment(
        engine = engine,
        isOpaque = false,
        environment = {
            createEnvironment(
                engine = engine,
                isOpaque = false,
                indirectLight = KTX1Loader.createIndirectLight(engine, context.assets.readBuffer("environments/neutral/neutral_ibl.ktx")).indirectLight,
                skybox = null,
                sphericalHarmonics = null
            )
        }
    )
    val renderer = rememberRenderer(engine)
    val view = rememberARView(
        engine = engine,
        creator = {
            val view = createView(engine)
            view.colorGrading = null
            view.isPostProcessingEnabled = false
            view
        }
    )

    val distanceToCamera = -3.25f
    val unitPerM = remember(value) {
        if (value != null) {
            if (value < 25f) 0.05f else 0.05f * 25f / value
        } else {
            0.05f
        }
    }

    val optMin = layer.optimalMin ?: 0f
    val optMax = layer.optimalMax ?: 0f

    val optHeightRaw = remember(optMin, optMax, unitPerM) { (optMax - optMin) * unitPerM }
    val safeOptHeight = if (optHeightRaw <= 0f) 0.001f else optHeightRaw

    val optPositionY = remember(optMin, safeOptHeight, unitPerM) {
        -1f + (optMin * unitPerM) + (safeOptHeight / 2f)
    }

    val bgHeight = remember(optMin, unitPerM) { optMin * unitPerM }
    val safeBgHeight = if (bgHeight <= 0f) 0.001f else bgHeight
    val bgPositionY = remember(safeBgHeight) { -1f + (safeBgHeight / 2f) }

    val cylinderColor = remember (layer) {
        when (layer) {
            InfoLayer.TEMPERATURE -> colorTheme.colorTemperatures
            InfoLayer.HUMIDITY -> colorTheme.colorHumidities
            InfoLayer.PRESSURE -> colorTheme.colorPressures
            InfoLayer.WIND_SPEED -> colorTheme.colorWindSpeeds
            InfoLayer.WIND_DEGREE -> colorTheme.colorWindDegrees
            InfoLayer.AQI -> colorTheme.colorAQIs
            InfoLayer.PM2_5 -> colorTheme.colorPM2point5s
            InfoLayer.PM10 -> colorTheme.colorPM10s
            InfoLayer.NO2 -> colorTheme.colorNO2s
            InfoLayer.O3 -> colorTheme.colorO3s
            InfoLayer.CO -> colorTheme.colorCOs
        }
    }

    val backgroundCylinderColor = remember(cylinderColor) {
        cylinderColor.copy(alpha = 0.4f)
    }

    val textColor = remember (layer) {
        when (layer) {
            InfoLayer.TEMPERATURE -> colorTheme.colorTemperatures.toArgb()
            InfoLayer.HUMIDITY -> colorTheme.colorHumidities.toArgb()
            InfoLayer.PRESSURE -> colorTheme.colorPressures.toArgb()
            InfoLayer.WIND_SPEED -> colorTheme.colorWindSpeeds.toArgb()
            InfoLayer.WIND_DEGREE -> colorTheme.colorWindDegrees.toArgb()
            InfoLayer.AQI -> colorTheme.colorAQIs.toArgb()
            InfoLayer.PM2_5 -> colorTheme.colorPM2point5s.toArgb()
            InfoLayer.PM10 -> colorTheme.colorPM10s.toArgb()
            InfoLayer.NO2 -> colorTheme.colorNO2s.toArgb()
            InfoLayer.O3 -> colorTheme.colorO3s.toArgb()
            InfoLayer.CO -> colorTheme.colorCOs.toArgb()
        }
    }

    var backgroundCylinderNode by remember { mutableStateOf<CylinderNode?>(null) }
    var optimalCylinderNode by remember { mutableStateOf<CylinderNode?>(null) }
    var valueCylinderNode by remember { mutableStateOf<CylinderNode?>(null) }

    var optimalMinTextNode by remember { mutableStateOf<TextNode?>(null) }
    var optimalMaxTextNode by remember { mutableStateOf<TextNode?>(null) }
    var valueTextNode by remember { mutableStateOf<TextNode?>(null) }

    LaunchedEffect(cylinderColor) {
        optimalCylinderNode?.materialInstance = materialLoader.createColorInstance(cylinderColor)
        valueCylinderNode?.materialInstance = materialLoader.createColorInstance(cylinderColor)
        backgroundCylinderNode?.materialInstance = materialLoader.createColorInstance(backgroundCylinderColor)
    }

    LaunchedEffect(optimalCylinderNode, optimalMinTextNode, optimalMaxTextNode, safeOptHeight, optPositionY, textColor) {
        backgroundCylinderNode?.apply {
            scale = io.github.sceneview.math.Scale(x = 1f, y = safeBgHeight, z = 1f)
            position = Position(x = -0.35f, y = bgPositionY, z = distanceToCamera)
        }

        optimalCylinderNode?.apply {
            scale = io.github.sceneview.math.Scale(x = 1f, y = safeOptHeight, z = 1f)
            position = Position(x = -0.35f, y = optPositionY, z = distanceToCamera)
        }

        optimalMinTextNode?.apply {
            position = Position(x = -0.8f, y = -1f + (optMin * unitPerM), z = distanceToCamera)
            text = "%.2f".format(optMin)
            this.textColor = textColor
            isVisible = false
            isVisible = true
            onTransformChanged()
        }

        optimalMaxTextNode?.apply {
            position = Position(x = -0.35f, y = -1f + (optMin * unitPerM) + safeOptHeight + 0.1f, z = distanceToCamera)
            text = "%.2f".format(optMax)
            this.textColor = textColor
            isVisible = false
            isVisible = true
            onTransformChanged()
        }
    }

    LaunchedEffect(valueCylinderNode, valueTextNode, value, unitPerM) {
        val h = (value ?: 0.001f) * unitPerM

        valueCylinderNode?.apply {
            scale = io.github.sceneview.math.Scale(x = 1f, y = h, z = 1f)
            position = Position(x = 0.35f, y = -1f + (h / 2f), z = distanceToCamera)
        }

        valueTextNode?.apply {
            position = Position(x = 0.35f, y = -1f + h + 0.1f, z = distanceToCamera)
            text = "%.2f".format(value)
            this.textColor = textColor
            isVisible = false
            isVisible = true
            onTransformChanged()
        }
    }

    SceneView(
        modifier = modifier,
        engine = engine,
        modelLoader = modelLoader,
        materialLoader = materialLoader,
        cameraNode = cameraNode,
        cameraManipulator = rememberCameraManipulator(
            targetPosition = Position(x = 0f, 0f, z = distanceToCamera)
        ),
        environment = environment,
        renderer = renderer,
        surfaceType = SurfaceType.TextureSurface,
        view = view,
        isOpaque = false
    ) {
        // Optimal
        CylinderNode(
            radius = 0.3f,
            height = 1.0f,
            materialInstance = materialLoader.createColorInstance(backgroundCylinderColor),
            position = Position(x = -0.35f, y = -1f, z = distanceToCamera),
            apply = {
                backgroundCylinderNode = this
            }
        )

        CylinderNode(
            radius = 0.3f,
            height = 1.0f,
            materialInstance = materialLoader.createColorInstance(cylinderColor),
            position = Position(x = -0.35f, y = -1f, z = distanceToCamera),
            apply = {
                optimalCylinderNode = this
            }
        )
        TextNode(
            text = "",
            textColor = textColor,
            backgroundColor = android.graphics.Color.TRANSPARENT,
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),
            apply = {
                optimalMinTextNode = this
            }
        )
        TextNode(
            text = "",
            textColor = textColor,
            backgroundColor = android.graphics.Color.TRANSPARENT,
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),
            apply = {
                optimalMaxTextNode = this
            }
        )

        // Displayed
        CylinderNode(
            radius = 0.3f,
            height = 1.0f,
            materialInstance = materialLoader.createColorInstance(cylinderColor),
            position = Position(x = 0.35f, y = -1f, z = distanceToCamera),
            apply = {
                valueCylinderNode = this
            }
        )
        TextNode(
            text = "",
            textColor = textColor,
            backgroundColor = android.graphics.Color.TRANSPARENT,
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD),
            apply = {
                valueTextNode = this
            }
        )
    }
}