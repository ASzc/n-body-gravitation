# n-Body Gravitation Simulation

## Overview

Performs a n-body simulation of gravitation, and renders the model with OpenGL via JOGL.

Motivated by the question "How hard could it be?" upon hearing that n-body gravitation is too computationaly expensive for Kerbal Space Program. The current, very naive, implementation performs well and seems to show that this idea is false.

## To do

*   Add support for predicting the path of an object if it were inserted into the model with a certain position and velocity. Probably would use a click-drag interface for position and velocity, then render a curve or points to show the path out to some time in the future.

## How to use

### Build & run

    mvn package
    java -jar target/*.jar

The jar is dependent on the JOGL native libraries, which are copied to target/lib/ at compile time.

### Controls

#### CLI options

*   --fps will cause FPS statistics to be printed to stdout every two seconds

#### Keyboard

*   Plus/Equals key: Increase the number of simulation steps per frame
*   Minus key: Decrease the number of simulation steps per frame
*   Shift key: When held, plus/minus will add/subtract 10 steps rather than 1 step
