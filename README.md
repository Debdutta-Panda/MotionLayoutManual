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

