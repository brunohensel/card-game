<h1>War of Suits</h1>

<h2>1. Project structure</h2>
I chose the modularize approach in order to achieve more encapsulation, separation of concerns,and depending on the size of the project, 
it could lead to a more faster building time.

- App: is the module responsible to generate dagger application component, it is also responsible for housing the main activity, 
the first access point to the app.

- Core: this module has all things that are common between the other modules, such as, base classes, drawables, and utility functions.

- Test: has some utilities functions to facilitate the testing of the flows, and encapsultes the coroutine rules.

- War of Suits: it is only for a better organization of the project, within it there are modules that we are more interested in.
  - :Public holds the public api and some models. This module will allow us to apply the  dependency inversion principle, keeping 
    the abstraction completely separate from its implementation.
  - :Impl is responsible for the implementation of the WarOfSuits interface. Here is where the magic happens :)
  - :Presentation converts the flows that are emitted in the :Impl module into state of its interest.
  
In a simplified way, below is the dependency graph between the modules:
  
![Bildschirmfoto 2021-06-04 um 05 44 24](https://user-images.githubusercontent.com/45492687/120742816-f1586380-c4f7-11eb-945d-21e339939039.png)

Modularization allows us to work on features in isolation - public api, implementation or ui, without touching other modules, and can avoid building a large chunk, 
providing therefore faster builds.

Some talks that helped me out: https://www.droidcon.com/media-detail?video=380843878 ,  https://www.youtube.com/watch?v=TWLkswxjSr0&t=916s
  
<h2>2. Stacks</h2>
- Project written 100% in kotlin

- I followed a MVVM pattern with state managment, where I tried to keep a unidiractional flow between the layers.

- As a rective paradigm kotlin flow was used, where the flows are converted into a StateFlow that helps us by not emiting the same state twice in a row.
For single shot events, a channel was used.

- Tests: For testing the units I made a lot of use of interfaces to be able to build fake implementations where it made sense, in order to test state and 
not just behavior.






