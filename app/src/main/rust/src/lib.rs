use jni::objects::{JByteBuffer, JObject, JString};
use jni::JNIEnv;
use jni::sys::jbyteArray;

#[no_mangle]
pub unsafe extern "C" fn Java_com_amozeshgam_amozeshgam_view_SplashActivity_hello<'a>(
    env: JNIEnv<'a>,
    _: JObject<'a>,
) -> JString<'a> {
    return env.new_string("hello world").unwrap();
}

#[no_mangle]
pub unsafe extern "C" fn Java_com_amozeshgam_amozeshgam_handler_exoplayerHandler_dataSource<'a>(
    mut env: JNIEnv<'a>,
    _: JObject,
    key: jbyteArray,
    nonce: jbyteArray,
    crypto_data: JByteBuffer<'a>,
) -> JByteBuffer<'a> {
    let convert_key = env.convert_byte_array(key.into()).unwrap();
    let convert_nonce = env.convert_byte_array(nonce.into()).unwrap();
    let convert_crypto_data = env.convert_byte_array(crypto_data.into()).unwrap();
    let cipher = chacha20::ChaCha20::new(&convert_key.into(), &convert_nonce.into());
    let mut decrypt_data = convert_crypto_data.clone();
    cipher.apply_keystream(&mut decrypt_data);
    return env.byte_array_from_slice(decrypt_data).unwrap();
}
