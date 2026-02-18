package com.financiaplus.app.core.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context,
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
        prefs.edit { putString(key, value) }
    }

    fun getDraftField(key: String): String? {
        return prefs.getString(key, null)
    }

    fun clearDraft() {
        prefs.edit { clear() }
    }

    fun saveCurrentStep(step: Int) {
        prefs.edit { putInt(KEY_CURRENT_STEP, step) }
    }

    fun getCurrentStep(): Int {
        return prefs.getInt(KEY_CURRENT_STEP, 0)
    }

    fun saveDocumentId(documentId: String) {
        prefs.edit { putString(KEY_DOCUMENT_ID, documentId) }
    }

    companion object {
        const val KEY_CURRENT_STEP = "current_step"
        const val KEY_FULL_NAME = "full_name"
        const val KEY_DOCUMENT_ID = "document_id"
        const val KEY_BIRTH_DATE = "birth_date"
        const val KEY_EMAIL = "email"
        const val KEY_PHONE = "phone"
        const val KEY_ADDRESS = "address"
        const val KEY_GENDER = "gender"
        const val KEY_DOCUMENT_IMAGE = "document_image"
        const val KEY_SELFIE_IMAGE = "selfie_image"
        const val KEY_GEO_IP = "geo_ip"
        const val KEY_GEO_CITY = "geo_city"
        const val KEY_GEO_COUNTRY = "geo_country"

        //origination
        const val KEY_MONTHLY_INCOME = "monthly_income"
        const val KEY_OCCUPATION = "occupation"
        const val KEY_DEPENDENTS = "dependents"
        const val KEY_FUNDS_ORIGIN = "funds_origin"
        const val KEY_FUNDS_INFO = "funds_info"
        const val KEY_ADDRESS_PROOF = "address_proof"
        const val KEY_CARD_TYPE = "card_type"
        const val KEY_CREDIT_LIMIT = "credit_limit"
        const val KEY_SIGNATURE = "signature"

    }
}