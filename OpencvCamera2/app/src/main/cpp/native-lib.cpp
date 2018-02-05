#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>


extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_snapwiz_opencvcamera_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ 1234";
    return env->NewStringUTF(hello.c_str());
}
