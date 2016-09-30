How to import for nubes:
 0) run ndk-build in the tippecanoe project (not nessesary)

 1) open your project and:
       -File -> New -> Import Module
       -Specify this tippcanoe project(Korean) as a sourse directory

 2)  fix gradle problems
      - go to settings.gradle -  modify first line:
       include ':tippcanoe',':nameOfYourMainModules' ...

      - enssure that in tippcanoe build.gradle first line - instead of: apply plugin: 'com.android.application'
        should be:   apply plugin: 'com.android.library'

      - add to the main module build.gradle as dependency next line:
        dependencies {
          ...
          compile project(':tippcanoe')
          ...
       }

      -remove in tippcanoe module build.gradle file next line:
                    applicationId "com.example.yuriy.korean"

      - sync gradle


  4) (this step is not necessary if step 0 has been done in order)  Android.mk is located in tippcanoe/src/main/jni :
    There are different ways to run ndk build:
      via creaing ndk-build  comand in external tools panel:
     -  creating ndk-build command:
         go to-> androidstudio ->preferenses;
          select: tools->external tools;
          press "+"
          fill the form:
         name = anyname (ndk-build-tippecanoe as example)
         discription = any discription
         Group: NDK   or any name
         program = path to ndk-buld
            example  = /Users/yuriy/Library/Android/sdk/ndk-bundle/ndk-build
         parameters - empty // to make clean command put parameter =  clean
         working directory = $ProjectFileDir$/tippcanoe/src/main
     - creating ndk clean command:
        do al previous step (creating ndk-build command:) but parameters has to be = clean

   5) to invoke ndk-build command
      - right click on any empty space and select NDK->ndk-build-tippecanoe

   6)how to use:

            - to generate in external dir file "any_name.mbtiles"

             in your main app type:
             TippeCanoeService tippeCanoeService = new TippeCanoeService();
             tippeCanoeService.generateMbtilesViaTippecanoe("any_name.mbtiles",context);

            by default it looks for "fields.json", "markers.json" files in external dir,
            you can set another names by passing them as params to TippeCanoeService constructor or
            via setters;


