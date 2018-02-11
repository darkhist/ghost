#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>

#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_example_snapwiz_opencvcamera_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    LOGD("Java_org_opencv_samples_facedetect_DetectionBasedTracker_nativeSetFaceSize -- END");
    std::string hello = "Hello from C++ 1234";
    return env->NewStringUTF(hello.c_str());

}
