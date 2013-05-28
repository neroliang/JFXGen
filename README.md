JFXGen
======
Simple Examples of creating compounded UI elements based on the the new power of JAVAFX

WFBuilder - Main Entry and builder of application window framework
From the upper screen to the lower part of screen, there are four different parts.
WFTopMenu- sit on the topmost position of the screen, in charge of all different menu (some imported from outside)
WFDrawPad - below the TopMenu but, but displayed as the background scene
WFWidgetMap - the horizontal position is roughly the same with WFDrawPad, but displayed as the front scene
WFCentralPane - in charge of differnt widget table, and afflicated control panel(could be customerized or stylized)

Besides, there are LeftPane and bottomPane, but didn't used a dedicated class to implement them, instead adopting some built-in JAVAFX compoents.

