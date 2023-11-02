package dev.eduardobarbosa.portfolio.infrastructure.framework.controller

import com.fasterxml.jackson.annotation.JsonProperty
import dev.eduardobarbosa.portfolio.application.job.GetAllJobsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/jobs")
class JobController(
  private val getAllJobsService: GetAllJobsService
) {

  @GetMapping
  fun getJobs(): ResponseEntity<List<HttpGetJobsResponse>> {
    return ResponseEntity.ok(getAllJobsService.execute())
  }

  @PostMapping
  fun addJob(): ResponseEntity<List<HttpGetJobsResponse>> {
    //TODO not implemented yet
    return ResponseEntity.internalServerError().build()
  }

  @PutMapping("{id}")
  fun updateJob(@PathVariable id: String): ResponseEntity<List<HttpGetJobsResponse>> {
    //TODO not implemented yet
    return ResponseEntity.internalServerError().build()
  }

  @DeleteMapping("{id}")
  fun deleteJob(@PathVariable id: String): ResponseEntity<List<HttpGetJobsResponse>> {
    //TODO not implemented yet
    return ResponseEntity.internalServerError().build()
  }
}

data class HttpGetJobsResponse(
  @JsonProperty("id") val id: String,
  @JsonProperty("companyName") val companyName: String,
  @JsonProperty("companyURL") val companyURL: String,
  @JsonProperty("isCurrentCompany") val isCurrentCompany: Boolean,
  @JsonProperty("companyStartMonth") val companyStartMonth: String,
  @JsonProperty("companyStartYear") val companyStartYear: String,
  @JsonProperty("companyEndMonth") val companyEndMonth: String?,
  @JsonProperty("companyEndYear") val companyEndYear: String?,
  @JsonProperty("jobData") val jobData: JobDataResponse,
)

data class JobDataResponse(
  @JsonProperty("positions") val positions: List<PositionResponse>,
) {
  data class PositionResponse(
    @JsonProperty("position") val position: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("isCurrentPosition") val isCurrentPosition: Boolean,
    @JsonProperty("positionStartMonth") val positionStartMonth: String,
    @JsonProperty("positionStartYear") val positionStartYear: String,
    @JsonProperty("positionEndMonth") val positionEndMonth: String?,
    @JsonProperty("positionEndYear") val positionEndYear: String?,
  )
}
