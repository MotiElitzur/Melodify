package motiapps.melodify.common.language.domain.repository

import motiapps.melodify.core.domain.base.Resource

interface LanguageRepository {
    suspend fun setAppLanguage(languageTag: String): Resource<Unit>
    suspend fun getAppLanguage(): Resource<String>
}