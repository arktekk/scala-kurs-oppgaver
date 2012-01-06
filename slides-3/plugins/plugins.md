!SLIDE
# plugins
> a way to use external code in a build definition

- custom tasks
- custom commands[*][1]
- custom settings

  [1]: http://groups.google.com/group/simple-build-tool/browse_frm/thread/7344d891f0a941a5/edfae75e124d255e
  
!SLIDE

- [mpeltonen/sbt-idea](https://github.com/mpeltonen/sbt-idea)
- [typesafehub/sbteclipse](https://github.com/typesafehub/sbteclipse)
- [siasia/xsbt-web-plugin](https://github.com/siasia/xsbt-web-plugin)
- [eed3si9n/sbt-appengine](https://github.com/eed3si9n/sbt-appengine)
- [sbt/sbt-assembly](https://github.com/sbt/sbt-assembly)
- [softprops/coffeescripted-sbt](https://github.com/softprops/coffeescripted-sbt)
- [n8han/posterous-sbt](https://github.com/n8han/posterous-sbt)
- [steppenwells/sbt-sh](https://github.com/steppenwells/sbt-sh)
- [and many more](https://github.com/harrah/xsbt/wiki/sbt-0.10-plugins-list)

!SLIDE
## using plugins

<br>
in project/build.sbt

    addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.0.0-M3")

	libraryDependencies += "org.clapper" %% "grizzled-scala" % "1.0.9"

!SLIDE

## plugins in your project
<br>
at the top of `build.sbt`, add the imports:

    import grizzled.sys._
    import OperatingSystem._
    
    // now do stuff with the plugin...
    libraryDependencies ++=
	    if (os ==Windows)
		    ("org.example" % "windows-only" 
		        % "1.0") :: Nil
	    else
		    Nil
      
!SLIDE

## bring plugin settings into scope
<br>
at the top of `build.sbt`, import the plugin settings:
<br>
<br>

    seq(SomePlugin.someSettings :_*)
    
    someSetting := "foo"

!SLIDE
## using plugins
![plugins k-v](plugins/sbt0.10plugins.png)