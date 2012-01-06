!SLIDE

# quick config dsl
<br>
<br>
a simple way to describe single module projects as a list of expressions

!SLIDE

## where
<br>

in the root directory of your project
<br>
<br>

    // settings and tasks go here
    build.sbt
    
    // define the sbt version here
    project/build.properties
    
    // plugins go here
    project/build.sbt

!SLIDE

## build.sbt

start with these settings in `build.sbt`
<br>

    organization := "com.foo"

    name := "bar"

    version := "0.0.1"

    scalaVersion := "2.9.1"

!SLIDE

## build.properties

this is all you need in `project/build.properties`
<br>
<br>

    sbt.version=0.11.2

!SLIDE

## setting or task?

> if it can be cached, it's a setting

<br>

> if it depends on your project, it's a task

!SLIDE

## how do settings work?
<br>

[Settings](https://github.com/harrah/xsbt/wiki/Settings) are evaluated at project load time and on reload.
<br>
<br>

once the project is loaded, the dependencies are fixed.

!SLIDE

## how are tasks different from settings?
<br>

-  [Tasks](https://github.com/harrah/xsbt/wiki/Tasks) can be added, changed or removed at execution
-  execute on demand
-  use `map` or `flatMap` to pass values from one task to another
-  may have external dependencies
-  may have side effects

!SLIDE

# syntax

> everything is parsed as an expression


!SLIDE

## field guide

- [Keys](http://harrah.github.com/xsbt/latest/sxr/Keys.scala.html) describe available settings
- the [harrah/xsbt/wiki/Index](https://github.com/harrah/xsbt/wiki/Index) of practically just about everything

<br>
<br>

- operators ending in `=` intialize
- operators beginning with `<` depend on other values
- no semi-colons are allowed at the end of lines
- use curly braces to scope or pass a function as a setting value

!SLIDE

## initializing
<br>

- `<<=` is the most general initialization operator 
- it declares a dependency and passes the right-hand value or method result to 
the setting represented by the left-hand key
- every other initialization operator can be defined in terms of `<<=`

!SLIDE

## assign values
<br>

`:=` initializes a setting that is
<br>
<br>

- a constant value
- does not depend on any other settings
- will silently overwrite a previously defined value with the same key

!SLIDE

## initial setup
<br>
start with these settings in `build.sbt`
<br>

    organization := "org.myproject"

    name := "something"

    version := "0.1"

    scalaVersion := "2.9.1"

<br>
and specify sbt 0.11.2 in `build.properties`:
<br>

    sbt.version=0.11.2


!SLIDE

## adding maven repos
<br>

    resolvers += "name" at "url"
  
    resolvers ++= Seq("name2" at "url2", 
      "name3" at "url3")

!SLIDE

## adding dependencies
<br>
use `+=` to append one dependency, or `++=` to append a `Seq` of multiple dependencies.
<br>
<br> 

    libraryDependencies += "org.xyz" %% "xyz" % "2.1"

    libraryDependencies ++= Seq(
      "net.databinder" %% "dispatch-meetup" % "0.7.8",
      "net.databinder" %% "dispatch-twitter" % "0.7.8"
    )

    
!SLIDE

## dependencies are settings that update other settings
<br> 

    libraryDependencies += "org.xyz" %% "xyz" % "2.1"

<br>
<br>
is the same as using `<<=` to call apply to update the previous value of 
`libraryDependencies` with another `ModuleID`  
<br>
<br>

    libraryDependencies <<= libraryDependencies { deps =>
      deps :+ "org.xyz" %% "xyz" % "2.1"
    }

!SLIDE

## dependencies that use scala version
<br>

`scalaVersion` is itself a setting, so you need to combine an append operator with
the `<<=` operator:
<br>

-  `<+=` combines `+=` and `<<=` 
-  `<++=` combines `++=` and `<<=`

<br>

    libraryDependencies <+= scalaVersion( "org.scala-lang" 
      % "scala-compiler" % _ )
    
    libraryDependencies <++= scalaVersion { sv =>
      ("org.scala-lang" % "scala-compiler" % sv) ::
      ("org.scala-lang" % "scalap" % sv) ::
      Nil
    }    

!SLIDE

## unmanaged dependencies
<br>
use `unmanagedJars` to take care of unmanaged dependencies:
<br>
<br>

    unmanagedJars in Compile 
      += file("/home/rose/myproject/lib/some.jar")


!SLIDE

## compiler options
<br>
Java:
<br>

    javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

    javaOptions += "-Xms512M -Xmx2G"
    
<br>
<br>
Scala:
<br>

    scalacOptions ++= Seq("-deprecation", "-unchecked")


!SLIDE

## console-project
<br>
[Console-Project](https://github.com/harrah/xsbt/wiki/Console-Project) lets you use the DSL to 
interact with your project:
<br>
<br>

- see and modify configuration values
- evaluate tasks (use `currentState` - see [Build State](https://github.com/harrah/xsbt/wiki/Build-State)) for more
    - show the classpaths for compilation and testing
    - show available or remaining commands

!SLIDE

## testing - supported out of box

- specs2
- ScalaCheck
- ScalaTest

<br>
<br>

## needs a plugin

- JUnit

!SLIDE

## publish
<br>
By default, sbt will publish

- the main binary jar
- a source jar
- API documentation jar
- a Maven pom
<br>
<br>

<pre>
publishTo := Some("XYZ Repo" 
  at "http://repo.xyz.org/releases/")
</pre>

!SLIDE

## define credentials
<br>
using a file

    credentials += Credentials(Path.userHome / ".ivy2" / 
      ".credentials")
<br>
or inline 

    credentials += Credentials("Some Repo", "myproject.org", 
      "admin", "admin123")

!SLIDE

## publish snapshots and releases
<br>

    publishTo <<= (version) { version: String =>
      val nexus = "http://xyz.org/content/repositories/"
      if (version.trim.endsWith("SNAPSHOT")) 
        Some("snapshots" at nexus+"snapshots/") 
      else                                   
        Some("releases" at nexus+"releases/")
    }