LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := MyLibrary
LOCAL_SRC_FILES := MyLibrary.cpp\
                   sqlite3.c\
                   decode.cpp\
                   enumerate.cpp\
                   geojson.cpp\
                   geometry.cpp\
                   main_tiopecanoe.cpp\
                   mbtiles.cpp\
                   memfile.cpp\
                   mvt.cpp\
                   pool.cpp\
                   projection.cpp\
                   serial.cpp\
                   tile.cpp\
                   tile-join.cpp\
                   clipper/clipper.cpp\
                   jsonpull/jsonpull.c\


#APP_STL := c++_shared
APP_CPPFLAGS += -fexceptions
APP_CPPFLAGS += -std=c++11

LOCAL_LDLIBS := -lz
LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)