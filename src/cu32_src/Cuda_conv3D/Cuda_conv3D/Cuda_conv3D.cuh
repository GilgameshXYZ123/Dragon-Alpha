/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class z_dragon_engine_cuda_impl_math_Cuda_conv3D */

#ifndef _Included_z_dragon_engine_cuda_impl_math_Cuda_conv3D
#define _Included_z_dragon_engine_cuda_impl_math_Cuda_conv3D
#ifdef __cplusplus
extern "C" {
#endif
#undef z_dragon_engine_cuda_impl_math_Cuda_conv3D_IM2COL_GEMM
#define z_dragon_engine_cuda_impl_math_Cuda_conv3D_IM2COL_GEMM 1L
	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D
	 * Signature: ([JIJIIJIIJIIIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_texture
	 * Signature: ([JIJIIJIIJIIIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1texture
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3DV2
	 * Signature: (Z[JIJIIJIIJIIIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3DV2
	(JNIEnv *, jclass, jboolean, jlongArray, jint, jlong, jint, jint, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_np
	 * Signature: ([JIJIIJIIJIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1np
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_W1
	 * Signature: ([JIJIIJJIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1W1
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jlong, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    kernel_remode
	 * Signature: (JJJIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_kernel_1remode
	(JNIEnv *, jclass, jlong, jlong, jlong, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_GemmR
	 * Signature: ([JIJIIJJIIJIIIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1GemmR
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_GemmR_texture
	 * Signature: ([JIJIIJJIIJIIIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1GemmR_1texture
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_GemmV2R
	 * Signature: (Z[JIJIIJJIIJIIIIIIIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1GemmV2R
	(JNIEnv *, jclass, jboolean, jlongArray, jint, jlong, jint, jint, jlong, jlong, jint, jint, jlong, jint, jint, jint, jint, jint, jint, jint, jint, jint);

	/*
	 * Class:     z_dragon_engine_cuda_impl_math_Cuda_conv3D
	 * Method:    conv3D_GemmR_W1
	 * Signature: ([JIJIIJJJIII)V
	 */
	JNIEXPORT void JNICALL Java_z_dragon_engine_cuda_impl_math_Cuda_1conv3D_conv3D_1GemmR_1W1
	(JNIEnv *, jclass, jlongArray, jint, jlong, jint, jint, jlong, jlong, jlong, jint, jint, jint);

#ifdef __cplusplus
}
#endif
#endif