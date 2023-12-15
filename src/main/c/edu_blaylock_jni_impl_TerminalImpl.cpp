#include <Windows.h>
#include <fcntl.h>
#include <io.h>
#include <jni.h>
#include "edu_blaylock_jni_impl_TerminalImpl.h"
#include "event_record.h"
#include "base_structs.h"

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    getConsoleScreenBufferInfo
 * Signature: (J)Ljni/structs/ConsoleScreenBufferInfo;
 */
JNIEXPORT jobject JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getConsoleScreenBufferInfo
        (JNIEnv * env, jobject jobj, jlong handle) {
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo((HANDLE) handle, &csbi);
    return genConsoleScreenBufferInfo(env, &csbi);
}

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    setConsoleTextAttribute
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_setConsoleTextAttribute
        (JNIEnv * env, jobject jobj, jlong handle, jint flags) {
    return SetConsoleTextAttribute((HANDLE) handle, flags);
}

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    write
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_write
        (JNIEnv * env, jobject jobj, jlong handle, jstring message) {
    const jchar* mess = env->GetStringChars(message, NULL);
    const jsize length = env->GetStringLength(message);

    DWORD numWritten = 0;
    WriteConsoleW((HANDLE) handle, (LPVOID) mess, length, &numWritten, NULL);
    env->ReleaseStringChars(message, mess);

    return (jint) numWritten;
}

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    read
 * Signature: ([CI)Z
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_read
        (JNIEnv * env, jobject jobj, jbyteArray jarray, jint i) {
    unsigned long read = 0;
    jbyte* buffer = env->GetByteArrayElements(jarray, nullptr);
    ReadFile(GetStdHandle(STD_INPUT_HANDLE), buffer, i, &read, NULL);
    env->SetByteArrayRegion(jarray, 0, (jsize) read, buffer);
    env->ReleaseByteArrayElements(jarray, buffer, 0);
    return (jint) read;
}

JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_readInputEvents
        (JNIEnv * env, jobject jobj, jobjectArray recordArray, jint numRead) {
    INPUT_RECORD* records = new INPUT_RECORD[numRead];
    HANDLE sin = GetStdHandle(STD_INPUT_HANDLE);
    DWORD num_read = 0;
    ReadConsoleInput(sin, records, numRead, &num_read);
    for(int i = 0 ; i < num_read; i++) {
        env->SetObjectArrayElement(recordArray, i, genRecord(env, records+i));
    }
    delete[] records;
    return (jint) num_read;
}

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    getConsolemode
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getConsolemode
        (JNIEnv *, jobject, jlong handle){
    DWORD result = 0;
    GetConsoleMode((HANDLE) handle, &result);
    return (long) result;
}

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    setConsolemode
 * Signature: (I)I
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_setConsolemode
        (JNIEnv * env, jobject, jlong handle, jint i){
    int b = SetConsoleMode((HANDLE) handle, (DWORD) i);
    return b;
}

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    setOutputCharacterEncoding
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getOutputCharacterEncoding
        (JNIEnv *, jobject, jint mode) {
    return SetConsoleOutputCP((UINT) mode);
}

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    getOutputCharacterEncoding
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getOutputCharacterEncoding
        (JNIEnv *, jobject) {
    return (jint) GetConsoleOutputCP();
}

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    setOutputCharacterEncoding
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_setOutputCharacterEncoding
        (JNIEnv *, jobject, jint mode) {
    return SetConsoleCP((UINT) mode);
}

/*
 * Class:     jni_impl_TerminalImpl
 * Method:    intialize
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_intialize
        (JNIEnv * env, jobject jobj) {
    const void* stdin_handle = GetStdHandle(STD_INPUT_HANDLE);
    const void* stdout_handle = GetStdHandle(STD_OUTPUT_HANDLE);
    const void* stderr_handle = GetStdHandle(STD_ERROR_HANDLE);

    if(stdin_handle == INVALID_HANDLE_VALUE ||
            stdout_handle == INVALID_HANDLE_VALUE ||
            stderr_handle == INVALID_HANDLE_VALUE)
        return false;

    jclass clazz = env->GetObjectClass(jobj);

    jfieldID stdinField = env->GetFieldID(clazz, "stdin", "J");
    jfieldID stdoutField = env->GetFieldID(clazz, "stdout", "J");
    jfieldID stderrField = env->GetFieldID(clazz, "stderr", "J");

    env->SetLongField(jobj, stdinField, (jlong) stdin_handle);
    env->SetLongField(jobj, stdoutField, (jlong) stdout_handle);
    env->SetLongField(jobj, stderrField, (jlong) stderr_handle);

    return true;
}