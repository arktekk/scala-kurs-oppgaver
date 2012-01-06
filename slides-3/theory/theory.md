!SLIDE
## Simple Build Tool

defining a build in terms of typesafe [expressions](https://github.com/harrah/xsbt/wiki/Settings)

not inheritance hierarchies or xml

!SLIDE
## Goal: a unified design - Settings!

    key in scope bind (dependencies) value

!SLIDE
## From a bird's eye
- Setting[T] produces typed values (simple values, tasks)

- Builds are collections of Settings

- Settings can (`<<=`) other settings' result types

- Think unix pipes but more flexible and for setting definitions

- More on Settings later &hellip;

!SLIDE
## Build it _your_ way

on build sizes and flavors

1. (small) shell
2. (medium) [Quick](https://github.com/harrah/xsbt/wiki/Basic-Configuration) Configuration
3. (large) [Full](https://github.com/harrah/xsbt/wiki/Full-Configuration) Configuration

!SLIDE
## In other words
1. `> eval expression # toe dipping`
2. `build.sbt # single project`
3. `build.scala # family of projects`

!SLIDE
# new friends

## eval (uate an expression)
## inspect (task doc and deps)
## show (run task and show output)
## last (say again?)

!SLIDE

# familiar friends

## ~ (watch and repeat)
## + (against cross version)
## ++ (against all cross versions)
## (compile, clean, test,...)

!SLIDE
## self aware

`session` is your passenger

    > help session
    > Manipulates session settings ...
    > session list
    > session save-all

!SLIDE
## [key](https://github.com/harrah/xsbt/blob/0.11.2/main/Keys.scala)-[value](https://github.com/harrah/xsbt/blob/0.11.2/main/Defaults.scala)

type `name` in the shell

    > name
    [info] helloworld

- this is looking up a value from `settings`<br>using the key `name`

!SLIDE
## key-value

type `publish-local` in the shell

    > publish-local
    [info] Packaging .../helloworld_2.9.1-sources.jar ...
    ...

!SLIDE
## key-value

type `publish-local` in the shell

    > publish-local
    [info] Packaging .../helloworld_2.9.1-sources.jar ...
    ...

- this is looking up a value from `settings`<br>using the key `publish-local`
- values can be executable functions (tasks)

!SLIDE
## key-value

`settings` is a Seq of `Setting[T]`

![k-v](theory/sbt0.10k-v.png)

!SLIDE
## keys can declare their independence
### (`:=`) context free

## or their dependencies on other keys (`<*`)
![deps](theory/sbt0.10k-v2.png)

!SLIDE
## An Emoji for just about every occasion

# ([:=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L177)), ([::=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L176)), ([:==](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L178)) ([+=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L132)), ([++=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L133)), ([<+=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L130)), ([<++=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L131)), ([<<=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L156)), ([~=](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L179)), ([??](https://github.com/harrah/xsbt/blob/0.10/main/Structure.scala#L162)) &hellip;

!SLIDE
### A quick rule of thumb regarding

### TasksKeys and SettingKeys with dependencies

!SLIDE
## SettingKeys _apply_

    val whichVersion = SettingKey[String]("which-version","...")
    override def settings = Seq(
      whichVersion <<= scalaVersion.apply(v =>
        "mah scala version is %s" format v
      )
    )

!SLIDE
## TaskKeys _map_

    val ohai = TaskKey[Unit]("ohai","prints ohai")
    override def settings = Seq(
       ohai <<= (streams, version) map { (out, vers) =>
         out.log.info("ohai %s!" format vers)
       }
    )
