package dev.eduardobarbosa.portfolio.infrastructure.framework.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class CommonRepositoryUtil {
  companion object {
    val gson: Gson = GsonBuilder()
      .serializeNulls()
      .create()
  }
}
