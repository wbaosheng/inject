LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := inject
LOCAL_SRC_FILES := \
				inject.c

LOCAL_LDLIBS := -llog

LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS  += -pie -fPIE -Wl,--as-needed

include $(BUILD_EXECUTABLE)