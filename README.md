# doophrame2

A second iteration (actually an entire rewrite) of doophrame - The gratest, and simplest android framework

## implementation

To use doophrame2 in your app add the following line to you `build.gradle` depenencies:

```
implementation "ca.doophie:doophrame-2:$current_version"
```

Make sure to define `current_version` or replace it with the most recent version

## usage

The following line should be included in `res/values/styles.xml` inside the base application theme in order to make use of the transitions:

```
        <item name="android:windowContentTransitions">true</item>
```

### Doophragment

In order to make use of the attach method, Doophragments must be used in place of Fragments. These act the exact same as regular android Fragments, with a few added things to perform transitions.

### TargetedTransition

This is used for transforming one object into another when changing between fragments. 

The three parameters required are:

1. fromFragment -> The fragment containing the view of the original object
2. fromObject   -> The view of the original object
3. toObjectId   -> The layout ID of the view that holds the object we transition too

Multiple `TargertedTransitions` can be passed in a list to the attach method of ViewGroup's in order to have multiple shared element transitions.

### ViewGroup.attach

The ViewGroup.attach method is used to place a fragment inside a ViewGroup.
Making this call will replace anything else inside the view group with the fragment passed in as the first parameter.
The attach call can be made in the following ways:

1. simply place a fragment in the ViewGroup:

```
    viewGroup.attach(SomeDoophragment())
```

2. place the fragment in the ViewGroup and add it to the back stack:

```
    viewGroup.attach(SomeDoophragment(), "SomeState")
```

3. place the fragment in the ViewGroup with certain shared element transitions.

```
    viewGroup.attach(SomeDoophragment(), listOf(
        TargetedTransition(fragmentWeAreComingFrom, objectWeTransitionFrom, idOfObjectWeTransitionToo)
    ), "SomeState") // the state is optional - only include if you want to add to backstack
```
