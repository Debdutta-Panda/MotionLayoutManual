# MotionLayoutManual

# Introduction
This is the documentation of Jetpack Composed MotionLayout.

# Motive

There are no comprehensive reference for MotionLayout. So, I crafting this manual.

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

# ConstraintSet

Description of the constraints used to layout the children.

# Progress

It is the value of how much we have moved towards the end state. Suppose in start state a ui component width is 0.dp and in end state the same is 100.dp. So, now if the progress is 0.75 then that ui component width will be `(0 + 0.75 x 100).dp`. The valid range of progress is 0 through 1.
