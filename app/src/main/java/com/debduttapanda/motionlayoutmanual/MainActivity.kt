package com.debduttapanda.motionlayoutmanual

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
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
                    //SampleUI()
                    MotionLayout1()
                }
            }
        }
    }
}

@SuppressLint("Range")
@OptIn(ExperimentalMotionApi::class)
@Composable
private fun SampleUI() {
    Box {
        var progress by remember {
            mutableStateOf(0f)
        }
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

@OptIn(ExperimentalMotionApi::class)
@Composable
fun Morphing() {
    Box(){
        var progress by remember {
            mutableStateOf(0f)
        }

        MotionLayout(
            start = ConstraintSet {
                val background = createRefFor("background")
                val slider = createRefFor("slider")
                val ecommerce = createRefFor("ecommerce")
                val platform = createRefFor("platform")

                val buy = createRefFor("buy")
                val items = createRefFor("items")
                val buyIcon = createRefFor("buyIcon")
                val buyHolder = createRefFor("buyHolder")

                constrain(items){
                    centerHorizontallyTo(buy)
                    bottom.linkTo(buyIcon.top,8.dp)
                }
                constrain(buy){
                    start.linkTo(buyHolder.start,16.dp)
                    bottom.linkTo(items.top,8.dp)
                }
                constrain(buyHolder){
                    top.linkTo(buy.top,-12.dp)
                    bottom.linkTo(background.bottom, 16.dp)
                    start.linkTo(parent.start,18.dp)
                    end.linkTo(buy.end,-16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                }
                constrain(buyIcon){
                    centerHorizontallyTo(buy)
                    bottom.linkTo(buyHolder.bottom,12.dp)
                }

                val sell = createRefFor("sell")
                val products = createRefFor("products")
                val sellIcon = createRefFor("sellIcon")
                val sellHolder = createRefFor("sellHolder")

                constrain(products){
                    centerHorizontallyTo(sell)
                    bottom.linkTo(sellIcon.top,8.dp)
                }
                constrain(sell){
                    end.linkTo(sellHolder.end,16.dp)
                    bottom.linkTo(products.top,8.dp)
                }
                constrain(sellHolder){
                    top.linkTo(sell.top,-12.dp)
                    bottom.linkTo(background.bottom, 16.dp)
                    start.linkTo(sell.start,-18.dp)
                    end.linkTo(parent.end,16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                }
                constrain(sellIcon){
                    centerHorizontallyTo(sell)
                    bottom.linkTo(sellHolder.bottom,12.dp)
                }

                constrain(background){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.matchParent
                    height = Dimension.value(250.dp)
                }
                constrain(slider){
                    bottom.linkTo(parent.bottom)
                }
                constrain(ecommerce){
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top,32.dp)
                }
                constrain(platform){
                    centerHorizontallyTo(parent)
                    top.linkTo(ecommerce.bottom)
                }
            },
            end = ConstraintSet {
                val background = createRefFor("background")
                val slider = createRefFor("slider")
                val ecommerce = createRefFor("ecommerce")
                val platform = createRefFor("platform")

                val buy = createRefFor("buy")
                val items = createRefFor("items")
                val buyIcon = createRefFor("buyIcon")
                val buyHolder = createRefFor("buyHolder")

                constrain(items){
                    centerVerticallyTo(buyHolder)
                    start.linkTo(buy.end)
                }
                constrain(buy){
                    start.linkTo(buyHolder.start,16.dp)
                    bottom.linkTo(items.top,8.dp)
                    centerVerticallyTo(buyHolder)
                }
                constrain(buyHolder){
                    top.linkTo(parent.top,8.dp)
                    start.linkTo(parent.start,16.dp)
                    bottom.linkTo(buy.bottom,-8.dp)
                    end.linkTo(buyIcon.end,-12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                constrain(buyIcon){
                    start.linkTo(items.end,12.dp)
                    centerVerticallyTo(buyHolder)
                }

                val sell = createRefFor("sell")
                val products = createRefFor("products")
                val sellIcon = createRefFor("sellIcon")
                val sellHolder = createRefFor("sellHolder")

                constrain(products){
                    centerVerticallyTo(sellHolder)
                    end.linkTo(sellIcon.start)
                }
                constrain(sell){
                    end.linkTo(products.start,16.dp)
                    centerVerticallyTo(sellHolder)
                }
                constrain(sellHolder){
                    top.linkTo(sellIcon.top,-8.dp)
                    start.linkTo(sell.start,-16.dp)
                    bottom.linkTo(background.bottom,8.dp)
                    end.linkTo(parent.end,12.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                constrain(sellIcon){
                    end.linkTo(sellHolder.end,12.dp)
                    bottom.linkTo(sellHolder.bottom,8.dp)
                }

                createHorizontalChain(ecommerce,platform, chainStyle = ChainStyle.Packed)
                constrain(ecommerce){
                    centerVerticallyTo(background)
                }
                constrain(platform){
                    baseline.linkTo(ecommerce.baseline)
                }
                constrain(background){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    width = Dimension.matchParent
                    height = Dimension.value(180.dp)
                }
                constrain(slider){
                    bottom.linkTo(parent.bottom)
                }
            },
            progress = progress,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("background")
                    .background(Color(0xff0075FF))
            )
            Text(
                "E-Commerce",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (32-8*progress).sp,
                modifier = Modifier
                    .layoutId("ecommerce")
                    .padding(horizontal = 4.dp)
            )
            Text(
                "Platform",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (32-8*progress).sp,
                modifier = Modifier
                    .layoutId("platform")
                    .padding(horizontal = 4.dp)
            )

            Box(
                modifier = Modifier
                    .layoutId("buyHolder")
                    .coloredShadow(
                        color = Color.Gray,
                        borderRadius = 12.dp,
                        blurRadius = 4.dp,
                        offsetX = 4.dp,
                        offsetY = 4.dp
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            )

            Text(
                "Buy",
                color = Color(0xff0075FF),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .layoutId("buy")
                    .padding(4.dp)
            )

            Text(
                "Items",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier
                    .layoutId("items")
                    .padding(4.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.milk_svgrepo_com),
                contentDescription = "",
                modifier = Modifier
                    .layoutId("buyIcon")
                    .padding(4.dp)
                    .size(32.dp),
                tint = Color.Unspecified
            )

            Box(
                modifier = Modifier
                    .layoutId("sellHolder")
                    .coloredShadow(
                        color = Color.Gray,
                        borderRadius = 12.dp,
                        blurRadius = 4.dp,
                        offsetX = 4.dp,
                        offsetY = 4.dp
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            )

            Text(
                "Sell",
                color = Color(0xff0075FF),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .layoutId("sell")
                    .padding(4.dp)
            )

            Text(
                "Products",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier
                    .layoutId("products")
                    .padding(4.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.rich_svgrepo_com),
                contentDescription = "",
                modifier = Modifier
                    .layoutId("sellIcon")
                    .padding(4.dp)
                    .size(32.dp),
                tint = Color.Unspecified
            )

            Slider(
                modifier = Modifier
                    .layoutId("slider")
                    .padding(24.dp),
                value = progress,
                onValueChange = {
                    progress = it
                }
            )
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun VariablesGenerage() {
    var progress by remember {
        mutableStateOf(0f)
    }
    val startj = """
        {
                Variables: {
                  angle: { from: 0, step: 10 },
                  rotation: { from: 'startRotation', step: 10 },
                  distance: 100,
                  mylist: { tag: 'box' }
                },
                Generate: {
                  mylist: {
                    width: 200,
                    height: 40,
                    circular: ['parent', 'angle', 'distance'],
                    pivotX: 0.1,
                    pivotY: 0.1,
                    translationX: 225,
                    rotationZ: 'rotation'
                  }
                }
            }
    """.trimIndent()
    MotionLayout(
        start = ConstraintSet(startj, overrideVariables = "{ startRotation: 0 }"),
        end = ConstraintSet(startj, overrideVariables = "{ startRotation: 90 }"),
        progress = progress,
        modifier = Modifier.fillMaxSize()
    ) {
        for (i in 1..36) {
            Box(
                modifier = Modifier
                    .layoutId("id$i", "box")
                    .background(Color.Red)
            )
        }
    }
    Slider(
        modifier = Modifier
            .layoutId("slider")
            .padding(24.dp),
        value = progress,
        onValueChange = {
            progress = it
        }
    )
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    var progress by remember {
        mutableStateOf(0f)
    }
    MotionLayout(
        start = ConstraintSet("""
            {
              Helpers: [
                [
                  'hGuideline',{
                    id: "guide1",
                    percent: 0.5
                  }
                ],
                ["hChain",["ref1","ref2","ref3"],
                  {
                    style: ["spread_inside",1]                
                  }
                ]
              ],
              guide2: {
                type: 'hGuideline',
                percent: 0.25
              },
              barrier1: {
                type: 'barrier',
                direction: 'top',
                margin: 20,
                contains: ['ref1']
                },
              ref1: {
                bottom: ["guide2",'bottom','margin']
              },
              ref2: {
                top: "barrier1"
              }
            }            
        """.trimIndent()),
        end = ConstraintSet("""
            
        """.trimIndent()),
        progress = progress,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("ref1")
                .size(50.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("ref2")
                .size(50.dp)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .layoutId("ref3")
                .size(50.dp)
                .background(Color.Blue)
        )
    }
    Slider(
        modifier = Modifier
            .layoutId("slider")
            .padding(24.dp),
        value = progress,
        onValueChange = {
            progress = it
        }
    )
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

fun Modifier.coloredShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Float = 0f,
    modifier: Modifier = Modifier,
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.dp.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel =  (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                /*
                    The feature maskFilter used below to apply the blur effect only works
                    with hardware acceleration disabled.
                 */
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)