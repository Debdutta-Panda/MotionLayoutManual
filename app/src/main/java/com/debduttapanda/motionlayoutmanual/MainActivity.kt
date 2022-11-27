package com.debduttapanda.motionlayoutmanual

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import com.debduttapanda.motionlayoutmanual.ui.theme.MotionLayoutManualTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotionLayoutManualTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SampleUI()
                }
            }
        }
    }
}

@SuppressLint("Range")
@OptIn(ExperimentalMotionApi::class)
@Composable
private fun SampleUI() {
    var progress by remember {
        mutableStateOf(0f)
    }
    Box {
        MotionLayout(
            start = ConstraintSet {
                val red = createRefFor("red")
                val blue = createRefFor("blue")
                val green = createRefFor("green")
                val hello = createRefFor("hello")
                val world = createRefFor("world")

                constrain(hello){
                    bottom.linkTo(parent.bottom)
                    visibility = Visibility.Visible
                }

                constrain(world){
                    start.linkTo(hello.end,4.dp)
                    baseline.linkTo(hello.baseline)//good for text
                    //bottom.linkTo(hello.bottom)//bad for text, good otherwise
                }

                val a = createRefFor("a")
                val b = createRefFor("b")
                val c = createRefFor("c")

                val ch = createHorizontalChain(a,b,c, chainStyle = ChainStyle.Spread)
                constrain(ch){
                    start.linkTo(blue.end)
                }

                ClockStartConstraint()



                constrain(a){
                    horizontalChainWeight = 1f
                    //width = Dimension.fillToConstraints
                    width = Dimension.value(20.dp)
                }

                constrain(b){
                    horizontalChainWeight = 2f
                    width = Dimension.fillToConstraints
                }

                constrain(c){
                    horizontalChainWeight = 1f
                    width = Dimension.fillToConstraints
                }

                val guide1 = createGuidelineFromStart(40.dp)
                val guide2 = createGuidelineFromTop(40.dp)
                val bar1 = createEndBarrier(red,blue)
                constrain(red){
                    top.linkTo(parent.top)
                    //start.linkTo(parent.start)
                    //centerTo(parent)
                    //centerHorizontallyTo(parent)
                    width = Dimension.value(100.dp)
                    height = Dimension.value(100.dp)
                    start.linkTo(guide1)
                    top.linkTo(guide2)
                }
                constrain(blue){
                    top.linkTo(parent.top,50.dp)
                    start.linkTo(parent.start,100.dp)
                }
                constrain(green){
                    top.linkTo(parent.top)
                    start.linkTo(bar1)
                }
            },
            end = ConstraintSet {
                val red = createRefFor("red")
                val blue = createRefFor("blue")
                val green = createRefFor("green")

                val hello = createRefFor("hello")

                ClockEndConstraint()

                constrain(hello){
                    bottom.linkTo(parent.bottom)
                    visibility = Visibility.Invisible
                }

                val a = createRefFor("a")
                val b = createRefFor("b")
                val c = createRefFor("c")

                createVerticalChain(a,b,c)

                val bar1 = createStartBarrier(red,blue)
                constrain(red){
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    rotationZ = 180f
                    pivotX = 0.5f
                    pivotY = 0.5f
                    //alpha = 0f
                    //scaleX = 0f
                    width = Dimension.value(50.dp)
                    height = Dimension.value(50.dp)
                }
                constrain(blue){
                    top.linkTo(parent.top,50.dp)
                    start.linkTo(parent.start,150.dp)
                }
                constrain(green){
                    top.linkTo(parent.top)
                    end.linkTo(bar1)
                }
            },
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .layoutId("red")
                    //.size(100.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .layoutId("blue")
                    .size(75.dp)
                    .background(Color.Blue)
            )
            Box(
                modifier = Modifier
                    .layoutId("green")
                    .size(50.dp)
                    .background(Color.Green)
            )

            Box(
                modifier = Modifier
                    .layoutId("a")
                    .height(50.dp)//no width
                    .background(Color.Cyan)
            )

            Box(
                modifier = Modifier
                    .layoutId("b")
                    .height(50.dp)//no width
                    .background(Color.Magenta)
            )

            Box(
                modifier = Modifier
                    .layoutId("c")
                    .height(50.dp)//no width
                    .background(Color.Yellow)
            )

            Text(
                "Hello",
                modifier = Modifier
                    .layoutId("hello"),
                color = Color.Red,
                fontSize = 48.sp
            )

            Text(
                "World",
                modifier = Modifier
                    .layoutId("world"),
                color = Color.Blue,
                fontSize = 24.sp
            )

            ClockUI()
        }
        Slider(
            value = progress,
            onValueChange = {
                progress = it
            }
        )
    }

}

fun ConstraintSetScope.ClockEndConstraint() {
    val center = createRefFor("center")
    val centerDot = createRefFor("centerDot")
    val mint = createRefFor("mint")
    val hour = createRefFor("hour")

    constrain(mint){
        centerHorizontallyTo(parent)
        bottom.linkTo(center.bottom)
        rotationZ = 360f
        pivotX = 0.5f
        pivotY = 1f
    }
    constrain(hour){
        centerHorizontallyTo(parent)
        bottom.linkTo(center.bottom)
        rotationZ = 30f
        pivotX = 0.5f
        pivotY = 1f
    }

    constrain(center){
        centerTo(parent)
    }

    constrain(centerDot){
        centerTo(parent)
    }

    for(i in 0..11){
        val ref = createRefFor("$i")
        constrain(ref){
            circular(
                other = center,
                angle = (i+1)*30f,
                distance = 60.dp
            )
        }
    }
}

fun ConstraintSetScope.ClockStartConstraint() {
    val center = createRefFor("center")
    val centerDot = createRefFor("centerDot")
    val mint = createRefFor("mint")
    val hour = createRefFor("hour")

    constrain(mint){
        centerHorizontallyTo(parent)
        bottom.linkTo(center.bottom)
        rotationZ = 0f
        pivotX = 0.5f
        pivotY = 1f
    }
    constrain(hour){
        centerHorizontallyTo(parent)
        bottom.linkTo(center.bottom)
        rotationZ = 0f
        pivotX = 0.5f
        pivotY = 1f
    }

    constrain(center){
        centerTo(parent)
    }

    constrain(centerDot){
        centerTo(parent)
    }

    for(i in 0..11){
        val ref = createRefFor("$i")
        constrain(ref){
            circular(
                other = center,
                angle = (i+1)*30f,
                distance = 60.dp
            )
        }
    }
}

@Composable
fun ClockUI() {
    Box(
        modifier = Modifier
            .layoutId("center")
            .size(0.dp)
    )

    Box(
        modifier = Modifier
            .layoutId("centerDot")
            .size(10.dp)
            .clip(CircleShape)
            .background(Color.Black)
    )
    for(i in 0..11){
        Text(
            "${i + 1}",
            modifier = Modifier
                .layoutId("$i")
        )
    }

    Box(
        modifier = Modifier
            .layoutId("hour")
            .width(7.dp)
            .height(30.dp)
            .background(Color.Blue)
    )

    Box(
        modifier = Modifier
            .layoutId("mint")
            .width(5.dp)
            .height(50.dp)
            .background(Color.Red)
    )
}
