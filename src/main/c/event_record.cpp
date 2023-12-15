#include "event_record.h"
#include "base_structs.h"

jobject genKeyEvent(JNIEnv * env, KEY_EVENT_RECORD* rec){
    jclass clazz =  env->FindClass("edu/blaylock/terminal/events/KeyEvent");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(ZIIICI)V");
    jobject jobj = env->NewObject(clazz, constructor, rec->bKeyDown, rec->wRepeatCount, rec->wVirtualKeyCode,
                                  rec->wVirtualScanCode, (jchar)(rec->uChar.UnicodeChar), rec->dwControlKeyState);
    return jobj;
}

jobject genMouseEvent(JNIEnv * env, MOUSE_EVENT_RECORD* rec){
    jclass clazz =  env->FindClass("edu/blaylock/terminal/events/MouseEvent");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "()V");
    jobject jobj = env->NewObject(clazz, constructor);
    return jobj;
}

jobject genWindowBufferSizeEvent(JNIEnv * env, WINDOW_BUFFER_SIZE_RECORD * rec){
    jclass clazz =  env->FindClass("edu/blaylock/terminal/events/WindowBufferSizeEvent");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(Ledu/blaylock/jni/structs/Coord;)V");
    jobject jobj = env->NewObject(clazz, constructor, genCoord(env, &rec->dwSize));
    return jobj;
}

jobject genMenuEvent(JNIEnv * env, MENU_EVENT_RECORD * rec){
    jclass clazz =  env->FindClass("edu/blaylock/terminal/events/MenuEvent");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(I)V");
    jobject jobj = env->NewObject(clazz, constructor, (jint) rec->dwCommandId);
    return jobj;
}

jobject genFocusEvent(JNIEnv * env, FOCUS_EVENT_RECORD * rec){
    jclass clazz =  env->FindClass("edu/blaylock/terminal/events/FocusEvent");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(Z)V");
    jobject jobj = env->NewObject(clazz, constructor, rec->bSetFocus);
    return jobj;
}

jobject genRecord(JNIEnv * env, INPUT_RECORD* record) {
    jobject event = nullptr;
    switch(record->EventType){
        case KEY_EVENT:
            event = genKeyEvent(env, &(record->Event.KeyEvent));
            break;
        case MOUSE_EVENT:
            event = genMouseEvent(env, &(record->Event.MouseEvent));
            break;
        case WINDOW_BUFFER_SIZE_EVENT:
            event = genWindowBufferSizeEvent(env, &(record->Event.WindowBufferSizeEvent));
            break;
        case MENU_EVENT:
            event = genMenuEvent(env, &(record->Event.MenuEvent));
            break;
        case FOCUS_EVENT:
            event = genFocusEvent(env, &(record->Event.FocusEvent));
            break;
        default:
            return nullptr;
    }

    jclass clazz =  env->FindClass("edu/blaylock/terminal/events/Record");
    jmethodID constructor = env->GetMethodID(clazz, "<init>", "(ILedu/blaylock/terminal/events/Event;)V");
    return env->NewObject(clazz, constructor, record->EventType, event);
}