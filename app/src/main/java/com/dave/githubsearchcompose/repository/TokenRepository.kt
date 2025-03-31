package com.dave.githubsearchcompose.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class TokenRepository @Inject constructor(private val dataStore : DataStore<Preferences>) {

    fun getToken(): Flow<String> =
        dataStore.data.map { preference ->
            preference[TOKEN_KEY].let { token ->
                if(token.isNullOrEmpty()) "" else decrypt(token)
            }
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { preference ->
            preference[TOKEN_KEY] = encrypt(token)
        }
    }

    private fun encrypt(data: String): String {

        // Secret Key와 IV를 ByteArray 형식으로 변환
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(IV.toByteArray())

        // Cipher 초기화
        val cipher = Cipher.getInstance(CIPHER)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)

        // 암호화할 데이터를 ByteArray 형식으로 변환 후 암호화 처리
        val encryptedBytes = cipher.doFinal(data.toByteArray())

        // 암호화된 ByteArray 형식의 데이터를 Base64 인코딩하여 문자열로 변환
        return String(Base64.getEncoder().encode(encryptedBytes), StandardCharsets.UTF_8)

    }

    private fun decrypt(encryptData: String): String {

        // Secret Key와 IV를 ByteArray 형식으로 변환
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(IV.toByteArray())

        // Cipher 초기화
        val cipher = Cipher.getInstance(CIPHER)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

        // 암호화된 문자열 데이터를 ByteArray 형식으로 변환
        val byteArrayEncryptedText = Base64.getDecoder().decode(encryptData)

        // 암호화된 ByteArray 형식의 데이터를 복호화 처리
        val decryptedBytes = cipher.doFinal(byteArrayEncryptedText)

        // 복호화된 ByteArray 형식의 데이터를 문자열로 변환
        return String(decryptedBytes, StandardCharsets.UTF_8)

    }


    companion object {
        val TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        val SECRET_KEY = "GithubSecretData"
        val IV = "GitHubCompose"
        val CIPHER = "AES/GCM/NoPadding"
    }

}