# MotionLayoutManual

# Introduction
This is the documentation of Jetpack Compose MotionLayout.

# Motive

There are no comprehensive reference for MotionLayout. So, I am crafting this manual.

# MotionLayout
Motion layout is extended variation of constraint layout. It supports animation between 2 states. We can define 2 states and motion layout will animate the layout from starting state to ending state.

# ConstraintLayout

Constraint layout is a special type of layout which allows us to layout the ui components as per given constraints.

# Setup

Insert the following inside the dependency block of module-level `build.gradle`

```groovy
implementation "androidx.constraintlayout:constraintlayout-compose:1.0.1"
```

# Startup

```kotlin
MotionLayout(
  start = ConstraintSet{
    ...
  },
  end = ConstraintSet{
    ...
  },
  progress = 0f
){
  ...
}
```

There are various overloads of MotionLayout. But we will focus the following one:

```kotlin
fun MotionLayout(
    start: ConstraintSet,
    end: ConstraintSet,
    transition: androidx.constraintlayout.compose.Transition? = null,
    progress: Float,
    debug: EnumSet<MotionLayoutDebugFlags> = EnumSet.of(MotionLayoutDebugFlags.NONE),
    modifier: Modifier = Modifier,
    optimizationLevel: Int = Optimizer.OPTIMIZATION_STANDARD,
    crossinline content: @Composable MotionLayoutScope.() -> Unit
)
```

This overload is good to start as it is easy to learn after we have habituated with ConstraintLayout.

# Progress

It is the value of how much we have moved towards the end state. Suppose in start state a ui component width is 0.dp and in end state the same is 100.dp. So, now if the progress is 0.75 then that ui component width will be `(0 + 0.75 x 100).dp`. The valid range of progress is 0 through 1.

# ConstraintSet

Description of the constraints used to layout the children.

There are 2 ways of describing the constraint set.
1. JSON5 way
2. Programmatical way

# ConstraintSet programmatically

```kotlin
MotionLayout(
        start = ConstraintSet { 
            val box = createRefFor("box")
            
            constrain(box){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .size(100.dp)
                .background(Color.Red)
        )
    }
```

<img src="https://user-images.githubusercontent.com/92369023/204151051-a2da2ea7-ac9e-43be-bdd3-564b90107cec.png" alt="drawing" width="200"/>

This can be the simplest possible motion layout example.

# Constraint describing procedure

1. Set the layoutId for the composable
2. Retrieve the ConstrainedLayoutReference for the composable
3. Set the constraints for the constrained layout reference

# layoutId

We can set layoutId for any composable by using `Modifier.layoutId(<layout_id>)`

Example:

```kotlin
Box(
    modifier = Modifier
        .layoutId("my_box")
        ...
)
```

# ConstrainedLayoutReference

We can have the ConstrainedLayoutReference by the following way

```kotlin
val <variable_name> = createRefFor(<layout_id>)
```

# Constraints

We can set the constraints by the following way

```kotlin
constrain(<constrianedLayoutReference_variable>){
    //constraints
}
```

Again the following is a complete but minimal example of motion layout

```kotlin
MotionLayout(
    start = ConstraintSet { 
        val box = createRefFor("box")

        constrain(box){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
    },
    end = ConstraintSet {  },
    progress = 0f,
    modifier = Modifier.fillMaxSize()
) {
    Box(
        modifier = Modifier
            .layoutId("box")
            .size(100.dp)
            .background(Color.Red)
    )
}
```

As we can see creating the reference and mentioning the constraints are only possible inside a ConstraintSet{}

There are various constraints for a component:
* start
* end
* top
* bottom
* centering horizontally
* centering vertically
* centering in both direction
* centering around
* pivotX
* pivotY
* scaleX
* scaleY
* rotationX
* rotationY
* rotationZ
* translationX
* translationY
* translationZ
* visibility
* alpha
* circular
* width
* height
* baseline
* absoluteLeft
* absoluteRight
* horizontalChainWeight
* verticalChainWeight

We can group these constraints as following:

1. Placement constraints
2. Dimension constraints
3. Transformation constraints
4. Property constraints
5. Other constraints

# Placement constraints

* start
* end
* top
* bottom
* centering Horizontally
* centering vertically
* centering in both direction
* centering around
* circular
* baseline
* absoluteLeft
* absoluteRight

# Dimension constraints

* width
* height

# Transformation constraints

* pivotX
* pivotY
* scaleX
* scaleY
* rotationX
* rotationY
* rotationZ
* translationX
* translationY
* translationZ

# Property constraints

* visibility
* alpha

# Other constraints

* horizontalChainWeight
* verticalChainWeight

# Placement constraints

We can further divide the placement constraints as following:

## 1. linkTo pattern
  * start
  * end
  * top
  * bottom
  * absoluteLeftt
  * absoluteRight
  * baseline

### Syntax

```kotlin
constraint_name.linkTo(other_constrainedReference.allowed_constraint_name, margin, goneMargin)
```

### Allowed Constraints

#### Horizontal constraints
  * start
  * end
  * absoluteLeft
  * absoluteRight

Horizontal constraints allow only horizontal constraints

#### Vertical constraints
  * top
  * bottom
  * baseline

Vertical constraints allow only vertical constraints

## Constrained Reference

There are few types of constrained reference

1. Implicit `parent`
2. Explicit, *created by* `createRefFor`
3. Explicit, `guideline`s
4. Explicit, `barrier`s

## margin, goneMargin

margin and goneMargin is in `Dp`

### Working examplle

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val box = createRefFor("box")

            constrain(box){
                top.linkTo(parent.top, 40.dp)
                start.linkTo(parent.start, 20.dp)
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .size(100.dp)
                .background(Color.Red)
        )
    }
}
```

## 2. Centering constraints
  * centering Horizontally
  * centering vertically
  * centering in both direction
  * centering around

## 3. Circular arrangement constraint
  * circular

### Syntax

```kotlin
<centering_method>(other_constrainedReference)
```

### Centering methods

1. centerTo
2. centerHorizontalTo
3. centerVerticalTo
4. centerAround: There are 2 variation, one for horizontal another for vertical

### Working exaampe

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val box = createRefFor("box")
            val smallBox = createRefFor("smallBox")

            constrain(box){
                centerTo(parent)
            }
            constrain(smallBox){
                centerTo(box)
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .size(100.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("smallBox")
                .size(50.dp)
                .background(Color.Blue)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204345099-2e70f6a9-1466-4d92-8080-9bbeb7dcbb3d.png" alt="drawing" width="200"/>

## Circular

Used to place a component around another compoent at an  angle and distance

### Syntax

```kotlin
circular(other_constrainedReference, angle, distance)
```

### Working example

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val center = createRefFor("center")



            constrain(center){
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
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("center")
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
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204347471-10e8d309-6e44-4da9-9561-854d30f711a2.png" alt="drawing" width="200"/>

# Dimension constraints

1. width
2. height
## Syntax

```kotlin
<dimension_name> = Dimension value
```

## Dimension value

There are few ways to mention the dimension value

1. **value in Dp:** `Dimension.value(20.dp)`
2. **wrapContent:** `Dimension.wrapContent`
3. **fillToConstraintss:** `Dimension.fillToConstraints`
4. **preferredWrapContent:** `Dimension.preferredWrapContent`
5. **matchParent:** `Dimension.matchParent`
6. **percent:** `Dimension.percent(0.5f)`, 1f = 100%
7. **preferredValue:** `Dimension.preferredValue(200.dp)`
8. **ratio:** `Dimenstion.ratio("1:2)")`, width : height if used width = ..., height : width otherwise

### Working example

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val box = createRefFor("box")
            val smallBox = createRefFor("smallBox")

            constrain(box){
                centerTo(parent)
            }
            constrain(smallBox){
                centerTo(box)
                width = Dimension.value(50.dp)
                height = Dimension.ratio("1:1")
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .size(100.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("smallBox")
                .background(Color.Blue)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204431084-11fc75fa-987f-4d48-a72c-b849419e0410.png" alt="drawing" width="200"/>

## Transformation constraints

* pivotX
* pivotY
* scaleX
* scaleY
* rotationX
* rotationY
* rotationZ
* translationX
* translationY
* translationZ

### Syntax

```kotlin
<constraint_name> = float_value
```

### Working example

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val box = createRefFor("box")
            val smallBox = createRefFor("smallBox")

            constrain(box){
                centerTo(parent)
            }
            constrain(smallBox){
                centerTo(box)
                width = Dimension.value(100.dp)
                height = Dimension.ratio("1:1")
                scaleX = 0.5f
                rotationZ = 45f
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("box")
                .size(100.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("smallBox")
                .background(Color.Blue)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204432247-7b98a477-af6c-482a-a586-175ff2391b39.png" alt="drawing" width="200"/>

# Guide

Guides are invisible anchors. We can use to place other components.

## Create guide

```kotlin
val guide1 = createGuideFrom<side_name>(offset_in_dp or fraction)
```

## Use

```kotlin
start.linkTo(guide1)
top.linkTo(guide2)
```

## Sides

1. **Start:** Creates guideline from start with offset, vertical anchor, used for horizontal constraints
2. **AbsoluteLeft:** Create guideline from left with offset, vertical anchor, used for horizontal constraints
3. **End:** Create guideline from end with offset, vertical anchor, used for horizontal constraints
4. **AbsoluteRight:** Create guideline from right with offset, vertical anchor, used for horizontal constraints
5. **Top:** Create guideline from top with offset, horizontal anchor, used for vertical constraints
6. **Bottom:** Create guideline from bottom with offset, horizontal anchor, used for vertical constraints

## Working example

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val box = createRefFor("box")
            val smallBox = createRefFor("smallBox")

            val guide1 = createGuidelineFromStart(40.dp)
            val guide2 = createGuidelineFromTop(60.dp)

            constrain(box){
                centerTo(parent)
            }
            constrain(smallBox){
                start.linkTo(guide1)
                top.linkTo(guide2)
                width = Dimension.value(100.dp)
                height = Dimension.ratio("1:1")
                scaleX = 0.5f
                rotationZ = 45f
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("smallBox")
                .background(Color.Blue)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204434973-de654e91-d851-4656-bb63-260cfa5e419b.png" alt="drawing" width="200"/>

# Barrier

A Barrier references multiple constrained-references as input, and creates a virtual guideline based on the most extreme widget on the specified side. For example, a left barrier will align to the left of all the referenced items.

## Syntax

```kotlin
val <name> = create<side_name>Barrier(ref1, ref2, margin_in_dp)
```

## Sides

1. **Start:** Creates vertical anchor, used for horizontal constraint
2. **End:** Creates vertical anchor, used for horizontal constraint
3. **Top:** Creates horizontal anchor, used for vertical constraint
4. **Bottom:** Creates horizontal anchor, used for vertical constraint
5. **AbsoluteLeft:** Creates vertical anchor, used for horizontal constraint
6. **AbsoluteRight:** Creates vertical anchor, used for horizontal constraint

## Working example

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val ref1 = createRefFor("ref1")
            val ref2 = createRefFor("ref2")
            val ref3 = createRefFor("ref3")

            val barrier1 = createStartBarrier(ref1, ref2)

            constrain(ref1){
                centerTo(parent)
            }
            constrain(ref2){
                centerTo(parent)
            }
            constrain(ref3){
                start.linkTo(barrier1)
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("ref2")
                .size(100.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("ref1")
                .size(50.dp)
                .background(Color.Blue)
        )
        Box(
            modifier = Modifier
                .layoutId("ref3")
                .size(20.dp)
                .background(Color.Green)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204437809-9f5c5493-1070-4bac-82e6-2c84b22ffe7d.png" alt="drawing" width="200"/>

# Chain

A chain is a group of views that are linked to each other with bi-directional position constraints. The views within a chain can be distributed either vertically or horizontally.

Chains can be horizontal or vertical.

Chains can be styled in one of the following ways:

**1) Spread:** The views are evenly distributed (after margins are accounted for). This is the default.

**2) Spread inside:** The first and last view are affixed to the constraints on each end of the chain and the rest are evenly distributed.

**3) Weighted:** When the chain is set to either spread or spread inside, you can fill the remaining space by setting one or more views to "match constraints" (0dp). By default, the space is evenly distributed between each view that's set to "match constraints," but you can assign a weight of importance to each view using the weight attributes. If you're familiar with layout_weight in a linear layout, this works the same way. So the view with the highest weight value gets the most amount of space; views that have the same weight get the same amount of space.

**4) Packed:** The views are packed together (after margins are accounted for). You can then adjust the whole chain's bias (left/right or up/down) by changing the chain's head view bias.

![image](https://user-images.githubusercontent.com/92369023/204440329-8e55a246-20b2-4b82-a072-34d5be5bb299.png)

![image](https://user-images.githubusercontent.com/92369023/204440354-015273e6-5f52-4a10-906c-af9fff691d0e.png)

## Types

There are two type of chain

1. Horizontal
2. Vertical

## Syntax

```kotlin
val <name> = create<Horizontal or Vertical>Chain(ref1, ref2, ..., chainStyle = <Chain Style>)
```

## Chain style

There are 3 types of chain

1. Spread
2. Packed
3. SpreadInside

## Working example

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val ref1 = createRefFor("ref1")
            val ref2 = createRefFor("ref2")
            val ref3 = createRefFor("ref3")

            val chain = createHorizontalChain(ref1,ref2,ref3, chainStyle = ChainStyle.Spread)

            constrain(ref1){
                top.linkTo(parent.top, 60.dp)
            }

            constrain(chain){
                start.linkTo(parent.start,75.dp)
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("ref1")
                .size(50.dp)
                .background(Color.Blue)
        )
        Box(
            modifier = Modifier
                .layoutId("ref2")
                .size(50.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("ref3")
                .size(50.dp)
                .background(Color.Green)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204525879-e6d9aa2b-52b1-4b76-b42f-9d1eadff4678.png" alt="drawing" width="200"/>

```kotlin
@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayout1() {
    MotionLayout(
        start = ConstraintSet {
            val ref1 = createRefFor("ref1")
            val ref2 = createRefFor("ref2")
            val ref3 = createRefFor("ref3")

            val chain = createHorizontalChain(ref1,ref2,ref3, chainStyle = ChainStyle.Spread)

            constrain(ref1){
                top.linkTo(parent.top, 20.dp)
                width = Dimension.fillToConstraints
                horizontalChainWeight = 1f
            }
            constrain(ref2){
                top.linkTo(parent.top, 30.dp)
                width = Dimension.fillToConstraints
                horizontalChainWeight = 2f
            }
            constrain(ref3){
                top.linkTo(parent.top, 40.dp)
                width = Dimension.fillToConstraints
                horizontalChainWeight = 3f
            }

            constrain(chain){
                start.linkTo(parent.start,75.dp)
            }
        },
        end = ConstraintSet {  },
        progress = 0f,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .layoutId("ref1")
                .height(50.dp)
                .background(Color.Blue)
        )
        Box(
            modifier = Modifier
                .layoutId("ref2")
                .height(50.dp)
                .background(Color.Red)
        )
        Box(
            modifier = Modifier
                .layoutId("ref3")
                .height(50.dp)
                .background(Color.Green)
        )
    }
}
```

<img src="https://user-images.githubusercontent.com/92369023/204527165-af55753e-bb79-4f79-ae4a-e3f7de5fe099.png" alt="drawing" width="200"/>

# API Reference

## ConstraintSetScope APIs

## createRefFor

```kotlin
fun createRefFor(id: Any): ConstrainedLayoutReference
```
Creates one `ConstrainedLayoutReference` corresponding to the `ConstraintLayout` element with `id`.

## constrain

```kotlin
fun constrain(
    ref: ConstrainedLayoutReference,
    constrainBlock: ConstrainScope.() -> Unit
): ConstrainScope
```

Specifies the constraints associated to the layout identified with `ref`

```kotlin
fun constrain(
    ref: HorizontalChainReference,
    constrainBlock: HorizontalChainScope.() -> Unit
): HorizontalChainScope
```

Specifies additional constraints associated to the horizontal chain identified with `ref`.

```kotlin
fun constrain(
    ref: VerticalChainReference,
    constrainBlock: VerticalChainScope.() -> Unit
): VerticalChainScope
```

Specifies additional constraints associated to the vertical chain identified with `ref`.

## createGuidelineFromStart

```kotlin
fun createGuidelineFromStart(offset: Dp): VerticalAnchor
```
Creates a guideline at a specific offset from the start of the `ConstraintLayout`.
```kotlin
fun createGuidelineFromStart(fraction: Float): VerticalAnchor
```

Creates a guideline at a specific offset from the start of the `ConstraintLayout`. A `fraction` of `0f` will correspond to the start of the `ConstraintLayout`, while `1f` will correspond to the end.

## createGuidelineFromEnd

```kotlin
fun createGuidelineFromEnd(offset: Dp): VerticalAnchor
```

Creates a guideline at a specific offset from the end of the `ConstraintLayout`.

```kotlin
fun createGuidelineFromEnd(fraction: Float): VerticalAnchor
```

Creates a guideline at a width fraction from the end of the `ConstraintLayout`. A `fraction` of 0f will correspond to the end of the `ConstraintLayout`, while 1f will correspond to the start.

## createGuidelineFromAbsoluteLeft

```kotlin
fun createGuidelineFromAbsoluteLeft(offset: Dp): VerticalAnchor
```

Creates a guideline at a specific offset from the left of the `ConstraintLayout`.

```kotlin
fun createGuidelineFromAbsoluteLeft(fraction: Float): VerticalAnchor
```

Creates a guideline at a width fraction from the left of the `ConstraintLayout`. A `fraction` of `0f` will correspond to the left of the `ConstraintLayout`, while `1f` will correspond to the right.

## createGuidelineFromAbsoluteRight

```kotlin
fun createGuidelineFromAbsoluteRight(offset: Dp): VerticalAnchor
```

Creates a guideline at a specific offset from the right of the `ConstraintLayout`.

```kotlin
fun createGuidelineFromAbsoluteRight(fraction: Float): VerticalAnchor
```

Creates a guideline at a width fraction from the right of the `ConstraintLayout`. A `fraction` of `0f` will correspond to the right of the `ConstraintLayout`, while `1f` will correspond to the left.

## createGuidelineFromTop

```kotlin
fun createGuidelineFromTop(offset: Dp): HorizontalAnchor
```
Creates a guideline at a specific offset from the top of the `ConstraintLayout`.
```kotlin
fun createGuidelineFromTop(fraction: Float): HorizontalAnchor
```
Creates a guideline at a height percenide from the top of the `ConstraintLayout`. A `fraction` of `0f` will correspond to the top of the `ConstraintLayout`, while `1f` will correspond to the bottom.

## createGuidelineFromBottom

```kotlin
fun createGuidelineFromBottom(offset: Dp): HorizontalAnchor
```
Creates a guideline at a specific offset from the bottom of the `ConstraintLayout`.

```kotlin
fun createGuidelineFromBottom(fraction: Float): HorizontalAnchor
```
Creates a guideline at a height percenide from the bottom of the `ConstraintLayout`. A `fraction` of `0f` will correspond to the bottom of the `ConstraintLayout`, while `1f` will correspond to the top.

## createStartBarrier

```kotlin
fun createStartBarrier(
    vararg elements: ConstrainedLayoutReference,
    margin: Dp = 0.dp
): VerticalAnchor
```
Creates and returns a start barrier, containing the specified elements.

## createEndBarrier

```kotlin
fun createEndBarrier(
    vararg elements: ConstrainedLayoutReference,
    margin: Dp = 0.dp
): VerticalAnchor
```
Creates and returns an end barrier, containing the specified elements.

## crreateAbsoluteLeftBarrier

```kotlin
fun createAbsoluteLeftBarrier(
        vararg elements: ConstrainedLayoutReference,
        margin: Dp = 0.dp
    ): VerticalAnchor
```
Creates and returns a left barrier, containing the specified elements.

## createAbsoluteRightBarrier

```kotlin
fun createAbsoluteRightBarrier(
    vararg elements: ConstrainedLayoutReference,
    margin: Dp = 0.dp
): VerticalAnchor
```
Creates and returns a right barrier, containing the specified elements.

## createTopBarrier

```kotlin
fun createTopBarrier(
        vararg elements: ConstrainedLayoutReference,
        margin: Dp = 0.dp
    ): HorizontalAnchor
```

Creates and returns a top barrier, containing the specified elements.

## createBottomBarrier

```kotlin
fun createBottomBarrier(
    vararg elements: ConstrainedLayoutReference,
    margin: Dp = 0.dp
): HorizontalAnchor
```
Creates and returns a bottom barrier, containing the specified elements.

## createHorizontalChain

```kotlin
fun createHorizontalChain(
    vararg elements: ConstrainedLayoutReference,
    chainStyle: ChainStyle = ChainStyle.Spread
): HorizontalChainReference
```

Creates a horizontal chain including the referenced layouts. Use [constrain] with the resulting [HorizontalChainReference] to modify the start/left and end/right constraints of this chain.

## createVerticalChain

```kotlin
fun createVerticalChain(
    vararg elements: ConstrainedLayoutReference,
    chainStyle: ChainStyle = ChainStyle.Spread
): VerticalChainReference
```

Creates a vertical chain including the referenced layouts. Use [constrain] with the resulting [VerticalChainReference] to modify the top and bottom constraints of this chain.

# ConstrainedLayoutReference APIs

## parent
```kotlin
val parent: ConstrainedLayoutReference
```
Reference to the `ConstraintLayout` itself, which can be used to specify constraints between itself and its children.

## start
```kotlin
val start: VerticalAnchorable
```
The start anchor of the layout - can be constrained using `VerticalAnchorable.linkTo`.

## absoluteLeft
```kotlin
val absoluteLeft: VerticalAnchorable
```
The left anchor of the layout - can be constrained using `VerticalAnchorable.linkTo`.

## top
```kotlin
val top: HorizontalAnchorable
```
The top anchor of the layout - can be constrained using `HorizontalAnchorable.linkTo`.

## end
```kotlin
val end: VerticalAnchorable
```
The end anchor of the layout - can be constrained using `VerticalAnchorable.linkTo`.

## absoluteRight
```kotlin
val absoluteRight: VerticalAnchorable
```
The right anchor of the layout - can be constrained using `VerticalAnchorable.linkTo`.

## bottom
```kotlin
val bottom: HorizontalAnchorable
```
The bottom anchor of the layout - can be constrained using `HorizontalAnchorable.linkTo`.

## baseline
```kotlin
val baseline: BaselineAnchorable
```
The `FirstBaseline` of the layout - can be constrained using `BaselineAnchorable.linkTo`.

## width
```kotlin
var width: Dimension
```
The width of the `ConstraintLayout` child.

## height
```kotlin
var height: Dimension
```
The height of the `ConstraintLayout` child.

## visibility
```kotlin
var visibility: Visibility
```
The overall visibility of the `ConstraintLayout` child. `Visibility.Visible` by default.

## alpha
```kotlin
var alpha: Float
```
The transparency value when rendering the content. Valid range is 0 to 1.

## scaleX
```kotlin
var scaleX: Float
```
The percent scaling value on the horizontal axis. Where 1 is 100%.

## scaleY
```kotlin
var scaleY: Float
```
The percent scaling value on the vertical axis. Where 1 is 100%.

## rotationX
```kotlin
var rotationX: Float
```
The degrees to rotate the content over the horizontal axis.

## rotationY
```kotlin
var rotationY: Float
```
The degrees to rotate the content over the vertical axis.

## rotationZ
```kotlin
var rotationZ: Float
```
The degrees to rotate the content on the screen plane.

## translationX
```kotlin
var translationX: Dp
```
The distance to offset the content over the X axis.

## translationY
```kotlin
var translationY: Dp
```
The distance to offset the content over the Y axis.

## translationZ
```kotlin
var translationZ: Dp
```
The distance to offset the content over the Z axis.

## pivotX
```kotlin
var pivotX: Float
```
The X axis offset percent where the content is rotated and scaled.

## pivotY
```kotlin
var pivotY: Float
```
The Y axis offset percent where the content is rotated and scaled.

## horizontalChainWeight
```kotlin
var horizontalChainWeight: Float
```
Whenever the width is not fixed, this weight may be used by an horizontal Chain to decide how much space assign to this widget.
## verticalChainWeight
```kotlin
var verticalChainWeight: Float
```
Whenever the height is not fixed, this weight may be used by a vertical Chain to decide how much space assign to this widget.

## linkTo
```kotlin
fun linkTo(
    start: ConstraintLayoutBaseScope.VerticalAnchor,
    end: ConstraintLayoutBaseScope.VerticalAnchor,
    startMargin: Dp = 0.dp,
    endMargin: Dp = 0.dp,
    startGoneMargin: Dp = 0.dp,
    endGoneMargin: Dp = 0.dp,
    @FloatRange(from = 0.0, to = 1.0) bias: Float = 0.5f
)
```
Adds both start and end links towards other `ConstraintLayoutBaseScope.VerticalAnchor`s.

```kotlin
fun linkTo(
    top: ConstraintLayoutBaseScope.HorizontalAnchor,
    bottom: ConstraintLayoutBaseScope.HorizontalAnchor,
    topMargin: Dp = 0.dp,
    bottomMargin: Dp = 0.dp,
    topGoneMargin: Dp = 0.dp,
    bottomGoneMargin: Dp = 0.dp,
    @FloatRange(from = 0.0, to = 1.0) bias: Float = 0.5f
)
```
Adds both top and bottom links towards other `ConstraintLayoutBaseScope.HorizontalAnchor`s.

```kotlin
fun linkTo(
    start: ConstraintLayoutBaseScope.VerticalAnchor,
    top: ConstraintLayoutBaseScope.HorizontalAnchor,
    end: ConstraintLayoutBaseScope.VerticalAnchor,
    bottom: ConstraintLayoutBaseScope.HorizontalAnchor,
    startMargin: Dp = 0.dp,
    topMargin: Dp = 0.dp,
    endMargin: Dp = 0.dp,
    bottomMargin: Dp = 0.dp,
    startGoneMargin: Dp = 0.dp,
    topGoneMargin: Dp = 0.dp,
    endGoneMargin: Dp = 0.dp,
    bottomGoneMargin: Dp = 0.dp,
    @FloatRange(from = 0.0, to = 1.0) horizontalBias: Float = 0.5f,
    @FloatRange(from = 0.0, to = 1.0) verticalBias: Float = 0.5f
)
```
Adds all start, top, end, bottom links towards other `ConstraintLayoutBaseScope.HorizontalAnchor`s.

## centerTo
```kotlin
fun centerTo(other: ConstrainedLayoutReference)
```
Adds all start, top, end, bottom links towards the corresponding anchors of `other`. This will center the current layout inside or around (depending on size) `other`.

## centerHorizontalyTo
```kotlin
fun centerHorizontallyTo(
    other: ConstrainedLayoutReference,
    @FloatRange(from = 0.0, to = 1.0) bias: Float = 0.5f
)
```
Adds start and end links towards the corresponding anchors of `other`. This will center horizontally the current layout inside or around (depending on size) `other`.

## centerVerticallyTo
```kotlin
fun centerVerticallyTo(
    other: ConstrainedLayoutReference,
    @FloatRange(from = 0.0, to = 1.0) bias: Float = 0.5f
)
```
Adds top and bottom links towards the corresponding anchors of `other`. This will center vertically the current layout inside or around (depending on size) `other`.

## centerAround
```kotlin
fun centerAround(anchor: ConstraintLayoutBaseScope.VerticalAnchor)
```
Adds start and end links towards a vertical `anchor`. This will center the current layout around the vertical `anchor`.

```kotlin
fun centerAround(anchor: ConstraintLayoutBaseScope.HorizontalAnchor)
```
Adds top and bottom links towards a horizontal `anchor`. This will center the current layout around the horizontal `anchor`.

## circular
```kotlin
fun circular(other: ConstrainedLayoutReference, angle: Float, distance: Dp)
```
Set a circular constraint relative to the center of `other`. This will position the current widget at a relative angle and distance from `other`.

## clearHorizontal
```kotlin
fun clearHorizontal()
```
Clear the constraints on the horizontal axis (left, right, start, end). Useful when extending another `ConstraintSet` with unwanted constraints on this axis.

## clearVertical
```kotlin
fun clearVertical()
```
Clear the constraints on the vertical axis (top, bottom, baseline). Useful when extending another `ConstraintSet` with unwanted constraints on this axis.

## clearConstraints
```kotlin
fun clearConstraints()
```
Clear all constraints (vertical, horizontal, circular). Useful when extending another `ConstraintSet` with unwanted constraints applied.

## resetDimensions
```kotlin
fun resetDimensions()
```
Resets the `width` and `height` to their default values. Useful when extending another `ConstraintSet` with unwanted dimensions.

## resetTransforms
```kotlin
fun resetTransforms()
```
Reset all render-time transforms of the content to their default values. Does not modify the `visibility` property.
Useful when extending another `ConstraintSet` with unwanted transforms applied.

# HorizontalChainReference APIs

## start
```kotlin
val start: ConstraintLayoutBaseScope.VerticalAnchor
```
The start anchor of the first element in the chain. Represents left in LTR layout direction, or right in RTL.

## absoluteLeft
```kotlin
val absoluteLeft: ConstraintLayoutBaseScope.VerticalAnchor
```
The left anchor of the first element in the chain.

## end
```kotlin
val end: ConstraintLayoutBaseScope.VerticalAnchor
```
The end anchor of the last element in the chain. Represents right in LTR layout direction, or left in RTL.

## absoluteRight
```kotlin
val absoluteRight: ConstraintLayoutBaseScope.VerticalAnchor
```
The right anchor of the last element in the chain.

# VerticalChainReference APIs

## top
```kotlin
val top: ConstraintLayoutBaseScope.HorizontalAnchor
```
The top anchor of the first element in the chain.

## bottom
```kotlin
val bottom: ConstraintLayoutBaseScope.HorizontalAnchor
```
The bottom anchor of the last element in the chain.

# HorizontalChainScope APIs

## parent
```kotlin
val parent: ConstrainedLayoutReference
```
Reference to the `ConstraintLayout` itself, which can be used to specify constraints between itself and its children.

## start
```kotlin
val start: VerticalAnchorable
```
The start anchor of the chain - can be constrained using `VerticalAnchorable.linkTo`.

## absoluteLeft
```kotlin
val absoluteLeft: VerticalAnchorable
```
The left anchor of the chain - can be constrained using `VerticalAnchorable.linkTo`.

## end
```kotlin
val end: VerticalAnchorable
```
The end anchor of the chain - can be constrained using `VerticalAnchorable.linkTo`.

## absoluteRight
```kotlin
val absoluteRight: VerticalAnchorable
```
The right anchor of the chain - can be constrained using `VerticalAnchorable.linkTo`.
