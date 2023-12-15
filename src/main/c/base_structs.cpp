#include "base_structs.h"

jobject genCoord(JNIEnv * env, COORD const* coordinate) {
    jclass clazz = env->FindClass("edu/blaylock/jni/structs/Coord");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(SS)V");
    jobject jobj = env->NewObject(clazz, constructor, coordinate->X, coordinate->Y);
    return jobj;
}

jobject genSmallRect(JNIEnv * env, SMALL_RECT const* rect) {
    jclass clazz = env->FindClass("edu/blaylock/jni/structs/SmallRect");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(SSSS)V");
    jobject jobj = env->NewObject(clazz, constructor, rect->Left, rect->Top, rect->Left, rect->Bottom);
    return jobj;
}

jobject genConsoleScreenBufferInfo(JNIEnv * env, CONSOLE_SCREEN_BUFFER_INFO const* csbi) {
    jclass clazz = env->FindClass("edu/blaylock/jni/structs/ConsoleScreenBufferInfo");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "()V");

    jobject jobj = env->NewObject(clazz, constructor);
    jfieldID dwsize = env->GetFieldID(clazz, "dwsize", "Ledu/blaylock/jni/structs/Coord;");
    jfieldID dwCursorPosition = env->GetFieldID(clazz, "dwCursorPosition", "Ledu/blaylock/jni/structs/Coord;");
    jfieldID wAttributes = env->GetFieldID(clazz, "wAttributes", "S");
    jfieldID srWindow = env->GetFieldID(clazz, "srWindow", "Ledu/blaylock/jni/structs/SmallRect;");
    jfieldID dwMaximumWindowSize = env->GetFieldID(clazz, "dwMaximumWindowSize", "Ledu/blaylock/jni/structs/Coord;");

    env->SetObjectField(jobj, dwsize, genCoord(env, &(csbi->dwSize)));
    env->SetObjectField(jobj, dwCursorPosition, genCoord(env, &(csbi->dwCursorPosition)));
    env->SetShortField(jobj, wAttributes, (short) csbi->wAttributes);
    env->SetObjectField(jobj, srWindow, genSmallRect(env, &(csbi->srWindow)));
    env->SetObjectField(jobj, dwMaximumWindowSize, genCoord(env, &(csbi->dwMaximumWindowSize)));

    return jobj;
}