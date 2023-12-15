#pragma once

#include<Windows.h>
#include<jni.h>

jobject genCoord(JNIEnv *, COORD const*);
jobject genSmallRect(JNIEnv *, SMALL_RECT const*);
jobject genConsoleScreenBufferInfo(JNIEnv *, CONSOLE_SCREEN_BUFFER_INFO const*);