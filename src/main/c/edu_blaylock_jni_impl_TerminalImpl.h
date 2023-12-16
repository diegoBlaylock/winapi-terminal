/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class edu_blaylock_jni_impl_TerminalImpl */

#ifndef _Included_edu_blaylock_jni_impl_TerminalImpl
#define _Included_edu_blaylock_jni_impl_TerminalImpl
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    getConsoleScreenBufferInfo
 * Signature: (J)Ledu/blaylock/jni/structs/ConsoleScreenBufferInfo;
 */
JNIEXPORT jobject JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getConsoleScreenBufferInfo
  (JNIEnv *, jobject, jlong);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    setConsoleTextAttribute
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_setConsoleTextAttribute
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    write
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_write
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    read
 * Signature: ([BI)I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_read
  (JNIEnv *, jobject, jbyteArray, jint);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    readInputEvents
 * Signature: ([Ledu/blaylock/terminal/events/Record;I)I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_readInputEvents
  (JNIEnv *, jobject, jobjectArray, jint);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    getConsolemode
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getConsolemode
  (JNIEnv *, jobject, jlong);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    setConsolemode
 * Signature: (JI)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_setConsolemode
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    setOutputCharacterEncoding
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_setOutputCharacterEncoding
        (JNIEnv *, jobject, jint);

/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    getOutputCharacterEncoding
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_getOutputCharacterEncoding
        (JNIEnv *, jobject);


/*
 * Class:     edu_blaylock_jni_impl_TerminalImpl
 * Method:    intialize
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_edu_blaylock_jni_impl_TerminalImpl_initialize
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
