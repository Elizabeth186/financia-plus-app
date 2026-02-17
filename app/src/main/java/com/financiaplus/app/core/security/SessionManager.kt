package com.financiaplus.app.core.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "financiaplus_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveDraftField(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getDraftField(key: String): String? {
        return prefs.getString(key, null)
    }

    fun clearDraft() {
        prefs.edit().clear().apply()
    }

    fun saveCurrentStep(step: Int) {
        prefs.edit().putInt(KEY_CURRENT_STEP, step).apply()
    }

    fun getCurrentStep(): Int {
        return prefs.getInt(KEY_CURRENT_STEP, 0)
    }

    fun saveDocumentId(documentId: String) {
        prefs.edit().putString(KEY_DOCUMENT_ID, documentId).apply()
    }

    companion object {
        const val KEY_CURRENT_STEP = "current_step"
        const val KEY_DOCUMENT_ID = "document_id"

    }
}